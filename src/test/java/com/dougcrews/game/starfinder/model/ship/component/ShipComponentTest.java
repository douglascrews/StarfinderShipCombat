package com.dougcrews.game.starfinder.model.ship.component;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.Target;
import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent.Size;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

class ShipComponentTest
{
	private ShipComponent component;
	private Ship ship = new Ship(Ship.Tier.ONE, Ship.Frame.EXPLORER);
	private PowerCore powerCore = new PowerCore(this.ship);

	private Weapon smallWeapon;
	private Weapon mediumWeapon;
	private Weapon largeWeapon;
	private Weapon capitalWeapon;
	
	@BeforeEach
	void setUp() throws Exception
	{
		this.component = new ShipComponent(this.ship)
		{
			@Override
			public String getDescription()
			{
				return "Unit Test Object";
			}

			@Override
			public String getGenericName()
			{
				return "Unit Test Object";
			}

			@Override
			public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
			{
				Set<PlayerIntent> intents = super.getDefaultIntents(targetShip);
				return intents;
			}

			@Override
			public Set<ReactionIntent> getPossibleReactions()
			{
				Set<ReactionIntent> reactions = super.getDefaultReactions();
				return reactions;
			}

			@Override
			public int getComponentBonus()
			{
				// TODO Auto-generated method stub
				return 0;
			}
		};
		
		this.smallWeapon = new Weapon(this.ship, Size.SMALL)
		{
			@Override public int getDamageDiceType() { return 1; }
			@Override public void onFireSuccessful(Target target, boolean isCrit) {}
			@Override public void doDamage(Target target, int rawDamage) {}
			@Override public String getGenericName() { return "Generic Weapon"; }
			@Override public String getDescription() { return "Unit Test Object"; }
			@Override
			public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
			{
				Set<PlayerIntent> intents = super.getDefaultIntents(targetShip);
				return intents;
			}

			@Override
			public Set<ReactionIntent> getPossibleReactions()
			{
				Set<ReactionIntent> reactions = super.getDefaultReactions();
				return reactions;
			}
		};
		
		this.mediumWeapon = new Weapon(this.ship, Size.MEDIUM)
		{
			@Override public int getDamageDiceType() { return 1; }
			@Override public void onFireSuccessful(Target target, boolean isCrit) {}
			@Override public void doDamage(Target target, int rawDamage) {}
			@Override public String getGenericName() { return "Generic Weapon"; }
			@Override public String getDescription() { return "Unit Test Object"; }
			@Override
			public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
			{
				Set<PlayerIntent> intents = super.getDefaultIntents(targetShip);
				return intents;
			}

			@Override
			public Set<ReactionIntent> getPossibleReactions()
			{
				Set<ReactionIntent> reactions = super.getDefaultReactions();
				return reactions;
			}
		};
		
		this.largeWeapon = new Weapon(this.ship, Size.LARGE)
		{
			@Override public int getDamageDiceType() { return 1; }
			@Override public void onFireSuccessful(Target target, boolean isCrit) {}
			@Override public void doDamage(Target target, int rawDamage) {}
			@Override public String getGenericName() { return "Generic Weapon"; }
			@Override public String getDescription() { return "Unit Test Object"; }
			@Override
			public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
			{
				Set<PlayerIntent> intents = super.getDefaultIntents(targetShip);
				return intents;
			}

			@Override
			public Set<ReactionIntent> getPossibleReactions()
			{
				Set<ReactionIntent> reactions = super.getDefaultReactions();
				return reactions;
			}
		};
		
		this.capitalWeapon = new Weapon(this.ship, Size.CAPITAL)
		{
			@Override public int getDamageDiceType() { return 1; }
			@Override public void onFireSuccessful(Target target, boolean isCrit) {}
			@Override public void doDamage(Target target, int rawDamage) {}
			@Override public String getGenericName() { return "Generic Weapon"; }
			@Override public String getDescription() { return "Unit Test Object"; }
			@Override
			public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
			{
				Set<PlayerIntent> intents = super.getDefaultIntents(targetShip);
				return intents;
			}

			@Override
			public Set<ReactionIntent> getPossibleReactions()
			{
				Set<ReactionIntent> reactions = super.getDefaultReactions();
				return reactions;
			}
		};
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.component = null;
		this.smallWeapon = null;
		this.mediumWeapon = null;
		this.largeWeapon = null;
		this.capitalWeapon = null;
	}

	@Test
	void testIsActive()
	{
		try
		{
			this.ship.addComponent(this.powerCore);
			this.ship.addComponent(this.component);
		}
		catch (Exception e)
		{
			fail(e.toString());
		}
		this.component.setEnabled(true);
		assertTrue(this.component.isEnabled(), "Expected component to be enabled");
	}

	@Test
	void testIsBroken()
	{
		assertFalse(this.component.isBroken(), "Default condition should be unbroken");
		this.component.setCurrHP(0);
		assertTrue(this.component.isBroken(), "Component should be broken");
	}

	@Test
	void testGetPowerRequired()
	{
		assertEquals(1, this.component.getPowerRequired(), "Mismatch in default power required");
		this.component.setEnabled(false);
		assertEquals(0, this.component.getPowerRequired(), "Mismatch in disabled power required");
		
		assertEquals(1, this.smallWeapon.getPowerRequired(), "Mismatch in default power required");
		assertEquals(2, this.mediumWeapon.getPowerRequired(), "Mismatch in default power required");
		assertEquals(3, this.largeWeapon.getPowerRequired(), "Mismatch in default power required");
		assertEquals(4, this.capitalWeapon.getPowerRequired(), "Mismatch in default power required");
	}

