package com.dougcrews.game.starfinder.model.ship.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;

class EngineTest
{
	private Engine engine;
	
	@BeforeEach
	void setUp() throws Exception
	{
		this.engine = new Engine(new Ship(Tier.ONE, Frame.FIGHTER));
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.engine = null;
	}

	@Test
	void testGetGenericName()
	{
		assertEquals("Engine", this.engine.getGenericName());
	}

	@Test
	void testGetDescription()
	{
		assertTrue(this.engine.getDescription().contains("Engine"));
	}
}
