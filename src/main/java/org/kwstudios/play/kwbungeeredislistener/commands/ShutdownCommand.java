package org.kwstudios.play.kwbungeeredislistener.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kwstudios.play.kwbungeeredislistener.App;

public class ShutdownCommand implements ICommand {

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
		String first = "This command shuts down the application, quitting everything associated with it.";
		String second = "Usage: end";
		String third = "Options: <none>";
		ArrayList<String> list = new ArrayList<String>();
		list.add(first);
		list.add(second);
		list.add(third);
		return list;
	}

	@Override
	public boolean execute(List<String> args) {
		App.getJedisChannelListener().getJedisPubSub().unsubscribe();
		System.exit(0);
		return true;
	}

}
