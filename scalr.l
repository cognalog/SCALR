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
{id} {yylval = (int) installID(); return(ID)};
{int} {yylval = (int) installInt(); return(INT);
%%
