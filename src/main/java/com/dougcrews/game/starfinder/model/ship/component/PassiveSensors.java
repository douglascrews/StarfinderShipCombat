package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class PassiveSensors extends ShipComponent
{

	@Override
	public String getGenericName()
	{
		return "Passive Sensors";
	}
	
	@Override
	public String getDescription()
	{
		return "Passive Sensors provide sensor readings of the surrounding area. Results are immediate thanks to subspace technology, but are limited to Intrasystem range. Anything farther than Beyond Extreme range is effectively invisible due to relativistic limitations. Each Passive Sensor after the first extends the range of all installed Passive Sensors, to a limit of Extreme range, as well as redundancy and additional bonuses to Passive Sensor actions. A ship with no working Passive Sensors or Sensor Arrays can only detect and target objects in the Point Blank range. Use of Passive Sensors does not alert other ships to your presence.";
	}

	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		Set<PlayerIntent> intents = this.getDefaultIntents(targetShip);
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
//			for (Ship ship : this.getShip().getDetectedShips())
//			{
//			}
//			for (ShipComponent component : this.getShip().getDetectedComponents().keySet())
//			{
//			}
			// Done elsewhere: IntentPassiveScanEachRound
			// TODO: IntentSubspaceScan
		}
		return intents;
	}
	
	@Override
	public Set<ReactionIntent> getPossibleReactions()
	{
		return super.getDefaultReactions();
	}
	
	// Constructors
	
	public PassiveSensors(Ship ship)
	{
		super(ship);
	}
	
	public PassiveSensors(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
	}

	// Getters & Setters

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getPassiveSensorsBonus();
	}
	
	@Override
	public String getConsoleStatus()
	{
		String status = super.getConsoleStatus();
		return status + " Max Range: " + this.ship.getPassiveSensorRange().getDescription();
	}
}
