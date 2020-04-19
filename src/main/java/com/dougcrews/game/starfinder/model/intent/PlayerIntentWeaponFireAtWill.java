package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Dice;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.Target;
import com.dougcrews.game.starfinder.model.ship.component.weapon.GuidedMissile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.MissileAlreadyLoadedException;
import com.dougcrews.game.starfinder.model.ship.component.weapon.MissileLauncher;
import com.dougcrews.game.starfinder.model.ship.component.weapon.SeekerMissile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

public class PlayerIntentWeaponFireAtWill extends PlayerIntentWeaponAbstract
{
	private static final Logger log = LogManager.getLogger(PlayerIntentWeaponFireAtWill.class.getName());
	
	private Target target;
	
	@Override
	public void execute()
	{
		log.info(this.getPlayer().getName() + " fires at will at " + this.target.getName() + " with " + this.weapon.getName());
		this.weapon.setFireAtWillPenalty(-4);
		
		this.weapon.fire(this.player, this.target);

		if (this.weapon instanceof MissileLauncher)
		{
			MissileLauncher launcher = (MissileLauncher) this.weapon;
			if (! launcher.isLoaded()) // fire() was successful and the launcher needs to be reloaded, or it never was loaded
			{
				// Hack: Give the gunner a free reload if needed, but you get a random missile from your magazines
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

			if (launcher.isLoaded()) // if the first fire() failed to achieve Target Lock, the missile is still waiting to launch
			{
				this.weapon.fire(this.player, this.target);
			}
			else
			{
				log.info(this.player.getName() + " failed to load a missile in the " + this.weapon.getName() + "!");
			}
		}
		else // if (this.weapon instanceof MissileLauncher)
		{
			this.weapon.fire(this.player, this.target);
		}
	}

	@Override
	public String toString()
	{
		return super.toString() + ":" + this.target.getName();
	}

	// Constructors
	
	public PlayerIntentWeaponFireAtWill(Player player, Weapon weapon, Target target)
	{
		super(ActionType.FULLROUND, player, weapon);
		this.target = target;
	}
	
	// Getters & Setters
	
	public Target getTarget()
	{
		return this.target;
	}

	@SuppressWarnings("unused")
	private void setTarget(Target target)
	{
		this.target = target;
	}

	@Override
	protected String getName()
	{
		return "Fire At Will";
	}
}
