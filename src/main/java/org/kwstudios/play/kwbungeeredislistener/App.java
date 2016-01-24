package org.kwstudios.play.kwbungeeredislistener;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.kwstudios.play.kwbungeeredislistener.toolbox.SimpleConsoleFormatter;

/**
 * This is the main class which will handle the console input.
 *
 */
public class App {

	private static final String LOGGER_NAME = "org.kwstudios.play.kwbungeeredislistener";
	private static Logger logger = Logger.getLogger(App.LOGGER_NAME);
	private static ConsoleHandler handler = new ConsoleHandler();

	public static void main(String[] args) {
		handler.setLevel(Level.ALL);
		handler.setFormatter(new SimpleConsoleFormatter());
		try {
			handler.setEncoding("UTF-8");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		logger.setUseParentHandlers(false);
		logger.setLevel(Level.ALL);
		logger.addHandler(handler);

		logger.log(Level.INFO, "******** Starting the Listener ********");
		// System.out.println( "******** Starting the Listener ********" );

		outputHandler();
	}

	private static void outputHandler() {
		try {
			Console console = System.console();

			if (console != null) {
				System.out.print(">");
				Scanner scanner = new Scanner(console.reader());
				scanner.useDelimiter(Pattern.compile("[\r\n]+"));

				while (scanner.hasNext()) {
					String str = scanner.next();
					logger.info(str);
					System.out.print(">");
				}
			} else {
				logger.info("There is no Console available!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
