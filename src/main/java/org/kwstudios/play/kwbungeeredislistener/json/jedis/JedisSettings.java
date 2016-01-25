package org.kwstudios.play.kwbungeeredislistener.json.jedis;

import redis.clients.jedis.Protocol;

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
		return (host == null) ? Protocol.DEFAULT_HOST : host;
	}

	public String getPassword() {
		return (password == null) ? "" : password;
	}

	public String[] getChannelsToListen() {
		return (channels_to_listen == null) ? new String[] {} : channels_to_listen;
	}

	public int getPort() {
		return (port == 0) ? Protocol.DEFAULT_PORT : port;
	}

}
