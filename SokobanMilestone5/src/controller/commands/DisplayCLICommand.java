package controller.commands;

import controller.server.SokobanClientHandler;
import model.iModel;

/**
 * The Class DisplayCLICommand - display the level to a client.
 */
public class DisplayCLICommand extends Command
{	
	private iModel model;
	private SokobanClientHandler clientHandler;

	public DisplayCLICommand(iModel model, SokobanClientHandler clientHandler)
	{
		this.model = model;
		this.clientHandler = clientHandler;
	}
	
	@Override
	public void execute()
	{
		if(this.model.getCurrentLevel().isEmpty() == true)
			this.clientHandler.insertToMessageQueue("You need to load level first.");
		else
			this.clientHandler.sendLevel(this.model.getCurrentLevel().getLevelBoard());
	}
}