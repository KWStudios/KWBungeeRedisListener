package org.kwstudios.play.kwbungeeredislistener.json;

import org.kwstudios.play.kwbungeeredislistener.json.jedis.JedisSettings;

public class Settings {

	private JedisSettings jedis;

	public JedisSettings getJedis() {
		return jedis;
	}

	public void setJedis(JedisSettings jedis) {
		this.jedis = jedis;
	}

}
