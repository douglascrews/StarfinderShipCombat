package com.dougcrews.game.starfinder.model.ship.component;

public class DuplicateComponentException extends Exception
{
	private static final long serialVersionUID = -6886440464538201553L;

	public DuplicateComponentException(String message)
	{
		super(message);
	}
}
