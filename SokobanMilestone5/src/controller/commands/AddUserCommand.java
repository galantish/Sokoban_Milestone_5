package controller.commands;

import model.iModel;

public class AddUserCommand extends Command
{
	private iModel model;
	
	public AddUserCommand(iModel model) 
	{
		this.model = model;
	}
	
	@Override
	public void execute() 
	{
		this.model.addUserToDB(getParams());
	}

}
