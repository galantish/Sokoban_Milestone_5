package controller.commands;

import model.iModel;

public class QueryCommand extends Command
{
	private iModel model;
	
	public QueryCommand(iModel model) 
	{
		this.model = model;
	}
	
	@Override
	public void execute() 
	{
		this.model.createQuery(getParams());
	}

}
