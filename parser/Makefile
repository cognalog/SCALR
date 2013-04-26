JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: ScalrParser.java ScalrParser.jj $(classes)

ScalrParser.jj: ScalrParser.jjt
	../javacc/jjtree ScalrParser.jjt

ScalrParser.java: ScalrParser.jj
	../javacc/javacc ScalrParser.jj

clean :
	rm -f *.class *.java *.jj

%.class : %.java
	$(JAVAC) $<