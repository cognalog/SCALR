JAVAC=javac
JFLAGS = -classpath './:../'
sources = $(wildcard *.java)
classes = $(sources:.java=.class)
f = 

.PHONY: all
all: ScalrParser.java $(classes)

.PHONY: run
run: all
	make && java -classpath './:../' parser.ScalrParser $(f)

.PHONY: example
example: all
	make && java -classpath './:../' parser.ScalrParser 'tests/hello.tscalr'

.PHONY: refresh
refresh: clean all

ScalrParser.java: ScalrParser.jj
	../javacc/javacc ScalrParser.jj

clean :
	rm -f *.class *.java *~

%.class : %.java
	$(JAVAC) $(JFLAGS) $<
