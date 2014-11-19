from django.conf.urls import patterns, url

from polls import views

urlpatterns = patterns('',
    url(r'^$', views.IndexView.as_view(), name='index'),
    url(r'^(?P<pk>\d+)/detail/$', views.DetailView.as_view(), name='detail'),
    # ex: /polls/5/results/
    url(r'^(?P<pk>\d+)/results/$', views.ResultsView.as_view(), name='results'),
    # ex: /polls/5/vote/
    url(r'^vote/$', views.vote, name='vote'),
    url(r'^add/$', views.add, name='add'),
   # url(r'^(?P<pk>\d+)/change/$', views.update_list, name='update_list'),
    url(r'^delete/(?P<pk>\d+)$', views.delete, name='delete'),
    url(r'^edit/(?P<pk>\d+)$', views.edit, name='edit'),
    url(r'^redirect_to_original/(?P<pk>\d+)$', views.redirect_to_original, name='redirect_to_original'),
    url(r'^logout_view/$', views.logout_view, name='logout_view'),
)
