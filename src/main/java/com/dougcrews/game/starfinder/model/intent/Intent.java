package com.dougcrews.game.starfinder.model.intent;

public abstract class Intent
{
	protected final ActionType actionType;

	public void go()
	{
		if (before())
		{
			execute();
		}
		after();
	}
	
	/**
	 * @return true if the action can proceed; false if something prevents it
	 */
	protected boolean before()
	{
		return true;
	}
	
	/**
	 * Execute this Intent with the parameters provided when creating it.
	 */
	protected abstract void execute();
	
	protected void after()
	{
	}
	
	protected abstract String getName();
	
	@Override
	public String toString()
	{
		return this.actionType.getBadge() + this.getName(); 
	}

	// Constructors
	
	public Intent(ActionType actionType)
	{
		this.actionType = actionType;
	}

	// Getters & Setters
	
	public ActionType getActionType()
	{
		return this.actionType;
	}
}
