package com.dougcrews.game.starfinder.model;

import java.security.InvalidParameterException;

import com.dougcrews.game.starfinder.model.ship.Ship;

/**
 * Non Player Character
 */
public class NPC extends Player {

	public NPC(String name, Ship ship) throws InvalidParameterException
	{
		super(name, ship);
	}
}
