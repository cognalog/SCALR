
package scalr;

import java.util.ArrayList;

import parser.SimpleNode;
import scalr.Exceptions.FunctionExistsError;
import scalr.Exceptions.TypeError;

public class BackendMain
{
	public static int	tempo	= 120;
	
	public BackendMain(SimpleNode node) throws FunctionExistsError, TypeError
	{
		// Delineating the tasks to the appropriate parser.
		if (node.jjtGetChild(0).toString().equals("fscalr"))
			new FscalrParser((SimpleNode) node.jjtGetChild(0));
	}
	
	public static ArrayList<SimpleNode> getChildren(SimpleNode node)
	{
		ArrayList<SimpleNode> children = new ArrayList<SimpleNode>(node.jjtGetNumChildren() + 1);
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
			children.add((SimpleNode) node.jjtGetChild(i));
		return children;
	}
}
