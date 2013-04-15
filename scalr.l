%{
#include <stdio.h>
#include "y.tab.h"
%}
whitespace [ \t\n]
letter [A-Za-z]
digit [1-9]
int {digit}+
id {letter}({letter}|{int})*
%%
{whitespace} { }
note return NOTE;
pitch return PITCH;
volume return VOLUME;
duration return DURATION;
end return END;
func return FUNC;
while return WHILE;
foreach return FOREACH;
if return IF;
else return ELSE;
{id} {yylval = (int) installID(); return(ID)};
{int} {yylval = (int) installInt(); return(INT)};
%%
