package com.dougcrews.game.starfinder.model.intent;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.Target;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

/**
 * Fire a weapon at a targetShip
 */
public class PlayerIntentWeaponFire extends PlayerIntentWeaponAbstract
{
	protected Target target;
	
	@Override
	public void execute()
	{
		this.weapon.fire(this.getPlayer(), this.target);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ":" + this.target.getName();
	}

	// Constructors
	
	public PlayerIntentWeaponFire(Player player, Weapon weapon, Target target)
	{
		super(ActionType.STANDARD, player, weapon);
		this.target = target;
	}

	// Getters & Setters
	
	@Override
	protected String getName()
	{
		return "Fire";
	}
}
