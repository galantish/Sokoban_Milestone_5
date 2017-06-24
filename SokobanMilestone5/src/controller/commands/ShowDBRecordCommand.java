package controller.commands;

import model.iModel;
import view.iView;

public class ShowDBRecordCommand extends Command
{
	private iModel model;
	private iView view;
	
	public ShowDBRecordCommand(iModel model, iView view) 
	{
		super();
		this.model = model;
		this.view = view;
	}
	
	@Override
	public void execute() 
	{
		this.view.showDBRecords(this.model.getCurrentRecordList());
	}

}
