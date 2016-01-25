package org.kwstudios.play.kwbungeeredislistener.json.jedis;

public class JedisSettings {

	private String host;
	private String password;
	private String[] channels_to_listen;

	private int port;

	public JedisSettings(String host, String password, String[] channels_to_listen, int port) {
		super();
		this.host = host;
		this.password = password;
		this.channels_to_listen = channels_to_listen;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public String getPassword() {
		return password;
	}

	public String[] getChannelsToListen() {
		return channels_to_listen;
	}

	public int getPort() {
		return port;
	}

}
