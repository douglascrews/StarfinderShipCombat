package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Dice;
import com.dougcrews.game.starfinder.model.ship.component.SensorArray;
import com.dougcrews.game.starfinder.model.ship.component.weapon.SeekerMissile;

public class ReactionIntentSensorArrayDetonate extends ReactionIntentComponent
{
	private static final Logger log = LogManager.getLogger(ReactionIntentSensorArrayDetonate.class.getName());
	
	private SeekerMissile missile;

	@Override
	protected void execute()
	{
		if (this.missile.hasTL())
		{
			// d20+Computers+Sensors vs. 20+Tier
			int d20 = Dice.d(20);
			int computersBonus = this.player.getComputersBonus();
			int sensorsBonus = this.player.getShip().getSensorArrayBonus();
			int totalRoll = d20 + computersBonus + sensorsBonus;
			int tierBonus = this.missile.getLauncher().getShip().getTier().getTierBonus();
			int totalTL = 20 + tierBonus;
			boolean success = (totalRoll >= totalTL);
			log.info(this.getPlayer().getName() + " attempts to detonate " + this.missile.getName() + ": (" + d20 + ") + " + computersBonus + " + " + sensorsBonus + " vs. 20 + " + tierBonus + " -> " + (success ? "Success!" : "Fail"));
			if (success)
			{
				log.info(this.missile.getName() + " detonates harmlessly!");
				this.missile.onMissileInactive();
			}
		}
		else
		{
			log.info(this.missile.getName() + " does not have Target Lock. Cannot detonate.");
		}
		
	}

	@Override
	public String toString()
	{
		return super.toString() + ":" + this.missile.getName();
	}

	// Constructors
	
	public ReactionIntentSensorArrayDetonate(SensorArray sensorArray, SeekerMissile missile)
	{
		super(sensorArray);
		this.missile = missile;
	}
	
	// Getters & Setters
	
	public SensorArray getSensorArray()
	{
		assert this.component instanceof SensorArray;
		return (SensorArray) this.component;
	}

	@Override
	protected String getName()
	{
		return "Detonate";
	}
}
