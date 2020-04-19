package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Dice;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.component.weapon.GuidedMissile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.MissileAlreadyLoadedException;
import com.dougcrews.game.starfinder.model.ship.component.weapon.MissileLauncher;
import com.dougcrews.game.starfinder.model.ship.component.weapon.SeekerMissile;

// TODO: <S> Reload Missile (unload + load) auto-success
public class PlayerIntentWeaponMissileLoad extends PlayerIntentWeaponAbstract
{
	private static final Logger log = LogManager.getLogger(PlayerIntentWeaponMissileLoad.class.getName());
	
	@Override
	protected void execute()
	{
		MissileLauncher launcher = this.getMissileLauncher();
		if (! launcher.isLoaded())
		{
			try
			{
				// TODO: Implement Missile Magazines
				Missile missile = Dice.flipACoin() ? new GuidedMissile(launcher) : new SeekerMissile(launcher);

				launcher.loadMissile(missile);
			}
			catch (MissileAlreadyLoadedException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			log.info(this.player.getName() + " tries to load a missile into " + this.weapon.getName() + " but finds one already loaded!");
		}
	}

	// Constructors
	
	public PlayerIntentWeaponMissileLoad(Player gunner, MissileLauncher weapon)
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
		return "Load Missile";
	}
}
