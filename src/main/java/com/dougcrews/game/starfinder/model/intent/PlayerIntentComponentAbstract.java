package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;

public abstract class PlayerIntentComponentAbstract extends PlayerIntent
{
	private static final Logger log = LogManager.getLogger(PlayerIntentComponentAbstract.class.getName());
	
	protected ShipComponent component;
	
	@Override
	protected boolean before()
	{
		if (this.component.isHasBeenUsedThisRound())
		{
			log.error(this.component.getName() + " console has already been used this round");
			return false;
		}
		
		return true;
	}
	
	@Override
	protected void after()
	{
		this.component.setHasBeenUsedThisRound(true);
	}
	
//	@Override
//	public String toString()
//	{
//		return super.toString() + ":" + this.component.getName();
//	}

	// Constructors

	public PlayerIntentComponentAbstract(ActionType actionType, Player player, ShipComponent component)
	{
		super(actionType, player);
		this.component = component;
	}

	// Getters & Setters

	public ShipComponent getComponent()
	{
		return this.component;
	}

	@SuppressWarnings("unused")
	private void setComponent(ShipComponent component)
	{
		this.component = component;
	}
}
