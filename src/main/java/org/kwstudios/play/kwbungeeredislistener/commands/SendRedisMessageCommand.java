package org.kwstudios.play.kwbungeeredislistener.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.kwstudios.play.kwbungeeredislistener.App;
import org.kwstudios.play.kwbungeeredislistener.commands.docs.ICommandDocs;
import org.kwstudios.play.kwbungeeredislistener.commands.docs.SendRedisMessageDocs;
import org.kwstudios.play.kwbungeeredislistener.sender.JedisMessageSender;

public class SendRedisMessageCommand implements ICommand {

	private ICommandDocs documentation;

	public SendRedisMessageCommand() {
		this.documentation = new SendRedisMessageDocs();
	}

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
		return new ArrayList<String>(Arrays.asList(documentation.getDoc().split("\\n")));
	}

	@Override
	public ICommandDocs getCommandDocs() {
		return documentation;
	}

	@Override
	public boolean execute(Map<String, Object> args) {
		String host = (args.get("--host") instanceof String) ? (String) args.get("--host")
				: App.getSettings().getJedis().getHost();
		int port = (args.get("--port") instanceof Integer) ? (Integer) args.get("--port")
				: App.getSettings().getJedis().getPort();
		String password = (args.get("--password") instanceof String) ? (String) args.get("--password")
				: App.getSettings().getJedis().getPassword();

		String channel = (args.get("<channel>") instanceof String) ? (String) args.get("<channel>") : null;
		String message = (args.get("<message>") instanceof String) ? (String) args.get("<message>") : null;
		;

		if (channel != null && message != null) {
			JedisMessageSender.sendMessageToChannel(host, port, password, channel, message);
			return true;
		}

		return false;
	}

}
