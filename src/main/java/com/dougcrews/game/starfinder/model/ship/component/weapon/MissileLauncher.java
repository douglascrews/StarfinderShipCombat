package com.dougcrews.game.starfinder.model.ship.component.weapon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.Target;
import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;

public class MissileLauncher extends Weapon
{
	private static final Logger log = LogManager.getLogger(MissileLauncher.class.getName());

	private Missile missile;
	private List<Missile> firedMissiles = new ArrayList<Missile>();

	@Override
	public String getGenericName()
	{
		return "Missile Launcher";
	}

	@Override
	public String getDescription()
	{
		return "A Missile Launcher can fire both Seeker and Guided missiles. A missile must be loaded from a Missile Magazine each time the launcher is fired. Seeker missiles are fire-and-forget - they are less susceptible to enemy countermeasures but once they lose Target Lock they fly off harmlessly; Guided missiles retain Target Lock relayed from their parent ship; if they lose Target Lock, a clever gunner may be able to reacquire it.";
	}
	
	/**
	 * Launch a missile at the target we have Target Lock on.
	 * @param target
	 */
	@Override
	public void onFireSuccessful(Target target, boolean isCrit)
	{
		log.trace("onFireSuccessful()");

		Missile missile = this.unloadMissile();
		if (missile != null)
		{
			missile.setCrit(isCrit);
			missile.launch(target);
		}
		else
		{
			log.info(this.getCurrentPlayer().getName() + " achieves Target Lock but there's no missile to fire!");
		}
	}
	
	public void addFiredMissile(Missile missile)
	{
		this.firedMissiles.add(missile);
	}
	
	public void removeFiredMissile(Missile missile)
	{
		this.firedMissiles.remove(missile);
	}
	
	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		Set<PlayerIntent> intents = this.getDefaultIntents(targetShip);
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
			// TODO: IntentLoadMissile
			// TODO: IntentUnloadMissile
		}
		return intents;
	}
	
	// Intents
	
	public void loadMissile(Missile missile) throws MissileAlreadyLoadedException
	{
		if (this.getMissile() != null)
		{
			throw new MissileAlreadyLoadedException(this.getMissile().getName() + " already loaded. You must remove it first.");
		}
		this.missile = missile;
	}

	public Missile unloadMissile()
	{
		Missile returned = this.getMissile();
		this.missile = null;
		return returned;
	}

	/**
	 * Attempt to regain control of a Guided Missile that has lost Target Lock.
	 * Seeker Missiles which have lost TL are not recoverable.
	 * @param gunner Player attempting the action
	 * @param missile Missile to be recontrolled
	 */
	// PlayerIntent
	public void regainTargetLock(Player gunner, GuidedMissile missile)
	{
		// Can only attempt to recontrol your own missiles; the logic to prevent this should be above this method
		assert missile.getLauncher().getShip().equals(this.getShip());

		if (missile.hasTL())
		{
			log.info(gunner.getName() + " attempts to regain control of " + missile.getName() + " but it already had Target Lock!");
			return;
		}

		if (super.fire(gunner, missile.getTarget()))
		{
			log.info(gunner.getName() + " regains control of " + missile.getStatus() + "!");
			missile.setHasTL(true);
		}
		else
		{
			log.info(gunner.getName() + " fails to regain control of " + missile.getName());
		}
	}

	// Constructors
	
	public MissileLauncher(Ship ship, Size size)
	{
		this(ship, size, false, false, false, false);
	}

	public MissileLauncher(Ship ship, Size size, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		this(ship, size, armored, shielded, hidden, resistant, true, true, true, false, true, true);
	}

	/**
	 * Missile Launchers launch a missile on successful hit and do damage if and when the missile strikes.
	 * Missile weapons are not affected by, and do not damage Shields.
	 * Missile weapons are absorbed by Armor, and do full damage to Armor.
	 * Missile weapons are absorbed by, and do full damage to Hulls.
	 * @param size Size of this MissileLauncher. This impacts range penalties, HP of this MissileLauncher, Build Points required, and more
	 * @param affectedByShields is the damage from this Energy Weapon reduced by Shields?
	 * @param affectsShields does this MissileLauncher damage Shields?
	 * @param affectedByArmor is the damage from this MissileLauncher reduced by Armor?
	 * @param affectsArmor does this MissileLauncher damage Armor?
	 * @param affectedByHull is the damage from this MissileLauncher reduced by Hull?
	 * @param affectsHull does this MissileLauncher damage Hull?
	 */
	public MissileLauncher(Ship ship, Size size, boolean armored, boolean shielded, boolean hidden, boolean resistant, boolean affectedByShields, boolean affectsShields, boolean affectedByArmor, boolean affectsArmor, boolean affectedByHull, boolean affectsHull)
	{
		super(ship, size, armored, shielded, hidden, resistant, false, false, true, true, true, true);
		this.size = size;
	}

	// Getters & Setters
	
	/**
	 * Missiles do ?d4 damage.
	 */
	@Override
	public int getDamageDiceType()
	{
		return 4;
	}
	
	/**
	 * @return true if a missile is loaded and ready to fire; false if not
	 */
	public boolean isLoaded()
	{
		return (this.missile != null);
	}
	
	/**
	 * Returns the missile currently loaded and ready to fire.
	 * @return missile loaded, or null if none
	 */
	public Missile getMissile()
	{
		return this.missile;
	}
	
	/**
	 * Returns a list of missiles fired by this launcher.
	 * @return list of missiles fired by this launcher
	 */
	public List<Missile> getFiredMissiles()
	{
		return this.firedMissiles;
	}
}
