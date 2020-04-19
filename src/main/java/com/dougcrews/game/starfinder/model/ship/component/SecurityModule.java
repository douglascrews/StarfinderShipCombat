package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class SecurityModule extends ShipComponent
{
	@Override
	public String getGenericName()
	{
		return "Security Module";
	}

	@Override
	public String getDescription()
	{
		return "Security Modules increase overall resistance to hacking & espionage attempts and allow certain Reactions to enemy attacks.";
	}
	
	// Constructors
	
	public SecurityModule(Ship ship)
	{
		super(ship);
	}

	// Getters & Setters

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getSecurityModuleBonus();
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
