package org.kwstudios.play.kwbungeeredislistener.listener;

import org.kwstudios.play.kwbungeeredislistener.App;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Protocol;

public abstract class JedisMessageListener {

	private JedisPubSub jedisPubSub;
	private String server;
	private int port;
	private String password;
	private String[] channel;

	public JedisMessageListener(String server, int port, String password, String... channel) {
		this.server = server;
		this.port = port;
		this.password = password;
		this.channel = channel;
		jedisPubSub = setupSubscriber();
	}

	public JedisMessageListener(String server, String password, String... channel) {
		this(server, Protocol.DEFAULT_PORT, password, channel);
	}

	public JedisMessageListener(String server, String[] channel) {
		this(server, Protocol.DEFAULT_PORT, null, channel);
	}

	public JedisMessageListener(String server, int port, String[] channel) {
		this(server, port, null, channel);
	}

	public abstract void taskOnMessageReceive(String channel, String message);

	private JedisPubSub setupSubscriber() {
		final JedisPubSub jedisPubSub = new JedisPubSub() {
			@Override
			public void onUnsubscribe(String channel, int subscribedChannels) {
				App.getLogger().info("Jedis unsubscribed successfully from the Redis Host!");
			}

			@Override
			public void onSubscribe(String channel, int subscribedChannels) {
				App.getLogger().info("Jedis subscribed successfully at the Redis Host!");
			}

			@Override
			public void onPUnsubscribe(String pattern, int subscribedChannels) {
			}

			@Override
			public void onPSubscribe(String pattern, int subscribedChannels) {
			}

			@Override
			public void onPMessage(String pattern, String channel, String message) {
			}

			@Override
			public void onMessage(String channel, String message) {
				App.getLogger().info("Jedis received a new message from the Redis Host!");
				App.getLogger().info(channel);
				App.getLogger().info(message);
				taskOnMessageReceive(channel, message);
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					App.getLogger().info("Jedis is connecting to the Redis Host!");
					Jedis jedis = new Jedis(server, port);
					if (password != null) {
						jedis.auth(password);
					}
					App.getLogger().info("Jedis is subscribing for a channel at the Redis Host!");
					jedis.subscribe(jedisPubSub, channel);
					jedis.quit();
					// jedis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		return jedisPubSub;
	}

	public JedisPubSub getJedisPubSub() {
		return jedisPubSub;
	}

}
