package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class SensorArray extends ShipComponent
{

	@Override
	public String getGenericName()
	{
		return "Sensor Array";
	}

	@Override
	public String getDescription()
	{
		return "Sensor Arrays provide active sensor readings of the surrounding area. Results are immediate thanks to subspace technology, but are limited to Intrasystem range. Anything farther than Beyond Extreme range is effectively invisible due to relativistic limitations. Each Sensor Array after the first extends the range of all installed Sensor Arrays, to a limit of Extreme range, as well as redundancy and additional bonuses to Sensor Array actions. A ship with no working Passive Sensors or Sensor Arrays can only detect and target objects in the Point Blank range. Use of Sensor Arrays WILL alert other ships to your presence.";
	}

	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		Set<PlayerIntent> intents = this.getDefaultIntents(targetShip);
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
//			for (Ship ship : this.getShip().getDetectedShips())
			{
				// TODO: IntentSensorScan
				// TODO: IntentHullScan
				// TODO: IntentLifeScan
				// TODO: IntentTachyonScan
			}
//			for (ShipComponent component : this.getShip().getDetectedComponents().keySet())
			{
				// TODO: IntentInfect
				// TODO: IntentControl
			}
//			for (ShipComponent component : this.getShip().getComponents())
			{
// TODO:				if (component.getControlledBy() != null)
				{
					// TODO: IntentRegainControl
				}
				// TODO: IntentInfect
				// TODO: IntentControl
			}
			// TODO: IntentSonarScan
			// TODO: IntentScanForIntruders
		}
		return intents;
	}

	@Override
	public Set<ReactionIntent> getPossibleReactions()
	{
		Set<ReactionIntent> intents = this.getDefaultReactions();
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
//			for (Ship ship : this.getShip().getDetectedShips())
			{
				// TODO: IntentJam
			}
//			for (ShipComponent component : this.getShip().getDetectedComponents().keySet())
//			{
//			}
//			for (Missile missile : this.getShip().getDetectedMissiles())
			{
				// TODO: IntentDetonate
			}
		}
		return intents;
	}

	// Intents

	// Constructors
	
	public SensorArray(Ship ship)
	{
		super(ship);
	}
	
	public SensorArray(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
	}

	// Getters & Setters

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getSensorArrayBonus();
	}
	
	@Override
	public String getConsoleStatus()
	{
		String status = super.getConsoleStatus();
		return status + " Max Range: " + this.ship.getSensorArrayRange().getDescription();
	}
}
