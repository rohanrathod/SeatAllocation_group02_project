from django.shortcuts import render
from django.http import HttpResponse, HttpResponseRedirect
from django.contrib.auth.models import User
from django.shortcuts import get_object_or_404, render
from django.template import RequestContext, loader
from django.http import Http404
from django.core.urlresolvers import reverse
from django.contrib.auth import authenticate, login, logout
from django.views import generic
from polls.models import Question,Options,Preferences

# Create your views here.
'''
def index(request):
    
    template = loader.get_template('polls/index.html')
    context = RequestContext(request, {
        
        })
    return HttpResponse(template.render(context))
'''
'''def detail(request, question_id):
    try:
        question = Question.objects.get(pk=question_id)
    except Question.DoesNotExist:
        raise Http404
    return render(request, 'polls/\detail.html', {'question': question})
'''
class IndexView(generic.ListView):
    template_name = 'polls/index.html'
    context_object_name = 'latest_question_list'

    def get_queryset(self):
        """Return the last five published questions."""
        return Question.objects.order_by('-pub_date')[:5]

class DetailView(generic.DetailView):
    model = User
    template_name = 'polls/detail.html'
    
class ResultsView(generic.ListView):
	model = User
	template_name = 'polls/results.html'
	context_object_name = 'prefsl'
	def get_queryset(self):
		return  Preferences.objects
	
	


'''def results(request, user_id):
    
    return render(request, 'polls/results.html', {'user': user})
'''
def vote(request):
	username = request.POST['username']
	password = request.POST['password']
	user = authenticate(username=username, password=password)	
	if user is not None:
		login(request,user)
		return HttpResponseRedirect(reverse('polls:results', args=(user.id,)))
		
	else:
		return render(request, 'polls/index.html', {
	         'error_message': 'invalid username or password',
		})
		
def add(request):
	#p = get_object_or_404(Preferences)
	now_user=request.user
	pr = request.POST['Preferences']
	al = Preferences.objects.get(id=pr)
	flag = True
	for p in now_user.options_set.all():
		if p.option_code == al.pref_code:
			flag = False
			
	
	if flag==True :
		now_user.options_set.create(option_college=al.pref_college,option_branch=al.pref_branch,option_code=al.pref_code,)
	
	
	return HttpResponseRedirect(reverse('polls:results', args=(now_user.id,)))

def delete(request,pk):
	p=get_object_or_404(Options, pk=pk)
	now_user=request.user
	obj = now_user.options_set.get(pk=pk)
	obj.delete()
	return HttpResponseRedirect(reverse('polls:results', args=(now_user.id,)))
	
	
def edit(request,pk):
	p=get_object_or_404(Options, pk=pk)
	now_user=request.user
	obj = now_user.options_set.get(pk=pk)
	return render(request, 'polls/edit.html',{
			'a' : pk ,
			'ch' : obj ,
			'prefsl' : Preferences.objects ,
		})
	
'''
def update_list(request):
	now_user=request.user
	get = request.POST['change_of']
	a = get[0]
	b = get[1]
	
	obj = now_user.options_set.get(id=a)
	
	if b == 1:
		return HttpResponseRedirect(reverse('polls:results', args=(now_user.id,)))
	elif b == 2:
		obj.delete()
		return HttpResponseRedirect(reverse('polls:results', args=(now_user.id,)))
	
	elif b == 3:
		return render(request, 'polls/edit.html',{
			'a' : a ,
			'ch' : 
			'prefsl' : Preferences.objects ,
		})
'''	
def redirect_to_original(request,pk):
	now_user=request.user
	
	p=get_object_or_404(Options, pk=pk)
	x = request.POST['Preferences']
	b = Preferences.objects.get(id=x)
	
	old = now_user.options_set.get(id=pk)
	flag = True
	#new = now_user.options_set.get(id=x)
	for q in now_user.options_set.all():
		if q.option_code == b.pref_code:
			z = False
	if z == True:	
		old.option_college = b.pref_college
		old.option_code = b.pref_code
		old.option_branch = b.pref_branch
		old.save()
		
	return HttpResponseRedirect(reverse('polls:results', args=(now_user.id,)))

def logout_view(request):
	logout(request)
	return render(request, 'polls/index.html')
	
    
	
'''p = get_object_or_404(Question, pk=question_id)
	try:
        selected_choice = p.choice_set.get(pk=request.POST['choice'])
    except (KeyError, Choice.DoesNotExist):
        # Redisplay the question voting form.
        return render(request, 'polls/detail.html', {
            'question': p,
            'error_message': "You didn't select a choice.",
        })
    else:
        selected_choice.votes += 1
        selected_choice.save()
        # Always return an HttpResponseRedirect after successfully dealing
        # with POST data. This prevents data from being posted twice if a
        # user hits the Back button.
        return HttpResponseRedirect(reverse('polls:results', args=(user.id,)))'''
