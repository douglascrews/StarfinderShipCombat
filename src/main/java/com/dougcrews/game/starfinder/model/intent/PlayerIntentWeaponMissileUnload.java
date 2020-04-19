package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.MissileLauncher;

public class PlayerIntentWeaponMissileUnload extends PlayerIntentWeaponAbstract
{
	private static final Logger log = LogManager.getLogger(PlayerIntentWeaponMissileUnload.class.getName());
	
	@Override
	protected void execute()
	{
		MissileLauncher launcher = this.getMissileLauncher();
		if (launcher.isLoaded())
		{
			// TODO: Implement Missile Magazines
			@SuppressWarnings("unused")
			Missile missile = launcher.unloadMissile();
		}
		else
		{
			log.info(this.player.getName() + " tries to unload a missile from " + this.weapon.getName() + " but finds none loaded!");
		}
	}

	// Constructors
	
	public PlayerIntentWeaponMissileUnload(Player gunner, MissileLauncher weapon)
	{
		super(ActionType.MOVE, gunner, weapon);
	}
	
	// Getters & Setters
	
	public MissileLauncher getMissileLauncher()
	{
		assert this.weapon instanceof MissileLauncher;
		return (MissileLauncher) this.weapon;
	}

	@Override
	protected String getName()
	{
		return "Unload Missile";
	}
}
