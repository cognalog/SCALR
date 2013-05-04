JAVAC=javac
JFLAGS = -classpath './:../'
sources = $(wildcard *.java)
classes = $(sources:.java=.class)
f = 

.PHONY: all
all: ScalrParser2.java $(classes)

.PHONY: run
run: all
	make && java -classpath './:../' parser.ScalrParser2 $(f)

.PHONY: example
example: all
	make && java -classpath './:../' parser.ScalrParser2 'tests/hello.tscalr'

.PHONY: refresh
refresh: clean all

ScalrParser2.java: ScalrParser2.jj
	../javacc/javacc ScalrParser2.jj

clean :
	rm -f *.class *.java *~

%.class : %.java
	$(JAVAC) $(JFLAGS) $<
