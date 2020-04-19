package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class Thrusters extends ShipComponent
{
	// Constructors
	
	public Thrusters(Ship ship)
	{
		super(ship);
	}
	
	public Thrusters(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
	}

	// Getters & Setters

	@Override
	public String getGenericName()
	{
		return "Thrusters";
	}

	@Override
	public String getDescription()
	{
		return "Thrusters provide maneuverability and tighter turn radius. A ship without working Thrusters is unable to turn, spin, bank, or yaw. Thrusters do not provide speed (Engines are required for that).";
	}

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getThrustersBonus();
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
}
