package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Dice;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentComponentStealthBlinkShip;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntentComponentStealthBlink;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;

public class StealthField extends ShipComponent
{
	private static final Logger log = LogManager.getLogger(StealthField.class.getName());

	@Override
	public String getGenericName()
	{
		return "Stealth Field Generator";
	}
	
	@Override
	public String getDescription()
	{
		return "Stealth Field Generators are able to bend the very laws of physics around themselves, masking a ship or component from detection and targeting. However, this comes with a substantial penalty to Sensor functionality.";
	}
	

	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		Set<PlayerIntent> intents = this.getDefaultIntents(targetShip);
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
			for (Ship ship : this.getShip().getDetectedShips().keySet())
			{
				intents.add(new PlayerIntentComponentStealthBlinkShip(this.currentPlayer, this, ship));
			}
//			for (ShipComponent component : this.getShip().getDetectedComponents().keySet())
//			{
//			}
			// TODO: PlayerIntentComponentStealthBlinkEverything
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
//			{
//			}
			for (Missile missile : this.getShip().getDetectedMissiles())
			{
				intents.add(new ReactionIntentComponentStealthBlink(this, missile));
			}
		}
		return intents;
	}

	// Intents
	
	// PlayerIntentComponentStealthBlinkMissile
	public void blink(Player gunner, Missile missile)
	{
		log.trace("blink()");
		
		assert gunner != null;
		assert missile != null;
		if (missile.hasTL())
		{
			// d20+Computers+StealthField vs. 20+Tier
			int d20 = Dice.d(20);
			int computersBonus = gunner.getComputersBonus();
			int stealthBonus = missile.getLauncher().getShip().getStealthFieldBonus();
			int gunnerTotal = d20 + computersBonus + stealthBonus;
			int successTarget = 20 + missile.getLauncher().getShip().getTier().getTierBonus();
			boolean success = (gunnerTotal >= successTarget);
			log.info(gunner.getName() + " attempts Blink on " + missile.getName() + ": (" + d20 + ") + " + computersBonus +
				" + " + stealthBonus +  " = " + gunnerTotal + " vs. " + successTarget + " " + (success ? "success!" : "fail"));
			if (success)
			{
				missile.onTargetLockLost();
			}
		}
		else
		{
			// Missile already doesn't have TL. Notify gunner.
			log.info(missile.getName() + " did not have Target Lock!");
		}
	}
	
	// PlayerIntentComponentStealthBlinkShip
	public void blink(Player gunner, Ship ship)
	{
		log.trace("blink()");
		
		assert gunner != null;
		assert ship != null;
		// d20+Computers+StealthField vs. 20+Tier
		int d20 = Dice.d(20);
		int computersBonus = gunner.getComputersBonus();
		int stealthBonus = ship.getStealthFieldBonus();
		int gunnerTotal = d20 + computersBonus + stealthBonus;
		int successTarget = 20 + ship.getTier().getTierBonus();
		boolean success = (gunnerTotal >= successTarget);
		log.info(gunner.getName() + " attempts Blink on " + ship.getName() + ": (" + d20 + ") + " + computersBonus +
			" + " + stealthBonus +  " = " + gunnerTotal + " vs. " + successTarget + " " + (success ? "success!" : "fail"));
		if (success)
		{
			ship.getDetectedShips().remove(this.getShip());
		}
	}

	/**
	 * Attempt a Full Round action Blink against all ships and torpedoes in
	 * Stealth Field maximum range.
	 * @param player
	 */
	// PlayerIntentComponentStealthBlinkEverything
	public void blink(Player player)
	{
		for (Missile missile : this.getShip().getController().getActiveMissiles())
		{
			if (this.getShip().canDetect(missile))
			{
				this.blink(player, missile);
			}
		}
	}

	// Constructors
	
	public StealthField(Ship ship)
	{
		super(ship);
	}
	
	public StealthField(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
	}

	// Getters & Setters

	@Override
	public int getComponentBonus()
	{
		return this.getShip().getStealthFieldBonus();
	}
}
