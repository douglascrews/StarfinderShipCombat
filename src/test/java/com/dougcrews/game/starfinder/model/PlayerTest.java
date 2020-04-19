package com.dougcrews.game.starfinder.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;
import com.dougcrews.game.starfinder.model.ship.component.PassiveSensors;

class PlayerTest {
	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger(PlayerTest.class.getName());

	@BeforeEach
	void setUp() throws Exception
	{
	}

	@AfterEach
	void tearDown() throws Exception
	{
	}

	@Test
	// TODO: Move to DiceTest
	void testD20() {
		int minRoll = 999;
		int maxRoll = -999;
		for (int ii = 0; ii < 1000; ii++)
		{
			int roll = Dice.d(20);
			//log.debug("(d20) = " + roll);
			if (roll < minRoll) minRoll = roll;
			if (roll > maxRoll) maxRoll = roll;
		}
		assertEquals(1, minRoll, "Incorrect d20 generator lower bound");
		assertEquals(20, maxRoll, "Incorrect d20 generator upper bound");
	}
	
	@Test
	void testDefaultPlayer()
	{
		Ship ship = new Ship(Ship.Tier.ONE, Ship.Frame.EXPLORER);
		Player player = new Player(null, ship);
		assertEquals("Anonymous Player (controlling nothing on Default Ship)",
			player.toString(), "Unexpected player descriptor");
		player.setCurrentConsole(new PassiveSensors(ship));
		assertEquals("Anonymous Player (controlling Passive Sensors on Default Ship)",
			player.toString(), "Unexpected player descriptor");
	}
	
	@Test
	void testSavedDice()
	{
		Player player = new Player("Unit Test Player", new Ship(Tier.ONE, Frame.FIGHTER));
		player.setRollForMe(false);
		player.pushD20(1);
		player.pushD20(20);
		player.pushD20(3);
		player.pushD20(5);
		player.pushD20(7);
		player.pushD20(11);
		player.pushD20(13);
		assertEquals(1, player.d20());
		assertEquals(20, player.d20());
		assertEquals(3, player.d20());
		assertEquals(5, player.d20());
		assertEquals(7, player.d20());
		assertEquals(11, player.d20());
		assertEquals(13, player.d20());
	}
}
