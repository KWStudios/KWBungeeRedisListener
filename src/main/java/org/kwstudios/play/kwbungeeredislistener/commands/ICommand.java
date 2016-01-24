package org.kwstudios.play.kwbungeeredislistener.commands;

import java.util.ArrayList;

public interface ICommand {

	/**
	 * Returns the first element, or the Commands specific label, of the
	 * command.
	 * 
	 * @return The label of the command.
	 */
	public String getLabel();

	/**
	 * Returns the ArrayList which holds all possible arguments for the Command.
	 * 
	 * @return The ArrayList with all possible arguments.
	 */
	public ArrayList<String> getArguments();

	/**
	 * Starts executing the Command with the given arguments.
	 * <p>
	 * Returns {@code false} iff the execution fails instantly, else
	 * {@code true}.
	 * <p>
	 * For example if a Command executes in the Background, the status cannot be
	 * tracked and this will return {@code true}.
	 * 
	 * @param args
	 *            The ArrayList with all arguments which were entered to the
	 *            Console.
	 * 
	 * @return False iff the execution fails, else true.
	 */
	public boolean execute(ArrayList<String> args);

}
