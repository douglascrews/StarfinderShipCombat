package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Player;

public class PlayerIntentFullRoundAction extends PlayerIntent
{
	private static final Logger log = LogManager.getLogger(PlayerIntentFullRoundAction.class.getName());
	
	/**
	 * Penalty applied to all skills using Multitask (automatically applied to all actions this round)
	 */
	public static final int MULTITASK_PENALTY = -4;

	@Override
	public void execute()
	{
		log.info(this.getPlayer().getName() + " is multitasking.");
		this.getPlayer().setMultitaskPenalty(MULTITASK_PENALTY);
	}

	// Constructors

	public PlayerIntentFullRoundAction(Player player)
	{
		super(ActionType.FULLROUND, player);
	}

	@Override
	protected String getName()
	{
		return "Full-Round Action";
	}
}
