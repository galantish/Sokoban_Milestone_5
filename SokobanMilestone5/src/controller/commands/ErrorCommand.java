package controller.commands;

import controller.server.SokobanClientHandler;
import view.iView;

public class ErrorCommand extends Command
{
	private iView view;
	private SokobanClientHandler clientHandler;
	
	public ErrorCommand(iView view, SokobanClientHandler clientHandler) 
	{
		this.view = view;
		this.clientHandler = clientHandler;
	}
	
	@Override
	public void execute() 
	{
		String error = getParams();
		this.view.displayError(error);
		if(this.clientHandler != null)
			this.clientHandler.insertToMessageQueue(error);
	}
}
