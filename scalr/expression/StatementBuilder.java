
package scalr.expression;

import java.util.ArrayList;

import parser.SimpleNode;
import scalr.BackendMain;

public final class StatementBuilder
{
	
	private StatementBuilder() throws AssertionError
	{
		throw new AssertionError();
	}
	
	public static Expression buildStmt(SimpleNode node, Function func)
	{
		System.out.println(node);
		// Get the children of this function node
		ArrayList<SimpleNode> children = BackendMain.getChildren(node);
		for (SimpleNode n : children) {
			if (n.toString().equals("assignment")) {
				func.addStatement(buildAssignment(n));
			}
		}
		return null;
	}
	
	private static Expression buildAssignment(SimpleNode node)
	{
		System.out.println("building assignment");
		// Get the name of the variable that is being assigned
		String name = (String) ((SimpleNode) node.jjtGetChild(0)).jjtGetValue();
		// Get the type of assignment
		AssignmentOperator assign =
		        new AssignmentOperator((String) node.jjtGetChild(1).jjtGetValue());
		System.out.println(assign);
		// Get the expression being assigned
		SimpleNode expression = node.jjtGetChild(2);
		Expression expr = getExpression(expression);
		return null;
	}
	
	private static Expression getExpression(SimpleNode expression)
	{
		ArrayList<SimpleNode> children = BackendMain.getChildren(expression);
		System.out.println(children.get(0).jjtGetValue());
		return null;
	}
}
