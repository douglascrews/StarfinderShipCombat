package com.dougcrews.game.starfinder.model.intent;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerIntentWeaponFireTest extends PlayerIntentWeaponAbstractTest
{
	
	@Override
	@BeforeEach
	void setUp() throws Exception
	{
		super.setUp();
	}

	@Override
	@AfterEach
	void tearDown() throws Exception
	{
		super.tearDown();
	}

	@Test
	void testExecute()
	{
		PlayerIntent intent = new PlayerIntentWeaponFire(this.gunner, this.weapon, this.targetShip);
		int initialHP = this.targetShip.getCurrHP();
		intent.execute();
		assertTrue(this.targetShip.getCurrHP() < initialHP);
	}
}
