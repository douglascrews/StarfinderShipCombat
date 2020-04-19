package com.dougcrews.game.starfinder.model.ship.component.weapon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.controller.CombatController;
import com.dougcrews.game.starfinder.model.Distance;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ShipToShipRelation;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Faction;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent.Size;

class MissileTest
{
	private CombatController controller;
	private Ship ship;
	private MissileLauncher launcher;
	private GuidedMissile guidedMissile;
	private SeekerMissile seekerMissile;
	private Ship target;
	
	@BeforeEach
	void setUp() throws Exception
	{
		this.controller = new CombatController();
		this.ship = new Ship(Tier.ONE, Frame.FIGHTER);
		this.ship.setFaction(Faction.PLAYER);
		this.controller.addShip(this.ship);
		this.launcher = new MissileLauncher(this.ship, Size.SMALL);
		this.guidedMissile = new GuidedMissile(this.launcher);
		this.seekerMissile = new SeekerMissile(this.launcher);
		this.target = new Ship(Tier.ONE, Frame.FIGHTER);
		this.target.setFaction(Faction.NPC);
		this.controller.addShip(this.target);
		for (ShipToShipRelation s2sr : this.controller.getShipRelations())
		{
			s2sr.setDistance(Distance.EXTREME);
		}
		Player aceGunner = new Player("Unit Test Ace Gunner", this.ship, 0, 20, 0, 0, 0, 0);
		this.launcher.setCurrentPlayer(aceGunner);
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.controller = null;
		this.ship = null;
		this.launcher = null;
		this.guidedMissile = null;
		this.seekerMissile = null;
		this.target = null;
	}

