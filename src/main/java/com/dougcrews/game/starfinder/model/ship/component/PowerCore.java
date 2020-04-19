package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class PowerCore extends ShipComponent
{
	// Constructors
	
	public PowerCore(Ship ship)
	{
		super(ship);
	}
	
	public PowerCore(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
	}

	// Getters & Setters

	@Override
	public String getGenericName()
	{
		return "Power Core";
	}
	
	@Override
	public String getDescription()
	{
		return "Power Cores provide 10 PCUs of power for components. A damaged Power Core will provide less power in proportion to its remaining Hull Points.";
	}

	@Override
	public int getDefaultHullPoints()
	{
		return 10;
	}
	
	/**
	 * Power Cores provide power instead of using it. Damaged power cores provide proportionally less power.
	 */
	@Override
	public int getPowerRequired()
	{
		return (this.enabled ? -(this.getCurrHP()) : 0); // Negative power required == power available
	}
	
	@Override
	public void setCurrHP(int currHP)
	{
		boolean isDamaged = (currHP < this.currHP);
		this.currHP = Math.max(currHP, 0);
		if (isDamaged)
		{
			this.ship.onPowerCoreDamaged();
		}
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

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getPowerCoreBonus();
	}
}
