package org.kwstudios.play.kwbungeeredislistener.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.kwstudios.play.kwbungeeredislistener.App;
import org.kwstudios.play.kwbungeeredislistener.commands.docs.ICommandDocs;
import org.kwstudios.play.kwbungeeredislistener.commands.docs.ShutdownCommandDocs;

public class ShutdownCommand implements ICommand {

	private ICommandDocs documentation;

	public ShutdownCommand() {
		documentation = new ShutdownCommandDocs();
	}

	@Override
	public String getLabel() {
		return "end";
	}

	@Override
	public ArrayList<String> getArguments() {
		String[] args = { "--help" };
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
		App.getJedisChannelListener().getJedisPubSub().unsubscribe();
		System.exit(0);
		return true;
	}

}
