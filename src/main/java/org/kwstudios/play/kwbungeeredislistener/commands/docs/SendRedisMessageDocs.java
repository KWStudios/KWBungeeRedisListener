package org.kwstudios.play.kwbungeeredislistener.commands.docs;

import org.docopt.Docopt;

public class SendRedisMessageDocs implements ICommandDocs {

	private final String doc = "KWStudios\n" + "\n" + "Usage:\n"
			+ "  kw send [--host=<host>] [--port=<port>] [--password=<password>] <channel> <message>\n"
			+ "  kw send (-h | --help)\n" + "\n" + "Options:\n" + "  -h --help               Show this screen.\n"
			+ "  --host=<host>           Redis host for this message.\n"
			+ "  --port=<port>           Redis port for this message.\n"
			+ "  --password=<password>   Moored (anchored) mine.\n" + "\n";

	private Docopt docopt;

	public SendRedisMessageDocs() {
		docopt = new Docopt(doc);
		docopt.withExit(false);
		docopt.withHelp(false);
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
