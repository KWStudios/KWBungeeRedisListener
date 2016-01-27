package org.kwstudios.play.kwbungeeredislistener.minigames;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kwstudios.play.kwbungeeredislistener.App;
import org.kwstudios.play.kwbungeeredislistener.json.CreationRequest;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;
import com.google.gson.Gson;

public class MinigameRequests {

	public static boolean startRequestedServer(String message) {
		Gson gson = new Gson();
		final CreationRequest server;
		try {
			server = gson.fromJson(message, CreationRequest.class);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if (server.getName() == null || server.getType() == null || server.getMap() == null) {
			return false;
		}

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("It starts the asynchronous scheduler!");
				if (MinigameRequests.isLocalServer(server)) {
					System.out.println("It is a local server!");
					String command = App.getSettings().getMinigames().getCommand();
					String commands[] = command.trim().split("\\s+");

					ProcessBuilder builder = new ProcessBuilder(commands);
					Map<String, String> map = builder.environment();
					map.put("GAME_TYPE", server.getType());
					map.put("MAP_NAME", server.getMap());
					map.put("SERVER_NAME", server.getName());
					try {
						builder.start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();

		return true;
	}

	/**
	 * Returns true if the given MinecraftServerModel is a local server. More
	 * formally, returns true iff the Range r, which is a closed Range for the
	 * <i>lower</i> and <i>upper</i> values ({@code [lower..upper]} {@code {x
	 * |lower <= x <= upper}}) which should be set in the config.yml contains
	 * the Integer {@code server.getNumber()} n AND was not excluded in the
	 * {@code Set} e which should be set in the config.yml OR was included in
	 * the {@code Set} i which should also be set in the config.yml such that:
	 * <p>
	 * {@code ((r.contains(n) && !e.contains(n)) || i.contains(n)) == true}
	 * 
	 * @param server
	 * @return
	 */
	public static boolean isLocalServer(CreationRequest server) {
		int lower = App.getSettings().getMinigames().getLocalServer().getLower();
		int upper = App.getSettings().getMinigames().getLocalServer().getUpper();

		List<Integer> excludeList = Ints.asList(App.getSettings().getMinigames().getLocalServer().getExclude());
		Set<Integer> exclude = ImmutableSet.copyOf(excludeList);
		List<Integer> includeList = Ints.asList(App.getSettings().getMinigames().getLocalServer().getInclude());
		Set<Integer> include = ImmutableSet.copyOf(includeList);

		Range<Integer> range = Range.closed(lower, upper);

		if ((range.contains(server.getNumber()) && !exclude.contains(server.getNumber()))
				|| include.contains(server.getNumber())) {
			return true;
		}
		return false;
	}

}
