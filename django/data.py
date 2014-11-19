import csv
from django.contrib.auth.models import User

with open('../ranklist.csv', newline='') as csvfile:
	candidate = csv.reader(csvfile, delimiter=',')
	first_row = next(candidate)
	for row in candidate:
		userid = row[0]
		passd = userid + 'pass'
		newcandidate = User.objects.create_user(userid, 'abc@gmail.com', passd)
		newcandidate.save()
