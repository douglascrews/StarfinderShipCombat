package com.dougcrews.game.starfinder.model.intent;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.ship.component.Engine;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;

class PlayerIntentCalledShotTest extends PlayerIntentWeaponAbstractTest
{
	protected ShipComponent targetComponent;
	protected PlayerIntentWeaponCalledShot intent;
	
	@Override
	@BeforeEach
	void setUp() throws Exception
	{
		super.setUp();
		this.targetComponent = new Engine(this.targetShip);
		this.intent = new PlayerIntentWeaponCalledShot(this.gunner, this.weapon, this.targetComponent);
	}

	@Override
	@AfterEach
	void tearDown() throws Exception
	{
		super.tearDown();
		this.targetComponent = null;
	}

	@Test
	void testExecute()
	{
		int initialHP = this.targetComponent.getCurrHP();
		this.intent.execute();
		assertTrue(this.targetComponent.getCurrHP() < initialHP);
	}
}
