package com.dougcrews.game.starfinder.model;

import com.dougcrews.game.starfinder.model.ship.Ship;

public interface Target
{
	/**
	 * Target's shields are damaged.
	 * @param damage raw damage to be applied
	 * @param causeDamage is the target system affected by this damage?
	 * @param useDR is raw damage reduced by the target's shields?
	 * @return damage passed on to next system
	 */
	public int hitShields(int damage, boolean causeDamage, boolean useDR, int ignoreDR);

	/**
	 * Target's armor is damaged.
	 * @param damage raw damage to be applied
	 * @param causeDamage are the target's shields affected by this damage?
	 * @param useDR is raw damage reduced by the target's armor?
	 * @return damage passed on to next system
	 */
	public int hitArmor(int damage, boolean causeDamage, boolean useDR, int ignoreDR);

	/**
	 * Target's hull is damaged.
	 * @param damage raw damage to be applied
	 * @param causeDamage is the target's armor affected by this damage?
	 * @param useDR is the raw damage reduced by the target's hull?
	 * @return damage passed on to next system
	 */
	public int hitHull(int damage, boolean causeDamage, boolean useDR, int ignoreDR);

	/**
	 * Target Lock is 10 + Thrusters + Stealth + SizeMod - Armor + Temporary Bonuses
	 * @return current TL for the Target
	 */
	public int getTargetLock();
	public String getName();
	public boolean isActive();
	public Distance getDistanceFrom(Ship ship);
}
