package com.dougcrews.game.starfinder.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.component.AIModule;
import com.dougcrews.game.starfinder.model.ship.component.Armor;
import com.dougcrews.game.starfinder.model.ship.component.PowerCore;
import com.dougcrews.game.starfinder.model.ship.component.ShieldGenerator;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;
import com.dougcrews.game.starfinder.model.ship.component.weapon.EnergyWeapon;
import com.dougcrews.game.starfinder.model.ship.component.weapon.KineticWeapon;
import com.dougcrews.game.starfinder.model.ship.component.weapon.MissileLauncher;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

class CombatControllerTest
{

	@BeforeEach
	void setUp() throws Exception
	{
	}

	@AfterEach
	void tearDown() throws Exception
	{
	}

	@Test
	void testSimpleTwoShipCombat()
	{
		Ship enterprise = new Ship(Ship.Tier.TEN, Ship.Frame.EXPLORER); // Kirk cheats
		enterprise.setName("Enterprise");
		Player captainKirk = new Player("Captain Kirk", enterprise, 20, 20, 20, 20, 20, 20); // Yes, he's just that good.
		enterprise.setFaction(Ship.Faction.PLAYER);
		try
		{
			enterprise.addComponent(new PowerCore(enterprise));
			enterprise.addComponent(new PowerCore(enterprise));
			enterprise.addComponent(new PowerCore(enterprise));
			enterprise.addComponent(new EnergyWeapon(enterprise, ShipComponent.Size.SMALL));
			enterprise.addComponent(new KineticWeapon(enterprise, ShipComponent.Size.SMALL));
			enterprise.addComponent(new MissileLauncher(enterprise, ShipComponent.Size.SMALL));
			enterprise.addComponent(new Armor(enterprise));
			enterprise.addComponent(new Armor(enterprise));
			enterprise.addComponent(new ShieldGenerator(enterprise));
			enterprise.addComponent(new ShieldGenerator(enterprise));
			enterprise.addComponent(new AIModule(enterprise));
			enterprise.addComponent(new AIModule(enterprise));
			enterprise.addComponent(new AIModule(enterprise));
			enterprise.addComponent(new AIModule(enterprise));
			enterprise.addComponent(new AIModule(enterprise));
			for (Weapon weapon : enterprise.getWeapons()) weapon.setCurrentPlayer(captainKirk);
		} catch (Exception e)
		{
			fail(e.toString());
		}

		Ship klingons = new Ship(Ship.Tier.ONE, Ship.Frame.EXPLORER);
		klingons.setName("Klingon Battle Cruiser");
		Player captainKlingon = new Player("Commander T'Putz", klingons);
		captainKlingon.setGunneryBonus(1);
		klingons.setPilot(captainKlingon);
		klingons.setFaction(Ship.Faction.NPC);
		try
		{
			klingons.addComponent(new PowerCore(klingons));
			klingons.addComponent(new EnergyWeapon(klingons, ShipComponent.Size.SMALL));
			klingons.addComponent(new KineticWeapon(klingons, ShipComponent.Size.SMALL));
			klingons.addComponent(new MissileLauncher(klingons, ShipComponent.Size.SMALL));
			klingons.addComponent(new Armor(klingons));
			klingons.addComponent(new ShieldGenerator(klingons));
			klingons.addComponent(new ShieldGenerator(klingons));
			for (Weapon weapon : klingons.getWeapons()) weapon.setCurrentPlayer(captainKlingon);
		} catch (Exception e)
		{
			fail(e.toString());
		}

		CombatController cc = new CombatController();
		cc.addShip(enterprise);
		cc.addShip(klingons);
		cc.setMaxRounds(100);
		testCombat(cc);

		assertTrue(cc.getShips().contains(enterprise), "Enterprise should be in combat");
		assertTrue(cc.getShips().contains(klingons), "Klingons should be in combat");
		assertTrue(enterprise.isActive(), "Enterprise doesn't win??? Oh, HELL NO!");
		assertFalse(klingons.isActive(), "Klingons should have been knocked out");
		assertEquals(0, klingons.getCurrHP(), "Klingon hull points should be exactly zero");
	}

//
//	@Test
//	void testTemp()
//	{
//		Player captainKirk = new Player();
//		captainKirk.setName("Captain Kirk");
//		captainKirk.setGunneryBonus(20); // Yes, he's just that good.
//		Ship enterprise = new Ship(1);
//		enterprise.setName("Enterprise");
//		enterprise.setHull(100); // Kirk cheats
//		try
//		{
//			enterprise.addComponent(new PowerCore());
//			enterprise.addComponent(new PowerCore());
//			enterprise.addComponent(new PowerCore());
//			enterprise.addComponent(new EnergyWeapon(ShipComponent.Size.SMALL));
//			enterprise.addComponent(new KineticWeapon(ShipComponent.Size.SMALL));
//			enterprise.addComponent(new Armor());
//			enterprise.addComponent(new Armor());
//			enterprise.addComponent(new ShieldGenerator());
//			enterprise.addComponent(new ShieldGenerator());
//			for (Weapon weapon : enterprise.getWeapons()) weapon.setCurrentPlayer(captainKirk);
//		} catch (Exception e)
//		{
//			fail(e.toString());
//		}
//		
//		for (Weapon weapon : enterprise.getWeapons())
//		{
//			String s = weapon.getSimpleStatus();
//			Log.info(s);
//		}
//	}

	/**
	 * Runs through a complete combat sequence immediately.
	 * @param controller CombatController to execute
	 */
	public void testCombat(CombatController controller)
	{
		controller.startCombat(true);
		while (! controller.isCombatComplete())
		{
			controller.combatRound(true);
		}
		controller.endCombat();
	}
}
