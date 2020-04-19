package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class Engine extends ShipComponent
{
	@Override
	public String getGenericName()
	{
		return "Engine";
	}
	
	@Override
	public String getDescription()
	{
		return "Engines provide speed, but not manueverability (Thrusters provide that). Higher speed grants a bonus to Pilot initiative.";
	}
	
	// Constructors
	
	public Engine(Ship ship)
	{
		super(ship);
	}
	
	public Engine(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
	}

	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		Set<PlayerIntent> intents = super.getDefaultIntents(targetShip);
		return intents;
	}

	@Override
	public Set<ReactionIntent> getPossibleReactions()
	{
		Set<ReactionIntent> reactions = super.getDefaultReactions();
		return reactions;
	}

	// Getters & Setters

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getEngineBonus();
	}
}
