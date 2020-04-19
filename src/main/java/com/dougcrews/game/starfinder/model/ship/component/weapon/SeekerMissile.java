package com.dougcrews.game.starfinder.model.ship.component.weapon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;

public class SeekerMissile extends Missile
{
	private static final Logger log = LogManager.getLogger(ShipComponent.class.getName());
	
	/**
	 * Seeker missiles which lose Target Lock are unable to reinstate it and are lost and
	 * out of the battle.
	 */
	@Override
	public void onTargetLockLost()
	{
		super.onTargetLockLost();
		log.info(this.getName() + " from " + this.getLauncher().getShip().getName() + " loses Target Lock and flies away harmlessly!");
		this.onMissileInactive();
	}

	// Constructors
	
	public SeekerMissile(MissileLauncher launcher)
	{
		super(launcher);
	}

	// Getters & Setters
	
	@Override
	public String getGenericName()
	{
		return "Seeker Missile";
	}
}
