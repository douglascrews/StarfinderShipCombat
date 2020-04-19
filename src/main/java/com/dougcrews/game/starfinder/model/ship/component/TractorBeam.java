package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class TractorBeam extends ShipComponent
{
	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger(TractorBeam.class.getName());

	@Override
	public String getGenericName()
	{
		return "Tractor Beam";
	}

	@Override
	public String getDescription()
	{
		return "A Tractor Beam is often installed on large military ships and asteroid miners. They can be used to slow or immobilize enemy ships. Multiple devices increase the effective range and the potency of all installed Tractor Beams.";
	}

	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		Set<PlayerIntent> intents = this.getDefaultIntents(targetShip);
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
//			for (Ship ship : this.getShip().getDetectedShips())
			{
				// TODO: IntentLockOn
				// TODO: IntentRelease
			}
//			for (ShipComponent component : this.getShip().getDetectedComponents().keySet())
//			{
//			}
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
	
	public TractorBeam(Ship ship)
	{
		super(ship);
	}

	public TractorBeam(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
	}

	// Getters & Setters

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getTractorBeamBonus();
	}
	
	@Override
	public String getConsoleStatus()
	{
		String status = super.getConsoleStatus();
		return status + " Max Range: " + this.ship.getTractorBeamRange().getDescription();
	}
}
