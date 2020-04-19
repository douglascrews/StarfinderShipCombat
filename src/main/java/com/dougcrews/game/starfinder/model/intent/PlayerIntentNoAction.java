package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Player;

public class PlayerIntentNoAction extends PlayerIntent
{
	private static final Logger log = LogManager.getLogger(PlayerIntentNoAction.class.getName());

	@Override
	public void execute()
	{
		log.info(this.getPlayer().getName() + " does not use their " + this.getActionType().getName() + " action.");
	}

	// Constructors

	public PlayerIntentNoAction(Player player, ActionType actionType)
	{
		super(actionType, player);
	}

	@Override
	protected String getName()
	{
		return "No Action";
	}
}
