package controller.commands;

import javafx.beans.property.StringProperty;
import model.iModel;

/**
 * The Class MoveCommand - move a movable item from one position to another.
 */
public class MoveCommand extends Command
{
	private iModel model;
	private StringProperty countSteps;
	
	public MoveCommand(iModel model, StringProperty countSteps)
	{
		this.model = model;
		this.countSteps = countSteps;
	}

	@Override
	public void execute()
	{
		this.model.move(getParams());
		int steps = this.model.getSteps();
		this.countSteps.set(""+(steps));
	}
}
