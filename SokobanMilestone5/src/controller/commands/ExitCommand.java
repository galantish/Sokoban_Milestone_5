package controller.commands;

import controller.CommandController;
import controller.server.MyServer;

/**
 * The Class ExitCommand - exit the program in an ordery way (close threads and loops).
 */
public class ExitCommand extends Command
{
	private CommandController controller;
	private MyServer theServer;
	
	public ExitCommand(CommandController controller, MyServer theServer) 
	{
		this.controller = controller;
		this.theServer = theServer;
	}
	
	@Override
	public void execute()
	{
		this.controller.stop();
		//If the server is open - stop it.
		if(this.theServer != null)
			this.theServer.stop();
	}

}