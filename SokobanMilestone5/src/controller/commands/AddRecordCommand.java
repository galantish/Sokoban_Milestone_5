package controller.commands;

import model.iModel;

public class AddRecordCommand extends Command
{
	private iModel model;
	
	public AddRecordCommand(iModel model) 
	{
		this.model = model;
	}
	
	@Override
	public void execute() 
	{
		this.model.addRecordToDB(getParams());
	}
}