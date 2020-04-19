package com.dougcrews.game.starfinder.model.intent;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.controller.CombatController;
import com.dougcrews.game.starfinder.model.Distance;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ShipToShipRelation;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Faction;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent.Size;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

public abstract class PlayerIntentWeaponAbstractTest
{
	protected Ship ship;
	protected Weapon weapon;
	protected Player gunner;
	protected Ship targetShip;
	protected CombatController controller;

	@BeforeEach
	void setUp() throws Exception
	{
		this.ship = new Ship(Tier.ONE, Frame.FIGHTER);
		this.ship.setFaction(Faction.PLAYER);
		this.weapon = new Weapon(this.ship, Size.SMALL)
		{
			@Override
			public String getDescription()
			{
				return "Unit Test Weapon";
			}
			
			@Override
			public String getGenericName()
			{
				return "Unit Test Weapon";
			}
			
			@Override
			public int getDamageDiceType()
			{
				return 4;
			}

			@Override
			public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
			{
				return super.getPossibleIntents(targetShip);
			}

			@Override
			public Set<ReactionIntent> getPossibleReactions()
			{
				return super.getPossibleReactions();
			}
		};
		this.gunner = new Player("Unit Test Ace Gunner", this.ship, 0, 20, 0, 0, 0, 0);
		this.weapon.setCurrentPlayer(this.gunner);
		this.targetShip = new Ship(Tier.ONE, Frame.FIGHTER);
		this.targetShip.setFaction(Faction.NPC);
		this.controller = new CombatController();
		
		this.controller.addShip(this.ship);
		this.controller.addShip(this.targetShip);
		for (ShipToShipRelation s2sr : this.controller.getShipRelations())
		{
			s2sr.setDistance(Distance.POINT_BLANK);
		}
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.ship = null;
		this.weapon = null;
		this.gunner = null;
		this.targetShip = null;
		this.controller = null;
	}
	
	@Test
	public void test()
	{
	}
}
