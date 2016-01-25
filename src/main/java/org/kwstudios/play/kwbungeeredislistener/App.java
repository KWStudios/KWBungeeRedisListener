package org.kwstudios.play.kwbungeeredislistener;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.kwstudios.play.kwbungeeredislistener.commands.ICommand;
import org.kwstudios.play.kwbungeeredislistener.commands.SendRedisMessageCommand;
import org.kwstudios.play.kwbungeeredislistener.commands.ShutdownCommand;
import org.kwstudios.play.kwbungeeredislistener.json.Settings;
import org.kwstudios.play.kwbungeeredislistener.json.jedis.JedisSettings;
import org.kwstudios.play.kwbungeeredislistener.listener.JedisMessageListener;
import org.kwstudios.play.kwbungeeredislistener.toolbox.SimpleConsoleFormatter;

import com.google.gson.Gson;

import redis.clients.jedis.Protocol;

/**
 * This is the main class which will handle the console input.
 *
 */
public class App {

	private static final String LOGGER_NAME = "org.kwstudios.play.kwbungeeredislistener";

	private static Logger logger = Logger.getLogger(App.LOGGER_NAME);
	private static ConsoleHandler handler = new ConsoleHandler();

	private static Settings settings = new Settings();

	private static JedisMessageListener jedisChannelListener = null;

	private static List<ICommand> allCommands = new ArrayList<ICommand>();

	public static void main(String[] args) {
		setupLogger();

		logger.log(Level.INFO, "******** Starting the Listener ********");
		reloadSettingsFile();
		setupJedisListener();

		setupCommands();

		outputHandler();
	}

	private static void setupLogger() {
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
	}

	private static void reloadSettingsFile() {
		Gson gson = new Gson();

		URL url = App.class.getProtectionDomain().getCodeSource().getLocation();
		File folder = null;
		try {
			folder = new File(url.toURI()).getParentFile();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		File file = new File(folder, "settings.json");

		if (!file.exists()) {
			createDefaultSettingsFileAndExit(file);
		}

		FileInputStream fileInput = null;
		BufferedReader reader = null;
		try {
			fileInput = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(fileInput, "UTF-8"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		if (reader == null) {
			createDefaultSettingsFileAndExit(file);
		}

		// JedisSettings jedis = gson.fromJson(reader, JedisSettings.class);
		// settings.setJedis(jedis);
		settings = gson.fromJson(reader, Settings.class);
	}

	private static void setupJedisListener() {
		logger.info(settings.getJedis().getHost());
		logger.info(settings.getJedis().getPassword());
		logger.info(Integer.toString(settings.getJedis().getPort()));
		App.jedisChannelListener = new JedisMessageListener(settings.getJedis().getHost(),
				settings.getJedis().getPort(),
				settings.getJedis().getPassword().isEmpty() ? null : settings.getJedis().getPassword(),
				settings.getJedis().getChannelsToListen()) {
			@Override
			public synchronized void taskOnMessageReceive(String channel, String message) {
				logger.info("Message received, trying to parse it gracefully...");
				logger.info("Message received: " + message + "Channel: " + channel);
				// TODO Call new method which parses the message
			}
		};
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

					String[] splitted = str.split("\\s+");
					if (splitted.length > 0) {

						List<String> args = new ArrayList<String>();
						for (int i = 0; i < splitted.length; i++) {
							if (i != 0) {
								args.add(splitted[i]);
							}
						}
						// TODO Dynamic testing for the label through all
						// ICommands
						if (splitted[0].trim().equalsIgnoreCase(allCommands.get(0).getLabel())) {
							allCommands.get(0).execute(args);
						}

					}

					logger.info(str);
					System.out.print(">");
				}
				scanner.close();
			} else {
				logger.info("There is no Console available!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void createDefaultSettingsFileAndExit(File file) {
		Gson gson = new Gson();
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JedisSettings jedis = new JedisSettings(Protocol.DEFAULT_HOST, "", new String[] { "minigame-server-creation" },
				Protocol.DEFAULT_PORT);
		settings.setJedis(jedis);
		String settingsJson = gson.toJson(settings);
		try {
			Files.write(Paths.get(file.getAbsolutePath()), settingsJson.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("You should now configure the settings.json file.");
		logger.info("This application will exit in 10 seconds.");
		logger.info("Please change the settings.json to fit to your needs and restart this application.");
		final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

		exec.schedule(new Runnable() {
			@Override
			public void run() {
				System.exit(0);
				exec.shutdown();
			}
		}, 10, TimeUnit.SECONDS);

		try {
			exec.awaitTermination(20, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void setupCommands() {
		allCommands.add(new SendRedisMessageCommand());
	}

	public static Logger getLogger() {
		return logger;
	}

	public static Settings getSettings() {
		return settings;
	}

	public static JedisMessageListener getJedisChannelListener() {
		return jedisChannelListener;
	}

	public static List<ICommand> getAllCommands() {
		return allCommands;
	}

}
