package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class Armor extends ShipComponent
{
	private static final Logger log = LogManager.getLogger(Armor.class.getName());
	
	@Override
	public String getGenericName()
	{
		return "Armor";
	}

	@Override
	public String getDescription()
	{
		return "Armor provides protection against kinetic and energy damage and is not damaged by them; missiles damage armor and pass the remaining damage through to the hull. Unlike other ship components, armor requires no power.";
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
	
	public Armor(Ship ship)
	{
		super(ship);
	}

	/**
	 * The only option that makes sense for Armor is whether it's hidden or not.
	 * @param ship where this component is installed
	 * @param hidden is this component camoflaged from Scanners?
	 */
	public Armor(Ship ship, boolean hidden)
	{
		super(ship, false, false, hidden, false);
	}

	/**
	 * This is private because armored, shielded, and resistant are useless for Armor.
	 */
	private Armor(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, false, false, hidden, false);
		if (armored)
		{
			log.warn("You can't put Armor on Armor. Ignoring this directive.");
		}
		if (shielded)
		{
			log.warn("You can't put Shielding on Armor. Ignoring this directive.");
		}
		if (resistant)
		{
			log.warn("There's no point in making Armor resistant to electronic countermeasures. Ignoring this directive.");
		}
	}

	// Getters & Setters

	/**
	 * Armor requires no power.
	 */
	@Override
	public int getPowerRequired()
	{
		return 0;
	}
	
	/**
	 * Armor consists of bonus Hull Points, one for one.
	 */
	@Override
	public int getDefaultHullPoints()
	{
		return 1;
	}

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getArmorBonus();
	}
}
