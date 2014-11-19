all:java javadoc python django
	
python:
	cd python
	python update.py
	
javadoc:
	cd java
	javadoc -d html src/*.java 
java:
	cd java/src
	javac *.java
	java Main
	
django:
	
