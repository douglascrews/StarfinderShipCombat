package com.dougcrews.game.starfinder.model.intent;

import com.dougcrews.game.starfinder.model.ship.component.StealthField;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;

public class ReactionIntentComponentStealthBlink extends ReactionIntentComponent
{
	private Missile missile;

	@Override
	public void execute()
	{
		this.getStealthComponent().blink(this.player, this.missile);
	}

	@Override
	public String toString()
	{
		return super.toString() + ":" + this.missile.getName();
	}

	// Constructors
	
	public ReactionIntentComponentStealthBlink(StealthField stealthField, Missile missile)
	{
		super(stealthField);
		this.missile = missile;
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
