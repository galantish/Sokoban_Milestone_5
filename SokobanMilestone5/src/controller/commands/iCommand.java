package controller.commands;

/**
 * iCommand - an interface that holds all the function that a command should implement.
 */
public interface iCommand 
{
	public void execute();
	public void setParams(String params);
}
