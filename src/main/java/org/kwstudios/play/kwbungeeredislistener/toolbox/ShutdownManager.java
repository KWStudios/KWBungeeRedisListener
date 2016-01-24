package org.kwstudios.play.kwbungeeredislistener.toolbox;

public class ShutdownManager {

	public static void registerShutdownHandler() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {

			}
		});
	}

}
