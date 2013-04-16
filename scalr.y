%{
  #include <ctype.h>
  #include <stdio.h>
  %}

function: func identifier ( parameters )block \n return \n end;

parameters: identifier parameters’;

parameters’: ',' parameters
| /*empty */
;

block: block ‘\n’ line
| line
;  

line: loop
| conditional
| assignment
| "\n"
| /* empty */
;

loop: while ( boolean-expr ) block end
| foreach ( identifier in return ) block end
;

boolean-expr: boolean-expr and b-e’
| boolean-expr or b-e’
| b-e’
;

b-e’: not boolean-expr
| operand == operand
| operand > operand
| operand < operand
| operand <= operand
| operand <= operand
;

conditional: if ( boolean-expr ) block end \n conditional’

conditional': else conditional
| else block end
| /* empty */
;

assignment: identifier = expression
| identifier += expression
| identifier *= expression
| identifier /= expression
| identifier %= expression
;

expression: expression + expr’
| expression - expr’
| expr’
;

expr’: expr’ * expr’’
| expr’ / expr’’
| expr’’
;

expr’’: expr’’ % expr’’’
| expr’’’
;

expr’’’: ( expression )
| operand
;

operand: identifier
| int
| note
| sequence
;

note: note 
| note . pitch ( expression )
| note . pitch ( degree )
| note . pitch ( + expression )
| note . pitch ( - expression )
| note . volume ( expression )
| note . volume ( + expression )
| note . volume ( - expression )
| note . duration ( expression )
| note . duration ( + expression )
| note . duration ( - expression )
;

return: identifier
| sequence
;

sequence: [ note-list ];

note-list: note note-list’;

note-list’: , note-list
| /* empty */
;

return: identifier
| sequence
;

track: track '->' return;
%%
