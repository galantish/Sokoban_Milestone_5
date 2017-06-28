package controller.commands;

import model.iModel;

public class RestartCommand extends Command
{
	private iModel model;
	
	public RestartCommand(iModel model) 
	{
		this.model = model;
	}
	
	@Override
	public void execute() 
	{
		this.model.restart();
	}

}
