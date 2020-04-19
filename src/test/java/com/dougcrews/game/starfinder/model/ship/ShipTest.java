package com.dougcrews.game.starfinder.model.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.ship.component.DuplicateComponentException;
import com.dougcrews.game.starfinder.model.ship.component.InsufficientPowerException;
import com.dougcrews.game.starfinder.model.ship.component.PassiveSensors;
import com.dougcrews.game.starfinder.model.ship.component.PowerCore;
import com.dougcrews.game.starfinder.model.ship.component.ShipBuildException;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;

class ShipTest
{
	private Ship ship;
	
	@BeforeEach
	void setUp() throws Exception
	{
		this.ship = new Ship(Ship.Tier.ONE, Ship.Frame.EXPLORER);
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.ship = null;
	}

	@Test
	void testGetDefaultName()
	{
		assertTrue(this.ship.getStatus().contains("Default Ship"), "Invalid default ship name");
	}

	@Test
	void testGetName()
	{
		assertTrue(this.ship.getName().equals("Default Ship"), "Invalid default ship name");
	}

	@Test
	void testSetName()
	{
		final String name = "My Awesome Ship";
		this.ship.setName(name);
		assertTrue(this.ship.getName().equals(name), "Invalid custom ship name");
	}

	@Test
	void testGetBuildPoints()
	{
		Ship tier1Ship = new Ship(Ship.Tier.ONE, Ship.Frame.FIGHTER);
		assertEquals(55, tier1Ship.getBuildPoints(), "Mismatch in Build Points");
		Ship tier5Ship = new Ship(Ship.Tier.FIVE, Ship.Frame.FIGHTER);
		assertEquals(135, tier5Ship.getBuildPoints(), "Mismatch in Build Points");
		Ship tier10Ship = new Ship(Ship.Tier.TEN, Ship.Frame.FIGHTER);
		assertEquals(270, tier10Ship.getBuildPoints(), "Mismatch in Build Points");
		Ship tier15Ship = new Ship(Ship.Tier.FIFTEEN, Ship.Frame.FIGHTER);
		assertEquals(500, tier15Ship.getBuildPoints(), "Mismatch in Build Points");
		Ship tier20Ship = new Ship(Ship.Tier.TWENTY, Ship.Frame.FIGHTER);
		assertEquals(1000, tier20Ship.getBuildPoints(), "Mismatch in Build Points");
	}

	@SuppressWarnings("unused")
	@Test
	void testAddComponents()
	{
		// Try adding a component without enough power to service it
		try
		{
			this.ship.addComponent(new PassiveSensors(this.ship));
			fail("Expected InsufficientPowerException thrown");
		}
		catch (InsufficientPowerException ipe)
		{
			// expected, ignore
		}
		catch (DuplicateComponentException dce)
		{
			fail("Unexpected Exception thrown: " + dce);
		}
		catch (ShipBuildException sbe)
		{
			fail("Unexpected Exception thrown: " + sbe);
		}
		
		// Add a power core and then add a component, expect success
		PowerCore powerCore = new PowerCore(this.ship);
		ShipComponent passiveSensors = new PassiveSensors(this.ship);
		try
		{
			this.ship.addComponent(powerCore);
			this.ship.addComponent(passiveSensors);
		}
		catch (Exception e)
		{
			fail(e.toString());
		}
		
		// Try to add the same component twice
		try
		{
			this.ship.addComponent(passiveSensors);
			fail("DuplicateComponentException expected");
		}
		catch (InsufficientPowerException ipe)
		{
			fail("Unexpected Exception thrown: " + ipe);
		}
		catch (DuplicateComponentException dce)
		{
			// expected, ignore
		}
		catch (ShipBuildException sbe)
		{
			fail("Unexpected Exception thrown: " + sbe);
		}

		// Add a second component of the same type
		ShipComponent passiveSensors2 = new PassiveSensors(this.ship);
		try
		{
			this.ship.addComponent(passiveSensors2);
			assertEquals("Passive Sensors 2", passiveSensors2.getName(), "Name mismatch on installed component");
		}
		catch (Exception e)
		{
			fail(e.toString());
		}
		
		// A damaged power core provides less power
		powerCore.setCurrHP(3); // 
		assertEquals(1, this.ship.getNetPowerAvailable(), "Mismatch in power available");
		
		// Shut down power beyond current usage
		assertTrue(passiveSensors.isEnabled(), "Expected passive sensor array 1 to be enabled");
		assertTrue(passiveSensors2.isEnabled(), "Expected passive sensor array 2 to be enabled");
		powerCore.setCurrHP(1);
		// Expect a random passive sensor (those being the only two components installed) to be shut down
		assertTrue((passiveSensors.isLostPower() ^ passiveSensors2.isLostPower()), "Expected exactly one sensor lost power");
		assertTrue((passiveSensors.isActive() ^ passiveSensors2.isActive()), "Expected exactly one active sensor");
	}
	
	@Test
	void testGetStatus()
	{
		Ship ship = new Ship(Ship.Tier.TEN, Ship.Frame.EXPLORER);
		assertEquals("Ship Status:\nDefault Ship (HP:75 Shields:0 Armor:0)\nCaptain: Default Ship GEE-M\nPilot: Default Ship GEE-M\nComponents:\n", ship.getStatus(), "Mismatch in ship status");
		// TODO: add components, disable components, damage components, break components
	}
}
