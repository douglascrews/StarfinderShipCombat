package com.dougcrews.game.starfinder.model.intent;

public class IntentFailedException extends Exception
{
	private static final long serialVersionUID = 1415628157579822973L;

	public IntentFailedException(String msg)
	{
		super(msg);
	}
}
