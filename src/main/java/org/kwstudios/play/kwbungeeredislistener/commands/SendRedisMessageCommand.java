package org.kwstudios.play.kwbungeeredislistener.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kwstudios.play.kwbungeeredislistener.App;
import org.kwstudios.play.kwbungeeredislistener.sender.JedisMessageSender;

public class SendRedisMessageCommand implements ICommand {

	@Override
	public String getLabel() {
		return "send";
	}

	@Override
	public ArrayList<String> getArguments() {
		String[] args = new String[] { "--host", "--port", "--password", "--help" };
		return new ArrayList<String>(Arrays.asList(args));
	}

	@Override
	public ArrayList<String> getDescription() {
		String first = "This command sends a message to the given channel.";
		String second = "Usage: send [--host=<host>] [--port=<port>] [--password=<password>] <channel> <message>";
		String third = "Options: <none>";
		ArrayList<String> list = new ArrayList<String>();
		list.add(first);
		list.add(second);
		list.add(third);
		return list;
	}

	@Override
	public boolean execute(List<String> args) {
		String host = App.getSettings().getJedis().getHost();
		int port = App.getSettings().getJedis().getPort();
		String password = App.getSettings().getJedis().getPassword();

		List<String> arguments = new ArrayList<String>();
		String channel = null;
		String message = null;
		for (String arg : args) {
			if (arg.trim().toLowerCase().startsWith("--host")) {
				if (!arguments.isEmpty()) {
					return false;
				}
				String[] hostArray = arg.split("=");
				if (hostArray.length < 2) {
					return false;
				}
				host = hostArray[1];
			} else if (arg.trim().toLowerCase().startsWith("--port")) {
				if (!arguments.isEmpty()) {
					return false;
				}
				String[] portArray = arg.split("=");
				if (portArray.length < 2) {
					return false;
				}
				try {
					port = Integer.parseInt(portArray[1]);
				} catch (NumberFormatException e) {
					return false;
				}
			} else if (arg.trim().toLowerCase().startsWith("--password")) {
				if (!arguments.isEmpty()) {
					return false;
				}
				String[] passwordArray = arg.split("=");
				if (passwordArray.length < 2) {
					return false;
				}
				password = passwordArray[1];
			} else {
				if (arg.trim().toLowerCase().startsWith("--")) {
					return false;
				}
				arguments.add(arg);

				if (arguments.size() > 2) {
					return false;
				}
			}
		}

		if (arguments.size() != 2) {
			return false;
		}

		channel = arguments.get(0);
		message = arguments.get(1);

		JedisMessageSender.sendMessageToChannel(host, port, password, channel, message);

		return true;
	}

}
