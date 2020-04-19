package com.dougcrews.game.starfinder.model.intent;

import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;

public abstract class ReactionIntentComponent extends ReactionIntent
{
	protected final ShipComponent component;
	
	@Override
	public String toString()
	{
		return super.toString() + ":" + this.component.getName();
	}

	// Constructors
	
	public ReactionIntentComponent(ShipComponent component)
	{
		this.component = component;
	}

	// Getters & Setters

	public ShipComponent getComponent()
	{
		return this.component;
	}
}
