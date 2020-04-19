package com.dougcrews.game.starfinder.model.ship.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;

class ArmorTest
{
	private Armor armor;
	
	@BeforeEach
	void setUp() throws Exception
	{
		this.armor = new Armor(new Ship(Tier.ONE, Frame.FIGHTER));
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.armor = null;
	}

	@Test
	void testGetGenericName()
	{
		assertEquals("Armor", this.armor.getGenericName());
	}

	@Test
	void testGetDescription()
	{
		assertTrue(this.armor.getDescription().contains("Armor"));
	}

	@Test
	void testGetPowerRequired()
	{
		assertEquals(0, this.armor.getPowerRequired());
	}

	@Test
	void testGetDefaultHullPoints()
	{
		assertEquals(1, this.armor.getDefaultHullPoints());
	}

	@Test
	void testHiddenArmor()
	{
		Armor hiddenArmor = new Armor(new Ship(Tier.ONE, Frame.FIGHTER), true);
		assertFalse(hiddenArmor.isArmored(), "Armor cannot have Armor added to it.");
		assertFalse(hiddenArmor.isShielded(), "Armor cannot have Shields added to it.");
		assertTrue(hiddenArmor.isHidden(), "Armor can be Hidden from sensors.");
		assertFalse(hiddenArmor.isResistant(), "Armor cannot be Resistant to countermeasures.");
	}
}
