whitespace = [ \t\n]
letter = [A-Za-z]
int = 0 | [1-9][0-9]*
id = {letter}({letter}|{int})*
%%
{whitespace} { }
note { return NOTE };
pitch { return PITCH };
volume { return VOLUME };
duration { return DURATION };
end { return END };
func { return FUNC };
while { return WHILE };
foreach { return FOREACH };
in { return IN };
if { return IF };
else { return ELSE };
track { return TRACK };
{id} { yylval = (int) installID(); return(ID) };
{int} { yylval = (int) installInt(); return(INT) };
%%