	@Test
	void testIsArmored()
	{
		assertFalse(this.component.isArmored(), "Default state should be not armored");
		assertEquals(0, this.component.getMaxArmor(), "Mismatch in max armor value");
		assertEquals(0, this.component.getCurrArmor(), "Mismatch in current armor value");
		
		ShipComponent armoredComponent = new ShipComponent(this.ship, true, false, false, false)
		{
			@Override
			public String getDescription()
			{
				return "Unit Test Object";
			}

			@Override
			public String getGenericName()
			{
				return "Unit Test Object";
			}

			@Override
			public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
			{
				Set<PlayerIntent> intents = super.getDefaultIntents(targetShip);
				return intents;
			}

			@Override
			public Set<ReactionIntent> getPossibleReactions()
			{
				Set<ReactionIntent> reactions = super.getDefaultReactions();
				return reactions;
			}

			@Override
			public int getComponentBonus()
			{
				// TODO Auto-generated method stub
				return 0;
			}
		};

		assertTrue(armoredComponent.isArmored(), "Mismatch in armored value");
		assertEquals(1, armoredComponent.getMaxArmor(), "Mismatch in max armor value");
		assertEquals(1, armoredComponent.getCurrArmor(), "Mismatch in current armor value");
	}

	@Test
	void testIsShielded()
	{
		assertFalse(this.component.isShielded(), "Default state should be not shielded");
		assertEquals(0, this.component.getMaxShields(), "Mismatch in max shields value");
		assertEquals(0, this.component.getCurrShields(), "Mismatch in current shields value");
		
		ShipComponent shieldedComponent = new ShipComponent(this.ship, false, true, false, false)
		{
			@Override
			public String getDescription()
			{
				return "Unit Test Object";
			}

			@Override
			public String getGenericName()
			{
				return "Unit Test Object";
			}

			@Override
			public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
			{
				Set<PlayerIntent> intents = super.getDefaultIntents(targetShip);
				return intents;
			}

			@Override
			public Set<ReactionIntent> getPossibleReactions()
			{
				Set<ReactionIntent> reactions = super.getDefaultReactions();
				return reactions;
			}

			@Override
			public int getComponentBonus()
			{
				// TODO Auto-generated method stub
				return 0;
			}
		};

		assertTrue(shieldedComponent.isShielded(), "Mismatch in shields value");
		assertEquals(5, shieldedComponent.getMaxShields(), "Mismatch in max shields value");
		assertEquals(5, shieldedComponent.getCurrShields(), "Mismatch in current shields value");
	}
	
	@Test
	void testGetSimpleStatus()
	{
		// Enabled, Undamaged
		assertEquals("[ ]", this.component.getSimpleStatus());
		// Disabled, Undamaged
		this.component.setEnabled(false);
		assertEquals("{ }", this.component.getSimpleStatus());
		// Disabled, Damaged
		int maxHP = this.component.getMaxHP();
		int currHP = maxHP - 1;
		this.component.setCurrHP(currHP);
		assertEquals("{" + currHP + "}", this.component.getSimpleStatus());
		// Enabled, Damaged
		this.component.setEnabled(true);
		assertEquals("[" + currHP + "]", this.component.getSimpleStatus());
		// Enabled, Lost Power
		this.component.setLostPower(true);
		assertEquals("[P]", this.component.getSimpleStatus());
		// Disabled, Lost Power
		this.component.setEnabled(false);
		assertEquals("{P}", this.component.getSimpleStatus());
		// Disabled, Broken
		this.component.setCurrHP(0);
		assertEquals("{X}", this.component.getSimpleStatus());
		// Enabled, Broken
		this.component.setEnabled(true);
		assertEquals("[X]", this.component.getSimpleStatus());
	}
	
	@Test
	void testGetComponentBonus() throws Exception
	{
		PowerCore powerCore = new PowerCore(this.ship);
		this.ship.addComponent(powerCore);
		assertEquals(0, this.ship.getSensorArrayBonus());
		SensorArray sensorArray1 = new SensorArray(this.ship);
		this.ship.addComponent(sensorArray1);
		assertEquals(1, this.ship.getSensorArrayBonus());
		SensorArray sensorArray2 = new SensorArray(this.ship);
		this.ship.addComponent(sensorArray2);
		assertEquals(2, this.ship.getSensorArrayBonus());
		SensorArray sensorArray3 = new SensorArray(this.ship);
		this.ship.addComponent(sensorArray3);
		assertEquals(3, this.ship.getSensorArrayBonus());
		SensorArray sensorArray4 = new SensorArray(this.ship);
		this.ship.addComponent(sensorArray4);
		assertEquals(4, this.ship.getSensorArrayBonus());
		SensorArray sensorArray5 = new SensorArray(this.ship);
		this.ship.addComponent(sensorArray5);
		assertEquals(5, this.ship.getSensorArrayBonus());
		sensorArray2.setEnabled(false);
		assertEquals(4, this.ship.getSensorArrayBonus());
		sensorArray2.setCurrHP(0);
		assertEquals(4, this.ship.getSensorArrayBonus());
		sensorArray2.setEnabled(true);
		assertEquals(4, this.ship.getSensorArrayBonus());
		sensorArray3.setLostPower(true);
		assertEquals(3, this.ship.getSensorArrayBonus());
		powerCore.setCurrHP(1);
		this.ship.onPowerCoreDamaged();
		assertEquals(1, this.ship.getSensorArrayBonus());
	}
}
