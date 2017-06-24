package controller.commands;

import model.iModel;

/**
 * The Class LoadLevelCommand - load a level.
 */
public class LoadLevelCommand extends Command
{
	private iModel model;

	public LoadLevelCommand(iModel model)
	{
		this.model = model;
	}

	@Override
	public void execute()
	{
		this.model.loadLevel(getParams());
	}
}
