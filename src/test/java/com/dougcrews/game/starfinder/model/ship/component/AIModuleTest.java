package com.dougcrews.game.starfinder.model.ship.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;

class AIModuleTest
{
	private AIModule aiModule;
	
	@BeforeEach
	void setUp() throws Exception
	{
		this.aiModule = new AIModule(new Ship(Tier.ONE, Frame.FIGHTER));
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.aiModule = null;
	}

	@Test
	void testGetGenericName()
	{
		assertEquals("A.I. Module", this.aiModule.getGenericName());
	}

	@Test
	void testGetDescription()
	{
		assertTrue(this.aiModule.getDescription().contains("Governing Expert Entity Module"));
	}

	@Test
	void testGetName()
	{
		assertEquals("A.I. Module", this.aiModule.getName());
	}

}
