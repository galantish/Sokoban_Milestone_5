package controller.commands;

/**
 * Command - an abstract class that includes the common attributes of some command.
 */
public abstract class Command implements iCommand
{
	private String params;
	
	@Override
	public void setParams(String params)
	{
		this.params = params;
	}
	
	/**GetParams.
	 * 
	 * @return the command's parameters
	 */
	public String getParams()
	{
		return this.params;
	}
	
}
