package controller.commands;

import model.iModel;

public class SolveLevel extends Command
{
	private iModel model;
	
	public SolveLevel(iModel model) 
	{
		this.model = model;
	}
	
	@Override
	public void execute() 
	{
		this.model.getSolution();
	}

}
