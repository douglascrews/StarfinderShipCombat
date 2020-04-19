package com.dougcrews.game.starfinder.model.ship.component;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.component.PowerCore;

class PowerCoreTest
{
	private PowerCore powerCore;
	
	@BeforeEach
	void setUp() throws Exception
	{
		Ship ship = new Ship(Ship.Tier.ONE, Ship.Frame.EXPLORER);
		this.powerCore = new PowerCore(ship);
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.powerCore = null;
	}

	@Test
	void testGetPowerRequired()
	{
		assertEquals(-10, this.powerCore.getPowerRequired(), "Mismatch in default power required");
		this.powerCore.setCurrHP(3);
		assertEquals(-3, this.powerCore.getPowerRequired(), "Power core should provide its HP worth of power");
		this.powerCore.setEnabled(false);
		assertEquals(0, this.powerCore.getPowerRequired(), "Disabled power core should provide/consume zero power");
	}

}
