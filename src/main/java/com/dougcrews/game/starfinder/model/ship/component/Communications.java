package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class Communications extends ShipComponent
{
	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger(Communications.class.getName());

	@Override
	public String getGenericName()
	{
		return "Communications";
	}

	@Override
	public String getDescription()
	{
		return "A Communications rig is civilian-grade communications, necessary for intership communications, distress beacons, intersystem messaging, registered vessel identification, and InfoSphere access, and can be found in virtually every ship in active service. Multiple devices do not increase range (which is Beyond Extreme locally, and interstellar to home base or planet), but do increase redundancy.";
	}


	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		Set<PlayerIntent> intents = this.getDefaultIntents(targetShip);
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
//			for (Ship ship : this.getShip().getDetectedShips())
			{
				// TODO: IntentTaunt
				// TODO: IntentBluff
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
	
	public Communications(Ship ship)
	{
		super(ship);
	}

	public Communications(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
	}

	// Getters & Setters

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getCommunicationsBonus();
	}
	
	@Override
	public String getConsoleStatus()
	{
		String status = super.getConsoleStatus();
		return status + " Max Range: " + this.ship.getCommunicationsRange().getDescription();
	}
}
