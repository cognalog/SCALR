JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: ScalrParser.java $(classes)

ScalrParser.java: ScalrParser.jj
	javacc ScalrParser.jj

clean :
	rm -f *.class *.java

%.class : %.java
	$(JAVAC) $<