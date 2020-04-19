package com.dougcrews.game.starfinder.model.ship.component.weapon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.ship.Ship;

public class EnergyWeapon extends Weapon
{
	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger(EnergyWeapon.class.getName());
	
	@Override
	public String getGenericName()
	{
		return "Energy Weapon";
	}

	@Override
	public String getDescription()
	{
		return "Energy weapons like lasers do full damage to Shields, are absorbed by Armor, but are excellent at damaging Hulls and components once they pass through.";
	}

	// Intents
	
	// Constructors
	
	public EnergyWeapon(Ship ship, Size size)
	{
		this(ship, size, false, false, false, false);
	}

	public EnergyWeapon(Ship ship, Size size, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		this(ship, size, armored, shielded, hidden, resistant, true, true, true, false, true, true);
	}

	/**
	 * Energy weapons hit and do damage immediately.
	 * Energy weapons are absorbed by, and do full damage to Shields.
	 * Energy weapons are absorbed by Armor, but do not damage Armor.
	 * Energy weapons are absorbed by, and do full damage to Hulls.
	 * @param size Size of this EnergyWeapon. This impacts range penalties, HP of this EnergyWeapon, Build Points required, and more
	 * @param affectedByShields is the damage from this Energy Weapon reduced by Shields?
	 * @param affectsShields does this EnergyWeapon damage Shields?
	 * @param affectedByArmor is the damage from this EnergyWeapon reduced by Armor?
	 * @param affectsArmor does this EnergyWeapon damage Armor?
	 * @param affectedByHull is the damage from this EnergyWeapon reduced by Hull?
	 * @param affectsHull does this EnergyWeapon damage Hull?
	 */
	public EnergyWeapon(Ship ship, Size size, boolean armored, boolean shielded, boolean hidden, boolean resistant, boolean affectedByShields, boolean affectsShields, boolean affectedByArmor, boolean affectsArmor, boolean affectedByHull, boolean affectsHull)
	{
		super(ship, size, armored, shielded, hidden, resistant, true, true, true, false, true, true);
		this.size = size;
	}

	// Getters & Setters
	
	@Override
	public int getDamageDiceType()
	{
		return 6;
	}
}
