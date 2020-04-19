package com.dougcrews.game.starfinder.model.ship.component;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.Distance;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent.Size;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.MissileLauncher;
import com.dougcrews.game.starfinder.model.ship.component.weapon.SeekerMissile;

class FlakTest
{
	private Flak flak;
	
	@BeforeEach
	void setUp() throws Exception
	{
		this.flak = new Flak(new Ship(Tier.ONE, Frame.FIGHTER));
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.flak = null;
	}

	@Test
	void testGetGenericName()
	{
		assertEquals("Flak Weapon", this.flak.getGenericName());
	}

	@Test
	void testGetDescription()
	{
		assertTrue(this.flak.getDescription().contains("Flak"));
	}

	@Test
	void testFire()
	{
		MissileLauncher launcher = new MissileLauncher(new Ship(Tier.ONE, Frame.FIGHTER), Size.SMALL);
		Missile missile = new SeekerMissile(launcher);
		missile.setDistanceFromTarget(Distance.SHORT);
		missile.setHasTL(true);
		launcher.addFiredMissile(missile);
		Player sharpshooter = new Player("Unit Test Gunner", this.flak.getShip(), 0, +20, 0, 0, 0, 0);
		this.flak.setCurrentPlayer(sharpshooter);
		this.flak.fire(missile);
		assertNull(missile.getTarget());
		assertNull(missile.getLauncher());
		assertFalse(missile.hasTL());
		assertFalse(launcher.getFiredMissiles().contains(missile));
	}
}
