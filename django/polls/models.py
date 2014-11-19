from django.db import models
import datetime
from django.utils import timezone
from django.contrib.auth.models import User


# Create your models here.
class Question(models.Model):
    question_text = models.CharField(max_length=200)
    pub_date = models.DateTimeField('date published')
    def __str__(self):              # __unicode__ on Python 2
        return self.question_text

    def was_published_recently(self):
        return self.pub_date >= timezone.now() - datetime.timedelta(days=1)

    was_published_recently.admin_order_field = 'pub_date'
    was_published_recently.boolean = True
    was_published_recently.short_description = 'Published recently?'

#preferences
class Options(models.Model):
    user = models.ForeignKey(User)
    option_college = models.CharField(max_length=100)
    option_branch = models.CharField(max_length=200)
    option_code = models.CharField(max_length=10)
    #votes = models.IntegerField(default=0)
    def __str__(self):              # __unicode__ on Python 2
        return self.choice_code

class Preferences(models.Model):
	pref_college = models.CharField(max_length=100)
	pref_branch = models.CharField(max_length=200)
	pref_code = models.CharField(max_length=10)
	def __str__(self):              # __unicode__ on Python 2
		return self.pref_code
	
