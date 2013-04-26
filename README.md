SCALR
=====

Sexy Compositions and Luscious Rhythms

Testing SyntaxChecker.jj:

1. ./javacc/javacc SyntaxChecker.jj
2. javac *.java
3. java SyntaxChecker test.txt

Installing midiutil:

`python python-midiutil/setup.py`

Running string-to-midi converter:

`python midi_generator.py "[60,1,100|64,1,100|65,1,100][64,1,100|67,2,100]"`

Square braces denote a track, inside the square braces are notes delimited by |.

The notes are specified by pitch, duration (in quarter notes), volume.