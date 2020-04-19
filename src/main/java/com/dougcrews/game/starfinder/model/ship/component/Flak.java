package com.dougcrews.game.starfinder.model.ship.component;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.controller.MessageController;
import com.dougcrews.game.starfinder.model.Dice;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentComponentEnableDisable;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentComponentFlak;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;

public class Flak extends ShipComponent
{
	private static final Logger log = LogManager.getLogger(Flak.class.getName());

	@Override
	public String getGenericName()
	{
		return "Flak Weapon";
	}

	@Override
	public String getDescription()
	{
		return "Flak throwers are useless against other ships, but they are a popular defense against incoming missiles; some GEE-M modules are intelligent enough to fire them automatically as needed.";
	}

	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		Set<PlayerIntent> intents = this.getDefaultIntents(targetShip);
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
			for (Missile missile : this.getShip().getDetectedMissiles())
			{
				intents.add(new PlayerIntentComponentFlak(this.currentPlayer, this, missile));
			}
			
			intents.add(new PlayerIntentComponentEnableDisable(this.currentPlayer, this));
		}
		return intents;
	}

	@Override
	public Set<ReactionIntent> getPossibleReactions()
	{
		return this.getDefaultReactions();
	}

	// Intents
	
	/**
	 * d20+Gunnery+Passive vs. Missile TL
	 * @param missile Missile to be destroyed
	 */
	public void fire(Missile missile)
	{
		Player gunner = (this.getCurrentPlayer() == null ? this.getShip().getAICrewmember() : this.getCurrentPlayer());
		int d20 = Dice.d(20);
		int gunneryBonus = gunner.getGunneryBonus();
		int passiveSensorBonus = this.getShip().getPassiveSensorsBonus();
		int missileTL = missile.getTL();
		boolean success = MessageController.checkSuccess(gunner.getName() + " fires " + this.getName() + " at " + missile.getName(),
			d20, missileTL, gunneryBonus, passiveSensorBonus);
		
		if (success)
		{
			log.info(missile.getName() + " explodes harmlessly!");
			missile.onMissileInactive();
		}
	}
	
	// Constructors
	
	public Flak(Ship ship)
	{
		super(ship);
	}
	
	public Flak(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		super(ship, armored, shielded, hidden, resistant);
	}

	// Getters & Setters

	@Override
	public int getComponentBonus()
	{
		return 0;
	}
}
