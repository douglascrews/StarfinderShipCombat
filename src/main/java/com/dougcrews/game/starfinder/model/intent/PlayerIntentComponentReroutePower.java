package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;

/**
 * Enable/Disable this component
 */
public class PlayerIntentComponentReroutePower extends PlayerIntent
{
	private static final Logger log = LogManager.getLogger(PlayerIntentComponentReroutePower.class.getName());

	private ShipComponent componentToDisable;
	private ShipComponent componentToEnable;
	
	@Override
	public void execute()
	{
		log.info(this.getPlayer().getName() + " attempts to reroute power from " + this.getComponentToDisable().getName() + " to " + this.getComponentToEnable().getName());
	
		if (! this.componentToDisable.isActive())
		{
			log.warn(this.componentToDisable.getName() + " is already inactive.");
		}
		this.componentToDisable.disable();
		
		if (! this.componentToEnable.isActive())
		{
			log.warn(this.componentToDisable.getName() + " is inactive.");
		}
		this.componentToEnable.enable();;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ":" + this.componentToDisable.getName() + ":" + this.componentToEnable.getName();
	}
	
	// Constructors
	
	public PlayerIntentComponentReroutePower(Player player, ShipComponent componentToDisable, ShipComponent componentToEnable)
	{
		super(ActionType.STANDARD, player);
		this.componentToDisable = componentToDisable;
		this.componentToEnable = componentToEnable;
	}

	// Getters & Setters
	
	public ShipComponent getComponentToDisable()
	{
		return this.componentToDisable;
	}

	public ShipComponent getComponentToEnable()
	{
		return this.componentToEnable;
	}

	@Override
	protected String getName()
	{
		return "Reroute Power";
	}
}
