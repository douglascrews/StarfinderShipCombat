package com.dougcrews.game.starfinder.model.ship.component.weapon;

public class MissileAlreadyLoadedException extends Exception
{
	private static final long serialVersionUID = -6685258788367179385L;

	public MissileAlreadyLoadedException(String msg)
	{
		super(msg);
	}
}
