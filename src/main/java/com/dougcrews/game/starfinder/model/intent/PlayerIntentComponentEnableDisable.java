package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;

/**
 * Enable/Disable this component
 */
public class PlayerIntentComponentEnableDisable extends PlayerIntentComponentAbstract
{
	private static final Logger log = LogManager.getLogger(PlayerIntentComponentEnableDisable.class.getName());
	
	@Override
	public void execute()
	{
		if (this.component.isLostPower())
		{
			log.info(this.getPlayer().getName() + " reengages power to " + this.getComponent().getName());
			this.component.setEnabled(true);
		}
		else
		{
			log.info(this.getPlayer().getName() + " disengages power to " + this.getComponent().getName());
			this.component.setEnabled(false);
		}
	}

	@Override
	protected String getName()
	{
		return "Enable/Disable";
	}

	// Constructors
	
	public PlayerIntentComponentEnableDisable(Player player, ShipComponent component)
	{
		super(ActionType.MOVE, player, component);
	}
	
	// Getters & Setters
}
