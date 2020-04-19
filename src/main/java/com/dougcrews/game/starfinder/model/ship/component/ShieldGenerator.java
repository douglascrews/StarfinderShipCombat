package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

// TODO: Implement fore/aft shields
public class ShieldGenerator extends ShipComponent
{
	private static final Logger log = LogManager.getLogger(ShieldGenerator.class.getName());
	
	@Override
	public String getGenericName()
	{
		return "Shield Generator";
	}

	@Override
	public String getDescription()
	{
		return "Shield Generators provide protection against kinetic and energy weapons; missiles move too slow to be affected by them. Energy weapons deplete shields.";
	}

	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		Set<PlayerIntent> intents = this.getDefaultIntents(targetShip);
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
			// TODO: IntentBalanceShields
			// TODO: IntentOverchargeShields
		}
		return intents;
	}

	@Override
	public Set<ReactionIntent> getPossibleReactions()
	{
		return this.getDefaultReactions();
	}

	// Intents
	
	
	// Constructors
	
	public ShieldGenerator(Ship ship)
	{
		super(ship);
	}
	
	/**
	 * Putting Shields on a Shield Generator is not possible, so this option is not allowed.
	 */
	public ShieldGenerator(Ship ship, boolean armored, boolean hidden, boolean resistant)
	{
		super(ship, armored, false, hidden, resistant);
	}
	
	/**
	 * This is private because putting Shields on a Shield Generator does not make sense.
	 */
	private ShieldGenerator(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
		if (shielded)
		{
			log.warn("You can't put Shielding on Shield Generators. Ignoring this directive.");
		}
	}

	// Getters & Setters

	@Override
	public int getComponentBonus()
	{
		return 0;
	}
}
