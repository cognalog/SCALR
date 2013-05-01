JAVAC=javac
JFLAGS = -classpath './:../'
sources = $(wildcard *.java)
classes = $(sources:.java=.class)
f = 

.PHONY: all
all: ScalrParser.java ScalrParser.jj $(classes)

.PHONY: run
run: all
	make && java -classpath './:../' parser.ScalrParser $(f)

.PHONY: example
example: all
	make && java -classpath './:../' parser.ScalrParser 'tests/2-halves.fscalr'

.PHONY: refresh
refresh: clean all
	make

ScalrParser.jj: ScalrParser.jjt
	../javacc/jjtree ScalrParser.jjt

ScalrParser.java: ScalrParser.jj
	../javacc/javacc ScalrParser.jj

clean :
	rm -f *.class *.java *.jj *~

%.class : %.java
	$(JAVAC) $(JFLAGS) $<
