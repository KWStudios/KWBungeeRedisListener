package org.kwstudios.play.kwbungeeredislistener.json;

import org.kwstudios.play.kwbungeeredislistener.json.jedis.JedisSettings;

import redis.clients.jedis.Protocol;

public class Settings {

	private JedisSettings jedis;
	private MinigameSettings minigames;

	public Settings() {
		jedis = (jedis == null) ? new JedisSettings(Protocol.DEFAULT_HOST, "",
				new String[] { "minigame-server-creation" }, Protocol.DEFAULT_PORT) : jedis;
		minigames = (minigames == null)
				? new MinigameSettings("ruby test.rb", new LocalServer(0, 0, new int[] {}, new int[] {})) : minigames;
	}

	public JedisSettings getJedis() {
		return jedis;
	}

	public void setJedis(JedisSettings jedis) {
		this.jedis = jedis;
	}

	public MinigameSettings getMinigames() {
		return minigames;
	}

	public void setMinigames(MinigameSettings minigames) {
		this.minigames = minigames;
	}

}
