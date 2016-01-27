package org.kwstudios.play.kwbungeeredislistener.json;

public class MinigameSettings {

	private String command;
	private LocalServer local_server;

	public MinigameSettings(String command, LocalServer local_server) {
		super();
		this.command = command;
		this.local_server = local_server;
	}

	public String getCommand() {
		return (command == null) ? "ruby test.rb" : command;
	}

	public LocalServer getLocalServer() {
		return local_server;
	}

}
