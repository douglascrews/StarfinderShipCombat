package com.dougcrews.game.starfinder.model.ship.component;

public class InsufficientPowerException extends Exception
{
	private static final long serialVersionUID = -6886440464538201553L;

	public InsufficientPowerException(String message)
	{
		super(message);
	}
}
