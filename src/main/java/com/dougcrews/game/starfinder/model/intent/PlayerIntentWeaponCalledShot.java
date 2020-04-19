package com.dougcrews.game.starfinder.model.intent;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

public class PlayerIntentWeaponCalledShot extends PlayerIntentWeaponAbstract
{
	protected ShipComponent targetedComponent;
	
	/**
	 * Penalty applied to weapons fire using Called Shot (automatically applied to ShipComponents)
	 */
	public static final int CALLED_SHOT_PENALTY = -2;
	
	@Override
	public void execute()
	{
		// Called Shot penalty is baked in to ShipComponent.getTargetLock()
		this.weapon.fire(getPlayer(), this.targetedComponent);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ":" + this.targetedComponent.getName();
	}
	
	// Constructors
	
	public PlayerIntentWeaponCalledShot(Player player, Weapon weapon, ShipComponent target)
	{
		super(ActionType.MOVE, player, weapon);
		this.targetedComponent = target;
	}

	// Getters & Setters
	
	@Override
	protected String getName()
	{
		return "Called Shot";
	}
}
