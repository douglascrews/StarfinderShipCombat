package com.dougcrews.game.starfinder.model.intent;

public class ActionNotAvailableException extends Exception
{
	private static final long serialVersionUID = -7300561511172554699L;

	public ActionNotAvailableException(String message)
	{
		super(message);
	}
}
