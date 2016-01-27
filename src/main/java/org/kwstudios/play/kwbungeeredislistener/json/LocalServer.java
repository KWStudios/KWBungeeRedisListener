package org.kwstudios.play.kwbungeeredislistener.json;

public class LocalServer {

	private int lower;
	private int upper;

	private int[] exclude;
	private int[] include;

	public LocalServer(int lower, int upper, int[] exclude, int[] include) {
		super();
		this.lower = lower;
		this.upper = upper;
		this.exclude = exclude;
		this.include = include;
	}

	public int getLower() {
		return lower;
	}

	public int getUpper() {
		return upper;
	}

	public int[] getExclude() {
		return (exclude == null) ? new int[] {} : exclude;
	}

	public int[] getInclude() {
		return (include == null) ? new int[] {} : include;
	}

}
