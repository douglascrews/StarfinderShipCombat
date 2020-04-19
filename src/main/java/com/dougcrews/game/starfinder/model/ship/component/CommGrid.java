package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class CommGrid extends ShipComponent
{
	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger(CommGrid.class.getName());

	@Override
	public String getGenericName()
	{
		return "Comm Grid";
	}

	@Override
	public String getDescription()
	{
		return "A Communications Grid is military-grade communications, capable of a multitude of offensive and defensive measures. Multiple instances increase both the effectiveness and range of all Comm Grids installed.";
	}

	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		Set<PlayerIntent> intents = this.getDefaultIntents(targetShip);
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
//			for (Ship ship : this.getShip().getDetectedShips())
			{
				// TODO: IntentJam
				// TODO: IntentEavesdrop
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
		Set<ReactionIntent> intents = this.getDefaultReactions();
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
//			for (Ship ship : this.getShip().getDetectedShips())
//			{
//			}
//			for (ShipComponent component : this.getShip().getDetectedComponents().keySet())
			{
				// TODO: IntentFeedbackLoop
				// TODO: IntentJam
				// TODO: IntentFalseInformation
			}
//			for (Missile missile : this.getShip().getDetectedMissiles())
			{
				// TODO: IntentImpede
			}
		}
		return intents;
	}

	// Intents

	/**
	 * d20+Computers+CommGrid vs. 20+Countermeasures+Jamming
	 * Specified ship may not communicate with other ships.
	 * Once successful, a jam may be broken using the victim's own CommGrid to
	 * institute a Feedback Loop, or the attacking ship's CommGrid can be attacked
	 * directly to release the jam.
	 * @param ship Ship to jam
	 */
/*	public void jam(Ship ship)
	{
		int d20 = this.currentPlayer.d20();
		int computersBonus = this.currentPlayer.getComputersBonus();
		int commGridBonus = this.getShip().getCommGridBonus();
		int enemyCountermeasuresBonus = ship.getCountermeasuresBonus();
		int enemyJammingBonus = 0; // TODO Implement Jam ReactionIntent
		int totalBonus = computersBonus + commGridBonus;
		int successTarget = 20 + enemyCountermeasuresBonus + enemyJammingBonus;
		if (MessageController.checkSuccess(this.currentPlayer.getName() + " tries to jam " + ship.getName(), d20, successTarget, totalBonus))
		{
			// TODO: Jamming is more complicated than it appears
			// * All enemy Communications are marked as "isJammed"
			// * Generate new AICrewmember to keep this console active running the jam
			// * poll for damage to this console every round and release the jam if so
			// * <R> Feedback Loop can break jam and damage this component
		}
	}
*/
	
	// Constructors
	
	public CommGrid(Ship ship)
	{
		super(ship);
	}

	public CommGrid(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
	}

	// Getters & Setters

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getCommGridBonus();
	}
	
	@Override
	public String getConsoleStatus()
	{
		String status = super.getConsoleStatus();
		return status + " Max Range: " + this.ship.getCommGridRange().getDescription();
	}
}
