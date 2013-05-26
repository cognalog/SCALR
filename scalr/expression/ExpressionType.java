
package scalr.expression;

/**
 * A simple enum that efficiently tells us the type an expression ultimately has. The type has to be
 * one of the three variables, as well as a boolean type.
 * @author mark
 */
public enum ExpressionType {
	BOOLEAN, NUMBER, NOTE, SEQUENCE, SCALE, RETURN, CANCEL, PRINT
}
