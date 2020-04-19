package com.dougcrews.game.starfinder.model.ship.component.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.controller.CombatController;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Faction;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;

public abstract class WeaponTest {
	protected Ship ship;
	protected Ship enemyShip;
	protected Weapon weapon;
	protected CombatController controller = new CombatController();

	abstract void testGetDamageDiceType();
	
	abstract void testGetDamageDiceNumber();

	abstract void testDoDamage();

	@BeforeEach
	void setUp() throws Exception
	{
		this.ship = new Ship(Tier.ONE, Frame.FIGHTER);
		this.ship.setFaction(Faction.PLAYER);
		this.enemyShip = new Ship(Tier.ONE, Frame.FIGHTER);
		this.enemyShip.setFaction(Faction.NPC);
		this.controller.addShip(this.ship);
		this.controller.addShip(this.enemyShip);
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.ship = null;
		this.enemyShip = null;
		this.weapon = null;
	}

	@Test
	void testGetShip()
	{
		assertEquals(this.ship, this.weapon.getShip(), "Wrong ship assigned to weapon");
	}
	
	@Test
	void testFire()
	{
		// TODO: fail("Not yet implemented");
	}

	@Test
	void testOnFireSuccessful()
	{
		// TODO: fail("Not yet implemented");
	}
	
	@Test
	void testGetPowerRequired()
	{
		// TODO: fail("Not yet implemented");
	}
	
	@Test
	void testGetDistanceMod()
	{
		// TODO: fail("Not yet implemented");
	}
	
	@Test
	void testCanTarget()
	{
		// TODO: fail("Not yet implemented");
	}
}
