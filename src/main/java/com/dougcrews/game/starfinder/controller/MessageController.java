package com.dougcrews.game.starfinder.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageController
{
	private static final Logger log = LogManager.getLogger(MessageController.class.getName());

	public static boolean checkSuccess(String description, int d20, int successTarget, int ...bonuses)
	{
		int totalRoll = d20;
		StringBuilder sb = new StringBuilder(description).append(": (" + d20 + ") ");
		for (int bonus : bonuses)
		{
			totalRoll += bonus;
			sb.append("+ " + bonus + " ");
		}
		boolean success = (totalRoll >= successTarget);
		sb.append("vs. " + successTarget + " => ").append(success ? "Success!" : "Fail");
		log.info(sb.toString());
		return success;
	}
}
