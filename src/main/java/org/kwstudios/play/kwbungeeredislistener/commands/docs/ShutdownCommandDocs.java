package org.kwstudios.play.kwbungeeredislistener.commands.docs;

import org.docopt.Docopt;

public class ShutdownCommandDocs implements ICommandDocs {

	private final String doc = "KWStudios\n" + "\n" + "Usage:\n" + "  kw end\n\n" + "Description:\n"
			+ "This command ignores any arguments and shuts down instantly";

	private Docopt docopt;

	public ShutdownCommandDocs() {
		docopt = new Docopt(doc);
	}

	@Override
	public Docopt getDocopt() {
		return docopt;
	}

	@Override
	public String getDoc() {
		return doc;
	}

}
