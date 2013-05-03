
package scalr.expression;

import java.util.ArrayList;

import parser.SimpleNode;
import scalr.BackendMain;
import scalr.variable.Sequence;

public final class StatementBuilder
{
	
	private StatementBuilder() throws AssertionError
	{
		throw new AssertionError();
	}
	
	public static void buildStmt(SimpleNode node, Function func)
	{
		System.out.println(node);
		// Get the children of this function node
		ArrayList<SimpleNode> children = BackendMain.getChildren(node);
		for (SimpleNode n : children) {
			if (n.toString().equals("assignment")) {
				func.addStatement(buildAssignment(n));
			}
		}
	}
	
	private static Expression buildAssignment(SimpleNode node)
	{
		// Get the name of the variable that is being assigned
		String name = (String) ((SimpleNode) node.jjtGetChild(0)).jjtGetValue();
		// Get the type of assignment
		AssignmentOperator assign =
		        new AssignmentOperator((String) node.jjtGetChild(1).jjtGetValue());
		System.out.println(assign);
		// Get the expression being assigned
		SimpleNode expression = node.jjtGetChild(2);
		Expression expr = buildExpression(expression);
		System.out.println(expr);
		return null;
	}
	
	private static Expression buildExpression(SimpleNode expression)
	{
		ArrayList<SimpleNode> children = BackendMain.getChildren(expression);
		Expression result = null;
		// What follows is a case by case listing of what could follow after each production of
		// expression.
		if (children.size() == 1) {
			if (children.get(0).toString().equals("expr1"))
				return buildExpr1(children.get(0));
		}
		else {
			return result;
		}
		return result;
	}
	
	private static Expression buildExpr1(SimpleNode node)
	{
		// An expr1 could have 1 or more expr2 children
		ArrayList<SimpleNode> children = BackendMain.getChildren(node);
		if (children.size() == 1) {
			return buildExpr3(children.get(0));
		}
		else {
			return null;
		}
	}
	
	private static Expression buildExpr3(SimpleNode node)
	{
		// An expr3 has 1 expr5 child and 0 or more expr4 children
		ArrayList<SimpleNode> children = BackendMain.getChildren(node);
		if (children.size() == 1) {
			return buildExpr5(children.get(0));
		}
		else {
			return null;
		}
	}
	
	private static Expression buildExpr5(SimpleNode node)
	{
		// An expr5 either has an expression as its child or an operand. If it's an operand,
		SimpleNode child = node.jjtGetChild(0);
		if (child.toString().equals("operand")) {
			return getOperand(child);
		}
		else {
			return buildExpression(child);
		}
	}
	
	private static Expression getOperand(SimpleNode node)
	{
		// An operand's child could be one of 5 things
		SimpleNode child = node.jjtGetChild(0);
		String nodeType = child.toString();
		if (nodeType.equals("funcall")) {
			
		}
		else if (nodeType.equals("id")) {
			
		}
		else if (nodeType.equals("note")) {
			
		}
		else if (nodeType.equals("sequence")) {
			return buildSequence(child);
		}
		else if (nodeType.equals("num")) {
			
		}
		return null;
	}
	
	private static Expression buildSequence(SimpleNode node)
	{
		// A sequence can have 0 or more children
		ArrayList<SimpleNode> children = BackendMain.getChildren(node);
		if (children.size() == 0) {
			// In this case, it's a new sequence
			return new Sequence();
		}
		else {
			
		}
		return null;
	}
}
