package controller;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import controller.commands.AddRecordCommand;
import controller.commands.AddUserCommand;
import controller.commands.DisplayCLICommand;
import controller.commands.DisplayGUICommand;
import controller.commands.ErrorCommand;
import controller.commands.ExitCommand;
import controller.commands.LoadLevelCommand;
import controller.commands.MoveCommand;
import controller.commands.QueryCommand;
import controller.commands.SaveLevelCommand;
import controller.commands.ShowDBRecordCommand;
import controller.commands.iCommand;
import controller.server.MyServer;
import controller.server.SokobanClientHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.iModel;
import view.iView;

/*
 * SokobanControllrt - a spesipic class that create all the attributes the SOKOBAN controller needs.
 */
public class SokobanController implements Observer
{
	private CommandController controller;
	private iModel model;
	private iView view;
	private HashMap<String, iCommand> commands;
	private StringProperty countSteps;
	private SokobanClientHandler clientHandler;
	private MyServer theServer;
	
	/*
	 * SokobanController - ordinary CTOR - running without a server&client.
	 */
	public SokobanController(iModel model, iView view) 
	{
		this.model = model;
		this.view = view;
		this.controller = new CommandController();
		this.countSteps = new SimpleStringProperty();
		initCommands();
		this.controller.start();
		this.view.createBindSteps(this.countSteps);
	}

	/*
	 * SokobanController - special CTOR - running with a server&client.
	 */
	public SokobanController(iModel model, iView view, SokobanClientHandler clientHandler, int port) 
	{
		this.model = model;
		this.view = view;
		this.controller = new CommandController();
		this.countSteps = new SimpleStringProperty();
		this.clientHandler = clientHandler;
		this.theServer = new MyServer(port, this.clientHandler);
		this.theServer.start();
		initCommands();
		this.controller.start();
		this.view.createBindSteps(this.countSteps);
	}
	
	/*
	 * InitCommands - init the hash command.
	 */
	private void initCommands()
	{
		this.commands = new HashMap<>();
		this.commands.put("load", new LoadLevelCommand(this.model));
		this.commands.put("display", new DisplayCLICommand(this.model, this.clientHandler));
		this.commands.put("move", new MoveCommand(this.model, this.countSteps));
		this.commands.put("save", new SaveLevelCommand(this.model));
		this.commands.put("exit", new ExitCommand(this.controller, this.theServer));
		this.commands.put("change", new DisplayGUICommand(this.model, this.view));
		this.commands.put("error", new ErrorCommand(this.view, this.clientHandler));
		this.commands.put("query", new QueryCommand(this.model));
		this.commands.put("showdbresults", new ShowDBRecordCommand(this.model, this.view));
		this.commands.put("adduser", new AddUserCommand(this.model));
		this.commands.put("addrecord", new AddRecordCommand(this.model));

	}
	
	/*
	 * ObjectToString - convert an object to a string.
	 */
	private String[] objectToString(Object arg)
	{
		String[] input;
		String command = (String)arg;
		input = command.split(" ", 2);
		return input;
	}
	
	@Override
	public void update(Observable o, Object arg) 
	{				
		String[] input = objectToString(arg);
		String commandName = input[0];
		String params = null;	
		if(input.length > 1)
			params = input[1];
		
		iCommand command = this.commands.get(commandName.toLowerCase());
		
		if(command == null)
		{
			this.view.displayError("Command " + commandName + " not found.");
			if(this.clientHandler != null)
				this.clientHandler.insertToMessageQueue("Command " + commandName + " not found.");
			return;
		}
		
		command.setParams(params);

		try 
		{
			controller.insertCommand(command);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}						
	}

	public CommandController getCommandController() 
	{
		return controller;
	}

	public iModel getIModel() 
	{
		return model;
	}

	public iView getIView() 
	{
		return view;
	}
}
