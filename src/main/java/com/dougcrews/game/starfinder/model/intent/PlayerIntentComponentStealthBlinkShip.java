package com.dougcrews.game.starfinder.model.intent;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.component.StealthField;

public class PlayerIntentComponentStealthBlinkShip extends PlayerIntentComponentAbstract
{
	private Ship ship;

	@Override
	public void execute()
	{
		this.getStealthComponent().blink(this.player, this.ship);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ":" + this.ship.getName();
	}

	// Constructors
	
	public PlayerIntentComponentStealthBlinkShip(Player player, StealthField component, Ship ship)
	{
		super(ActionType.STANDARD, player, component);
		this.ship = ship;
	}
	
	// Getters & Setters
	
	public StealthField getStealthComponent()
	{
		assert this.component instanceof StealthField;
		return (StealthField) this.component;
	}

	@Override
	protected String getName()
	{
		return "Blink";
	}
}
