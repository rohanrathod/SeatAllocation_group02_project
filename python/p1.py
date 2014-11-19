#!/usr/bin/python3
import HTMLParser
from urllib.request import urlopen
import sys
import re

if len(sys.argv) <= 1:
	print("Incorrect Usage: Need to put correct usage over here")
	exit(1)


base = sys.argv[1]
splits = "|".join(sys.argv[2:])
urlObj = urlopen(base)
content = urlObj.read()
content = content.decode('UTF-8')
filtr = re.compile(r'.*('+splits+')$')


class Parser(HTMLParser):
	stored = []
	fileNames = []
	def __init__(self,base):
		HTMLParser.__init__(self)
		self.base = base
	def handle_starttag(self, tag, attrs):
		if tag == "A" or tag == "a":
			for x in attrs:	
				if x[0] == 'href' and filtr.search(x[1]):
					self.stored += [self.base+str(x[1])]
					self.fileNames += [str(x[1])]
	def handle_endtag(self, tag):
		pass
	def handle_data(self, data):
		pass

parsObj = Parser(base)
parsObj.feed(content)

for x in range(len(parsObj.fileNames)):
	out = open(parsObj.fileNames[x],mode='wb')
	url = urlopen(parsObj.stored[x])
	data = url.read()
	out.write(data)
	out.close()





#---In Memory Of Memories...
