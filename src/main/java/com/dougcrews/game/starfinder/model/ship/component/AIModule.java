package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class AIModule extends ShipComponent
{
	@Override
	public String getGenericName()
	{
		return "A.I. Module";
	}
	
	@Override
	public String getDescription()
	{
		// TODO: Implement automatic Flak firing, since we're promising it here.
		return "A Governing Expert Entity Module (GEE-M) provides general-purpose computing power to assist the crew with a multitude of actions. If your pilot is absent or incapacitated, the GEE-M will take over. It will passively search the area for stealthed ships, and if you have a Flak module, it will automatically fire at the nearest missile if a crewmember does not.";
	}

	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		return this.getDefaultIntents(targetShip);
	}

	@Override
	public Set<ReactionIntent> getPossibleReactions()
	{
		return this.getDefaultReactions();
	}

	// Intents
	
	// Constructors
	
	public AIModule(Ship ship)
	{
		super(ship);
	}
	
	public AIModule(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
	}

	// Getters & Setters

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getAIBonus();
	}
}
