package com.dougcrews.game.starfinder.model.intent;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

public abstract class PlayerIntentWeaponAbstract extends PlayerIntentComponentAbstract
{
	protected Weapon weapon;
	
//	@Override
//	public String toString()
//	{
//		return super.toString() + ":" + this.weapon.getName();
//	}
	
	// Constructors
	
	public PlayerIntentWeaponAbstract(ActionType actionType, Player player, Weapon weapon)
	{
		super(actionType, player, weapon);
		this.weapon = weapon;
	}
}
