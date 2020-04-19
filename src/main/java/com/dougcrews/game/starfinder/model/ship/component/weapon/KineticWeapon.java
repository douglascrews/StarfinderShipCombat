package com.dougcrews.game.starfinder.model.ship.component.weapon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.ship.Ship;

public class KineticWeapon extends Weapon
{
	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger(KineticWeapon.class.getName());

	// Constructors
	
	public KineticWeapon(Ship ship, Size size)
	{
		this(ship, size, false, false, false, false);
	}

	public KineticWeapon(Ship ship, Size size, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		this(ship, size, armored, shielded, hidden, resistant, true, true, true, false, true, true);
	}

	/**
	 * Kinetic weapons hit and do damage immediately.
	 * Kinetic weapons are absorbed by, but do not damage Shields.
	 * Kinetic weapons are absorbed by, but do not damage Armor.
	 * Kinetic weapons are absorbed by, and do full damage to Hulls.
	 * @param size Size of this KineticWeapon. This impacts range penalties, HP of this KineticWeapon, Build Points required, and more
	 * @param affectedByShields is the damage from this Energy Weapon reduced by Shields?
	 * @param affectsShields does this KineticWeapon damage Shields?
	 * @param affectedByArmor is the damage from this KineticWeapon reduced by Armor?
	 * @param affectsArmor does this KineticWeapon damage Armor?
	 * @param affectedByHull is the damage from this KineticWeapon reduced by Hull?
	 * @param affectsHull does this KineticWeapon damage Hull?
	 */
	public KineticWeapon(Ship ship, Size size, boolean armored, boolean shielded, boolean hidden, boolean resistant, boolean affectedByShields, boolean affectsShields, boolean affectedByArmor, boolean affectsArmor, boolean affectedByHull, boolean affectsHull)
	{
		super(ship, size, armored, shielded, hidden, resistant, true, false, true, false, true, true);
		this.size = size;
	}

	// Getters & Setters
	
	@Override
	public String getGenericName()
	{
		return "Kinetic Weapon";
	}

	@Override
	public String getDescription()
	{
		return "Kinetic weapons like mass drivers are slowed by Shields and Armor, but are excellent at damaging Hulls and components once they pass through.";
	}
	
	@Override
	public int getDamageDiceType()
	{
		return 8;
	}

//	@Override
//	public Set<PlayerIntent> getPossibleIntents()
//	{
//		Set<PlayerIntent> intents = super.getDefaultIntents();
//		return intents;
//	}
//
//	@Override
//	public Set<ReactionIntent> getPossibleReactions()
//	{
//		Set<ReactionIntent> reactions = super.getDefaultReactions();
//		return reactions;
//	}
}
