
package scalr;

import java.util.ArrayList;

import parser.SimpleNode;
import scalr.Exceptions.FunctionExistsError;
import scalr.Exceptions.TypeError;
import scalr.expression.Function;
import scalr.expression.StatementBuilder;

public class FscalrParser
{
	
	public FscalrParser(SimpleNode node) throws FunctionExistsError, TypeError
	{
		// We expect to receive functions, so let's look for them
		// Get the children of the node
		ArrayList<SimpleNode> children = BackendMain.getChildren(node);
		// Go through the nodes and make the functions
		for (SimpleNode n : children) {
			if (n.toString().equals("function"))
				buildFunc(n);
			else if (n.toString().equals("fscalr2"))
				buildFunc((SimpleNode) n.jjtGetChild(0));
		}
	}
	
	private void buildFunc(SimpleNode node) throws FunctionExistsError, TypeError
	{
		// First, get the name of the function
		Function func = new Function((String) ((SimpleNode) node.jjtGetChild(0)).jjtGetValue());
		System.out.println(func.getName());
		// Then, get the parameters
		ArrayList<SimpleNode> params = BackendMain.getChildren((SimpleNode) node.jjtGetChild(1));
		for (SimpleNode n : params)
			func.addParameter((String) n.jjtGetValue());
		// Then, get the statements
		ArrayList<SimpleNode> fblock = BackendMain.getChildren((SimpleNode) node.jjtGetChild(2));
		for (SimpleNode n : fblock) {
			StatementBuilder.buildStmt(n, func);
		}
	}
}
