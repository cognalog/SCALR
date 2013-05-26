package parser;

import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

public class StreamGobbler
{
	private final LinkedBlockingDeque<String> lastString;

	public StreamGobbler(InputStream is)
	{
		lastString = new LinkedBlockingDeque<String>();
		Runnable gobbler = new Gobbler(is);
		new Thread(gobbler).start();
	}

	public String getLastLine()
	{
		try {
			return lastString.take();
		} catch (InterruptedException e) {
			return null;
		}
	}

	private class Gobbler implements Runnable
	{
		InputStream stream;

		public Gobbler(InputStream is)
		{
			stream = is;
		}

		@Override
		public void run()
		{
			Scanner in = new Scanner(stream);
			String lastLine = "-1";
			while (in.hasNextLine()) {
				String line = in.nextLine();
				if ((line = line.trim()).isEmpty())
					continue;
				lastLine = line;
			}
			lastString.push(lastLine);
			in.close();
		}
	}
}
