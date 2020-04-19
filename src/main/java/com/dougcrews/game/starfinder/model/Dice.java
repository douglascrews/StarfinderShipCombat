package com.dougcrews.game.starfinder.model;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dice
{
	private static final Logger log = LogManager.getLogger(Dice.class.getName());

	private static Random diceRoller = new Random();

	public static int d(int dieSize)
	{
		return Dice.diceRoller.nextInt(dieSize) + 1;
	}

	public static boolean flipACoin()
	{
		int coin = diceRoller.nextInt(2);
		log.debug("coin flip = " + (coin == 1 ? "heads" : "tails"));
		return (coin == 1);
	}

	/**
	 * Is the roll a Critical Fail? It is if:<ul>
	 * <li> it is a natural 1, and
	 * <li> rolling a natural 0 would not have hit
	 * </ul>
	 * @param d20
	 * @param successTarget
	 * @param totalBonuses
	 * @return true if the die roll was a Critical Fail; false if not
	 */
	public static boolean isCriticalFail(int d20, int successTarget, int totalBonuses)
	{
		boolean isHit = (0 + totalBonuses) >= successTarget;
		if (isHit) return false; // Player is so badass he can hit on a zero!
		
		boolean isDieRollCritFail = (d20 == 1);
		if (! isDieRollCritFail) return false;
		
		log.info("Critical Fail!");
		return true;
	}
	
	/**
	 * Convenience method to make sure modifiers display correctly.
	 */
	public static String plusMinus(int value)
	{
		return (value < 0 ? "" : "+") + value;
	}
}
