package org.kwstudios.play.kwbungeeredislistener.toolbox;

import org.kwstudios.play.kwbungeeredislistener.App;

public class ShutdownManager {

	public static void registerShutdownHandler() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				App.getJedisChannelListener().getJedisPubSub().unsubscribe();
				App.getJedisPool().destroy();
			}
		});
	}

}
