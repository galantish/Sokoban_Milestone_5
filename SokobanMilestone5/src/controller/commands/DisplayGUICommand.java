package controller.commands;

import model.iModel;
import view.iView;

/**
 * The Class DisplayGUICommand - display the level to the console.
 */
public class DisplayGUICommand extends Command 
{
	private iModel model;
	private iView view;
	
	public DisplayGUICommand(iModel model, iView view) 
	{
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void execute() 
	{
		view.displayLevel(model.getCurrentLevel());
	}

}
