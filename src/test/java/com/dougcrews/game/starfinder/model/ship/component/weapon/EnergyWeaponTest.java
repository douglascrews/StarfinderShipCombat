package com.dougcrews.game.starfinder.model.ship.component.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;
import com.dougcrews.game.starfinder.model.ship.component.Armor;
import com.dougcrews.game.starfinder.model.ship.component.PowerCore;
import com.dougcrews.game.starfinder.model.ship.component.ShieldGenerator;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;

class EnergyWeaponTest extends WeaponTest
{
	@Override
	@BeforeEach
	void setUp() throws Exception
	{
		this.ship = new Ship(Ship.Tier.ONE, Ship.Frame.EXPLORER);
		this.ship.addComponent(new PowerCore(this.ship));
		this.weapon = new EnergyWeapon(this.ship, ShipComponent.Size.SMALL);
		this.ship.addComponent(this.weapon);
		this.enemyShip = new Ship(Ship.Tier.ONE, Ship.Frame.EXPLORER);
	}

	@Override
	@AfterEach
	void tearDown() throws Exception
	{
		this.ship = null;
		this.weapon = null;
		this.enemyShip = null;
	}

	@Override
	@Test
	void testGetDamageDiceType()
	{
		assertEquals(6, this.weapon.getDamageDiceType(), "Energy weapons should do ?d6 damage");
	}
	
	@Override
	@Test
	void testGetDamageDiceNumber()
	{
		Ship ship = new Ship(Tier.ONE, Frame.FIGHTER);
		
		Weapon smallWeapon = new EnergyWeapon(ship, ShipComponent.Size.SMALL);
		assertEquals(1, smallWeapon.getDamageDiceNumber(), "Small weapons should do 1d? damage");

		Weapon mediumWeapon = new EnergyWeapon(ship, ShipComponent.Size.MEDIUM);
		assertEquals(2, mediumWeapon.getDamageDiceNumber(), "Medium weapons should do 2d? damage");

		Weapon largeWeapon = new EnergyWeapon(ship, ShipComponent.Size.LARGE);
		assertEquals(3, largeWeapon.getDamageDiceNumber(), "Large weapons should do 3d? damage");

		Weapon capitalWeapon = new EnergyWeapon(ship, ShipComponent.Size.CAPITAL);
		assertEquals(4, capitalWeapon.getDamageDiceNumber(), "Capital weapons should do 4d? damage");
	}
	
	/**
	 * Expect:<ul>
	 * <li> damage reduced by shields
	 * <li> shields reduced by damage
	 * <li> damage reduced by armor
	 * <li> armor unaffected
	 * <li> remaining damage applied to hull
	 * <ul>
	 */
	@Override
	@Test
	void testDoDamage()
	{
		try
		{
			this.enemyShip.addComponent(new PowerCore(this.enemyShip));
			this.enemyShip.addComponent(new ShieldGenerator(this.enemyShip));
			for (int ii = 0; ii < 6; ii++) this.enemyShip.addComponent(new Armor(this.enemyShip));
		} catch (Exception e)
		{
			fail(e.toString());
		}
		
		// Expect damage reduced by (shields + armor), shields reduced, armor unchanged, hull damaged
		this.weapon.doDamage(this.enemyShip, 1);
		assertEquals(4, this.enemyShip.getCurrShields(), "Incorrect shields after damage");
		assertEquals(6, this.enemyShip.getCurrArmor(), "Incorrect armor after damage");
		assertEquals(55, this.enemyShip.getCurrHP(), "Incorrect hull after damage");

		// Expect damage reduced by (shields + armor), shields reduced, armor unchanged, hull damaged
		this.weapon.doDamage(this.enemyShip, 11);
		assertEquals(0, this.enemyShip.getCurrShields(), "Incorrect shields after damage");
		assertEquals(6, this.enemyShip.getCurrArmor(), "Incorrect armor after damage");
		assertEquals(54, this.enemyShip.getCurrHP(), "Incorrect hull after damage");

		// Expect damage reduced by (shields + armor), shields unchanged, armor unchanged, hull damaged
		this.weapon.doDamage(this.enemyShip, 1000);
		assertEquals(0, this.enemyShip.getCurrShields(), "Incorrect shields after damage");
		assertEquals(6, this.enemyShip.getCurrArmor(), "Incorrect armor after damage");
		assertEquals(0, this.enemyShip.getCurrHP(), "Incorrect hull after damage");
	}
}
