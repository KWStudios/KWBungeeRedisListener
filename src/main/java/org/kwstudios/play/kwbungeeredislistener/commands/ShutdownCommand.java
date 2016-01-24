package org.kwstudios.play.kwbungeeredislistener.commands;

import java.util.ArrayList;
import java.util.Arrays;

public class ShutdownCommand implements ICommand {

	public String getLabel() {
		return "end";
	}

	public ArrayList<String> getArguments() {
		String[] args = {};
		return new ArrayList<String>(Arrays.asList(args));
	}

	public boolean execute(ArrayList<String> args) {
		System.exit(0);
		return true;
	}

}
