package controller.commands;

import model.iModel;

/**
 * The Class SaveLevelCommand - save a level.
 */
public class SaveLevelCommand extends Command
{
	private iModel model;

	public SaveLevelCommand(iModel model)
	{
		this.model = model;
	}

	@Override
	public void execute()
	{
		this.model.saveLevel(getParams());
	}
}