	@Test
	void testMove()
	{
		try
		{
			this.launcher.loadMissile(this.seekerMissile);
		}
		catch (MissileAlreadyLoadedException e)
		{
			fail(e.toString());
		}
		this.launcher.onFireSuccessful(this.target, false);
		assertEquals(Distance.EXTREME, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.LONG, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.MEDIUM, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.SHORT, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.setHasTL(false);
		this.seekerMissile.move();
		assertEquals(Distance.POINT_BLANK, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.setHasTL(false);
		this.seekerMissile.move();
		assertEquals(Distance.SHORT, this.seekerMissile.getDistanceFromTarget());
		assertFalse(this.seekerMissile.isApproachingTarget());
		this.seekerMissile.move();
		assertEquals(Distance.MEDIUM, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.LONG, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.EXTREME, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.BEYOND_EXTREME, this.seekerMissile.getDistanceFromTarget());
		assertTrue(this.launcher.getFiredMissiles().contains(this.seekerMissile));
		this.seekerMissile.move();
		assertFalse(this.launcher.getFiredMissiles().contains(this.seekerMissile));

		try
		{
			this.launcher.loadMissile(this.guidedMissile);
		}
		catch (MissileAlreadyLoadedException e)
		{
			fail(e.toString());
		}
		this.launcher.onFireSuccessful(this.target, false);
		assertEquals(Distance.EXTREME, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.LONG, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.MEDIUM, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.SHORT, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.POINT_BLANK, this.guidedMissile.getDistanceFromTarget());
		assertTrue(this.guidedMissile.isApproachingTarget());
		this.guidedMissile.move();
		assertEquals(Distance.SHORT, this.guidedMissile.getDistanceFromTarget());
		assertFalse(this.guidedMissile.isApproachingTarget());
		this.guidedMissile.move();
		assertEquals(Distance.MEDIUM, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.LONG, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.EXTREME, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.BEYOND_EXTREME, this.guidedMissile.getDistanceFromTarget());
	}

	@Test
	void testOnHitTarget()
	{
		try
		{
			this.launcher.loadMissile(this.seekerMissile);
		}
		catch (MissileAlreadyLoadedException e)
		{
			fail(e.toString());
		}
		this.launcher.onFireSuccessful(this.target, false);
		assertEquals(Distance.EXTREME, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.LONG, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.MEDIUM, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.SHORT, this.seekerMissile.getDistanceFromTarget());
		int targetMaxHP = this.target.getCurrHP();
		this.seekerMissile.move();
		assertTrue(this.target.getCurrHP() < targetMaxHP);

		try
		{
			this.launcher.loadMissile(this.guidedMissile);
		}
		catch (MissileAlreadyLoadedException e)
		{
			fail(e.toString());
		}
		this.launcher.onFireSuccessful(this.target, false);
		assertEquals(Distance.EXTREME, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.LONG, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.MEDIUM, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.SHORT, this.guidedMissile.getDistanceFromTarget());
		targetMaxHP = this.target.getCurrHP();
		this.guidedMissile.move();
		assertTrue(this.target.getCurrHP() < targetMaxHP);
	}

	@Test
	void testLaunch()
	{
		try
		{
			this.launcher.loadMissile(this.seekerMissile);
			this.launcher.onFireSuccessful(this.target, false);
			assertEquals(Distance.EXTREME, this.seekerMissile.getDistanceFromTarget());
			assertTrue(this.seekerMissile.hasTL());
			assertTrue(this.launcher.getFiredMissiles().contains(this.seekerMissile));
			assertNull(this.launcher.getMissile());
		}
		catch (MissileAlreadyLoadedException e)
		{
			fail(e.toString());
		}
	}

	@Test
	void testOnMissileInactive()
	{
		try
		{
			this.launcher.loadMissile(this.seekerMissile);
		}
		catch (MissileAlreadyLoadedException e)
		{
			fail(e.toString());
		}
		this.launcher.onFireSuccessful(this.target, false);
		assertEquals(Distance.EXTREME, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.LONG, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.MEDIUM, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.SHORT, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.onTargetLockLost();
		assertFalse(this.launcher.getFiredMissiles().contains(this.seekerMissile));
		this.seekerMissile.move();
		assertEquals(Distance.POINT_BLANK, this.seekerMissile.getDistanceFromTarget());

		try
		{
			this.launcher.loadMissile(this.guidedMissile);
		}
		catch (MissileAlreadyLoadedException e)
		{
			fail(e.toString());
		}
		this.launcher.onFireSuccessful(this.target, false);
		assertEquals(Distance.EXTREME, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.onTargetLockLost();
		this.guidedMissile.move();
		assertEquals(Distance.LONG, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.MEDIUM, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.SHORT, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.POINT_BLANK, this.guidedMissile.getDistanceFromTarget());
		assertTrue(this.guidedMissile.isApproachingTarget());
		this.guidedMissile.move();
		assertEquals(Distance.SHORT, this.guidedMissile.getDistanceFromTarget());
		assertFalse(this.guidedMissile.isApproachingTarget());
		this.guidedMissile.move();
		assertEquals(Distance.MEDIUM, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.LONG, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.EXTREME, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.BEYOND_EXTREME, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.onTargetLockRegained();
		this.guidedMissile.move();
		assertTrue(this.guidedMissile.isApproachingTarget());
		assertEquals(Distance.EXTREME, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.LONG, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.MEDIUM, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.SHORT, this.guidedMissile.getDistanceFromTarget());
	}

	@Test
	void testOnTargetLockLost()
	{
		try
		{
			this.launcher.loadMissile(this.seekerMissile);
		}
		catch (MissileAlreadyLoadedException e)
		{
			fail(e.toString());
		}
		this.launcher.onFireSuccessful(this.target, false);
		assertTrue(this.launcher.getFiredMissiles().contains(this.seekerMissile));
		this.seekerMissile.onTargetLockLost();
		assertFalse(this.launcher.getFiredMissiles().contains(this.seekerMissile));

		try
		{
			this.launcher.loadMissile(this.guidedMissile);
		}
		catch (MissileAlreadyLoadedException e)
		{
			fail(e.toString());
		}
		this.launcher.onFireSuccessful(this.target, false);
		assertTrue(this.launcher.getFiredMissiles().contains(this.guidedMissile));
		this.guidedMissile.onTargetLockLost();
		assertTrue(this.launcher.getFiredMissiles().contains(this.guidedMissile));
	}

	@Test
	void testOnTargetLockRegained()
	{
		try
		{
			this.launcher.loadMissile(this.seekerMissile);
		}
		catch (MissileAlreadyLoadedException e)
		{
			fail(e.toString());
		}
		this.launcher.onFireSuccessful(this.target, false);
		assertEquals(Distance.EXTREME, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.LONG, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.MEDIUM, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.move();
		assertEquals(Distance.SHORT, this.seekerMissile.getDistanceFromTarget());
		this.seekerMissile.onTargetLockLost();
		assertFalse(this.launcher.getFiredMissiles().contains(this.seekerMissile));
		this.seekerMissile.move();
		assertEquals(Distance.POINT_BLANK, this.seekerMissile.getDistanceFromTarget());

		try
		{
			this.launcher.loadMissile(this.guidedMissile);
		}
		catch (MissileAlreadyLoadedException e)
		{
			fail(e.toString());
		}
		this.launcher.onFireSuccessful(this.target, false);
		assertEquals(Distance.EXTREME, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.onTargetLockLost();
		this.guidedMissile.move();
		assertEquals(Distance.LONG, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.MEDIUM, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.SHORT, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.POINT_BLANK, this.guidedMissile.getDistanceFromTarget());
		assertTrue(this.guidedMissile.isApproachingTarget());
		this.guidedMissile.move();
		assertEquals(Distance.SHORT, this.guidedMissile.getDistanceFromTarget());
		assertFalse(this.guidedMissile.isApproachingTarget());
		this.guidedMissile.move();
		assertEquals(Distance.MEDIUM, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.LONG, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.EXTREME, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.BEYOND_EXTREME, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.onTargetLockRegained();
		assertTrue(this.guidedMissile.isApproachingTarget());
		this.guidedMissile.move();
		assertEquals(Distance.EXTREME, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.LONG, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.MEDIUM, this.guidedMissile.getDistanceFromTarget());
		this.guidedMissile.move();
		assertEquals(Distance.SHORT, this.guidedMissile.getDistanceFromTarget());
	}
}
