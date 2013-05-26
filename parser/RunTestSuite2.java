package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class RunTestSuite2 {

	public RunTestSuite2()
	{
		// Get the root directory
		File rootDir = new File("src\\test\\tests-that-should-succeed");
		// Go recursively through each folder
		findTscalr(rootDir);
	}

	private void findTscalr(File rootDir)
	{
		for (File file : rootDir.listFiles()) {
			if (file.isDirectory())
				findTscalr(file);
			else if (file.getName().endsWith("tscalr"))
				runTest(file);
		}
	}

	private void runTest(File tscalr)
	{
		// Mark the expected output
		File expected = new File(tscalr.getParent(), "expected-output");
		String line = null;
		try {
			Scanner in = new Scanner(expected);
			line = in.nextLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Mark the ScalrParser class directory
		File scalr = new File("out\\production\\SCALR");

		// Run the parser on the tscalr file
		try {
			Process proc = Runtime.getRuntime().exec("java -cp . parser.ScalrParser " + tscalr.getAbsolutePath(), null,
					                                        scalr);
			// Gobble stderr
			new StreamGobbler(proc.getErrorStream());
			// Compare the last line of stdout
			StreamGobbler stdout = new StreamGobbler(proc.getInputStream());
			String lastLine;
			System.out.println("-------------" + new File(tscalr.getParent()).getName() +
					                   System.getProperty("file.separator") + tscalr.getName() + "-------------");
			System.out.println("Return code: " + proc.waitFor());
			System.out.println("Compiler Result: " + (lastLine = stdout.getLastLine()));
			System.out.println("Expected result: " + line);
			System.out.println("Verdit: " + ((lastLine.equals(line)) ? "Success" : "Fail"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		new RunTestSuite2();
	}
}
