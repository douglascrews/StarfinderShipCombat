package com.dougcrews.game.starfinder.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Dice;
import com.dougcrews.game.starfinder.model.Distance;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ShipToShipRelation;
import com.dougcrews.game.starfinder.model.ShipToShipRelation.DistanceIntent;
import com.dougcrews.game.starfinder.model.Target;
import com.dougcrews.game.starfinder.model.intent.Intent;
import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Faction;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;
import com.dougcrews.game.starfinder.model.ship.component.AIModule;
import com.dougcrews.game.starfinder.model.ship.component.Armor;
import com.dougcrews.game.starfinder.model.ship.component.CommGrid;
import com.dougcrews.game.starfinder.model.ship.component.Communications;
import com.dougcrews.game.starfinder.model.ship.component.Engine;
import com.dougcrews.game.starfinder.model.ship.component.Flak;
import com.dougcrews.game.starfinder.model.ship.component.PassiveSensors;
import com.dougcrews.game.starfinder.model.ship.component.PowerCore;
import com.dougcrews.game.starfinder.model.ship.component.SecurityModule;
import com.dougcrews.game.starfinder.model.ship.component.SensorArray;
import com.dougcrews.game.starfinder.model.ship.component.ShieldGenerator;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent.Size;
import com.dougcrews.game.starfinder.model.ship.component.StealthField;
import com.dougcrews.game.starfinder.model.ship.component.Thrusters;
import com.dougcrews.game.starfinder.model.ship.component.TractorBeam;
import com.dougcrews.game.starfinder.model.ship.component.weapon.EnergyWeapon;
import com.dougcrews.game.starfinder.model.ship.component.weapon.GuidedMissile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.KineticWeapon;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.MissileAlreadyLoadedException;
import com.dougcrews.game.starfinder.model.ship.component.weapon.MissileLauncher;
import com.dougcrews.game.starfinder.model.ship.component.weapon.SeekerMissile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

public class CombatController
{
	private static final Logger log = LogManager.getLogger(CombatController.class.getName());

	// Singleton
	private static final CombatController cc = new CombatController();
	
	private Map<String, Player> players = new HashMap<String, Player>();
	private Map<String, Ship> ships = new HashMap<String, Ship>();
	private Set<ShipToShipRelation> shipRelations = new HashSet<ShipToShipRelation>();
	private boolean combatComplete = false;
	private List<PlayerIntent> playerIntents = new ArrayList<PlayerIntent>();
	private Map<Player, Boolean> playersReadyMap = new HashMap<Player, Boolean>();
	private int maxRounds = 100;
	private int currRound = 1;

	// TODO: temporary, remove when UI can build & add ships and players
	public void generateTestArena()
	{
		Ship enterprise = new Ship(Tier.TWENTY, Frame.CRUISER);
		enterprise.setName("USS Enterprise");
		enterprise.setFaction(Faction.PLAYER);
		try
		{
			PowerCore pc1 = new PowerCore(enterprise);
			PowerCore pc2 = new PowerCore(enterprise);
			PowerCore pc3 = new PowerCore(enterprise);
			enterprise.addComponent(pc1);
			enterprise.addComponent(pc2);
			enterprise.addComponent(pc3);
			enterprise.addComponent(new AIModule(enterprise));
			enterprise.addComponent(new AIModule(enterprise));
			enterprise.addComponent(new AIModule(enterprise));
			enterprise.addComponent(new AIModule(enterprise));
			enterprise.addComponent(new AIModule(enterprise));
			enterprise.addComponent(new Armor(enterprise, true));
			enterprise.addComponent(new CommGrid(enterprise));
			enterprise.addComponent(new Communications(enterprise, false, false, false, true));
			enterprise.addComponent(new Engine(enterprise));
			enterprise.addComponent(new Flak(enterprise));
			enterprise.addComponent(new PassiveSensors(enterprise));
			enterprise.addComponent(new PassiveSensors(enterprise));
			enterprise.addComponent(new PassiveSensors(enterprise));
			enterprise.addComponent(new PassiveSensors(enterprise));
			enterprise.addComponent(new PassiveSensors(enterprise));
			enterprise.addComponent(new SecurityModule(enterprise));
			enterprise.addComponent(new SensorArray(enterprise, false, true, false, true));
			enterprise.addComponent(new SensorArray(enterprise));
			enterprise.addComponent(new ShieldGenerator(enterprise));
			enterprise.addComponent(new ShieldGenerator(enterprise));
			enterprise.addComponent(new StealthField(enterprise));
			enterprise.addComponent(new Thrusters(enterprise));
			enterprise.addComponent(new TractorBeam(enterprise));
			enterprise.addComponent(new EnergyWeapon(enterprise, Size.SMALL));
			enterprise.addComponent(new KineticWeapon(enterprise, Size.MEDIUM));
			enterprise.addComponent(new MissileLauncher(enterprise, Size.LARGE));
			pc1.hitHull(11, true, true, 0);
			enterprise.onPowerCoreDamaged();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		this.addShip(enterprise);
//		enterprise.hitShields(6, true, true, 0);
//		enterprise.hitArmor(1, true, true, 0);
//		enterprise.hitHull(20, true, true, 0);
		
		Ship klingon = new Ship(Tier.FIFTEEN, Frame.DESTROYER);
		klingon.setName("Klingon Battle Cruiser");
		klingon.setFaction(Faction.NPC);
		try
		{
			klingon.addComponent(new PowerCore(klingon));
			klingon.addComponent(new PowerCore(klingon));
			klingon.addComponent(new PowerCore(klingon));
			klingon.addComponent(new EnergyWeapon(klingon, Size.MEDIUM));
			klingon.addComponent(new KineticWeapon(klingon, Size.MEDIUM));
			klingon.addComponent(new PassiveSensors(klingon));
			klingon.addComponent(new PassiveSensors(klingon));
			klingon.addComponent(new PassiveSensors(klingon));
			klingon.addComponent(new PassiveSensors(klingon));
			klingon.addComponent(new PassiveSensors(klingon));
			klingon.addComponent(new ShieldGenerator(klingon));
			klingon.addComponent(new ShieldGenerator(klingon, false, true, false));
			klingon.addComponent(new AIModule(klingon));
			klingon.addComponent(new AIModule(klingon));
			klingon.addComponent(new AIModule(klingon));
			klingon.addComponent(new AIModule(klingon));
			klingon.addComponent(new AIModule(klingon));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		this.addShip(klingon);
		
		Ship xwing = new Ship(Tier.TEN, Frame.FIGHTER);
		xwing.setName("Luke's X-Wing");
		xwing.setFaction(Faction.NPC);
		try
		{
			xwing.addComponent(new PowerCore(xwing));
			xwing.addComponent(new EnergyWeapon(xwing, Size.SMALL));
			xwing.addComponent(new MissileLauncher(xwing, Size.SMALL));
			xwing.addComponent(new PassiveSensors(xwing));
			xwing.addComponent(new PassiveSensors(xwing));
			xwing.addComponent(new ShieldGenerator(xwing, false, true, false));
			xwing.addComponent(new AIModule(xwing));
			xwing.addComponent(new AIModule(xwing));
			xwing.addComponent(new AIModule(xwing));
			xwing.addComponent(new AIModule(xwing));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		this.addShip(xwing);

		Ship submarine = new Ship(Tier.FIVE, Frame.DESTROYER);
		submarine.setName("U-Boat");
		submarine.setFaction(Faction.NPC);
		try
		{
			submarine.addComponent(new PowerCore(submarine));
			submarine.addComponent(new StealthField(submarine));
			submarine.addComponent(new StealthField(submarine));
			submarine.addComponent(new StealthField(submarine));
			submarine.addComponent(new StealthField(submarine));
			submarine.addComponent(new StealthField(submarine));
			submarine.addComponent(new PassiveSensors(submarine));
			submarine.addComponent(new PassiveSensors(submarine));
			submarine.addComponent(new MissileLauncher(submarine, Size.SMALL));
			submarine.addComponent(new MissileLauncher(submarine, Size.SMALL));
			submarine.addComponent(new AIModule(submarine));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		this.addShip(submarine);
		
		Player kirk = new Player("James T. Kirk", enterprise, 20, 20, 20, 20, 20, 20);
		this.addPlayer(kirk);
		enterprise.setCaptain(kirk);
		Player spock = new Player("Mr. Spock", enterprise, 3, 4, 10, 20, 5, 5);
		this.addPlayer(spock);
		spock.setCurrentConsole(enterprise.getAvailableComponent("Sensor Array"));
		Player chekhov = new Player("Pavel Chekhov", enterprise, 5, 3, 1, 1, 0, 0);
		this.addPlayer(chekhov);
		enterprise.setPilot(chekhov);
		Player sulu = new Player("Hikaru Sulu", enterprise, 2, 5, 1, 1, 0, 0);
		this.addPlayer(sulu);
		sulu.setCurrentConsole(enterprise.getAvailableComponent("Energy Weapon"));
		Player uhura = new Player("Niobe Uhura", enterprise, 2, 2, 1, 1, 5, 5);
		this.addPlayer(uhura);
		uhura.setCurrentConsole(enterprise.getAvailableComponent("Comm Grid"));
		
		Player luke = new Player("Luke Starkiller", xwing, 7, 7, 1, 1, 3, 3);
		this.addPlayer(luke);

		enterprise.runPassiveSensorScan();
		klingon.runPassiveSensorScan();
		xwing.runPassiveSensorScan();
		submarine.runPassiveSensorScan();
		
		this.startCombatRound();
}

	// TODO: temporary, evolve this into a full-fledged combat AI
	private void setAutomatedPilotIntents()
	{
		for (ShipToShipRelation s2sr : this.shipRelations)
		{
			Distance distance1 = s2sr.getShip1().getPreferredDistanceFrom(s2sr.getShip2());
			ShipToShipRelation.DistanceIntent intent1 = ShipToShipRelation.getIntentFor(distance1, s2sr.getDistance());
			s2sr.setShip1DistanceIntent(intent1);

			Distance distance2 = s2sr.getShip2().getPreferredDistanceFrom(s2sr.getShip1());
			ShipToShipRelation.DistanceIntent intent2 = ShipToShipRelation.getIntentFor(distance2, s2sr.getDistance());
			s2sr.setShip2DistanceIntent(intent2);
		}
	}
	
	/**
	 * Starts combat
	 * @param automated Execute the entire combat without user interaction. Useful for Unit Tests.
	 */
	public void startCombat(boolean automated)
	{
		log.trace("startCombat()");
		log.info("Combat starting");
		for (Ship ship : this.getShips())
		{
			log.info(ship.getStatus());
		}
		this.currRound = 0;
	}

	public void combatRound(boolean automated)
	{
		log.trace("combatRound()");
		startCombatRound();
		if (automated) setAutomatedPilotIntents();
		getPilotIntents();
		moveShips();
		getPlayerIntents();
		handlePlayerIntents();
		moveMissiles();
		endCombatRound();
	}
	
	public void startCombatRound()
	{
		log.trace("startCombatRound()");
		this.currRound++;
		log.info("********** Starting Round " + this.currRound);
		this.resetRound();
		this.rollInitiatives();
	}

	public void processCombatRound()
	{
		this.getPilotIntents();
		this.moveShips();
		this.moveMissiles();
		this.getPlayerIntents();
		this.handlePlayerIntents();
	}

	private void getPilotIntents()
	{
		log.trace("getPilotIntents()");
//		boolean intentsComplete = false; // Do we have all the intents we need?
//		Map<Player, List<String>> playerMessages = new HashMap<Player, List<String>>();
//		while (! intentsComplete)
//		{
//			intentsComplete = true;
			for (ShipToShipRelation s2sr : this.shipRelations)
			{
				Ship ship1 = s2sr.getShip1();
				Ship ship2 = s2sr.getShip2();
				Distance actual = s2sr.getDistance();
				Distance s1preferred = ship1.getPreferredDistanceFrom(ship2);
				DistanceIntent s1intent = DistanceIntent.STATUS_QUO;
				if (s1preferred.compareTo(actual) > 0) s1intent = DistanceIntent.MOVE_FARTHER;
				if (s1preferred.compareTo(actual) < 0) s1intent = DistanceIntent.MOVE_CLOSER;
				s2sr.setShip1DistanceIntent(s1intent);

				Distance s2preferred = ship2.getPreferredDistanceFrom(ship1);
				DistanceIntent s2intent = DistanceIntent.STATUS_QUO;
				if (s2preferred.compareTo(actual) > 0) s2intent = DistanceIntent.MOVE_FARTHER;
				if (s2preferred.compareTo(actual) < 0) s2intent = DistanceIntent.MOVE_CLOSER;
				s2sr.setShip2DistanceIntent(s2intent);

// This should never happen; Constructor prohibits it
//				if (s2sr.getShip1().getFaction().equals(s2sr.getShip2().getFaction()))
//				{
//					// Same faction, no need to set pilot intents
//					s2sr.setShip1DistanceIntent(ShipToShipRelation.DistanceIntent.STATUS_QUO);
//					s2sr.setShip2DistanceIntent(ShipToShipRelation.DistanceIntent.STATUS_QUO);
//				}
//				if (s2sr.getShip1DistanceIntent() == null)
//				{
//					intentsComplete = false;
//					Player pilot = s2sr.getShip1().getPilot();
//					if (! playerMessages.containsKey(pilot))
//					{
//						playerMessages.put(pilot, new ArrayList<String>());
//					}
//					playerMessages.get(pilot).add("Need pilot intent for " + s2sr.getShip2().getName());
//				}
//				if (s2sr.getShip2DistanceIntent() == null)
//				{
//					intentsComplete = false;
//					Player pilot = s2sr.getShip2().getPilot();
//					if (! playerMessages.containsKey(pilot))
//					{
//						playerMessages.put(pilot, new ArrayList<String>());
//					}
//					playerMessages.get(pilot).add("Need pilot intent for " + s2sr.getShip1().getName());
//				}
			}
//		}
	}

	private void moveShips()
	{
		log.trace("moveShips()");
		for (ShipToShipRelation s2sr : this.shipRelations)
		{
			Ship ship1 = s2sr.getShip1();
			Ship ship2 = s2sr.getShip2();
			ShipToShipRelation.DistanceIntent intent = null;
			if (ship1.getFaction().equals(ship2.getFaction()))
			{
				// Same faction; no need to determine relative movement
				log.warn("ShipToShipRelation should not exist with the same Faction on both sides: " + s2sr);
				continue;
			}
			if (s2sr.getShip1DistanceIntent().equals(s2sr.getShip2DistanceIntent()))
			{
				// Hey, we agree! Let's do the thing!
				intent = s2sr.getShip1DistanceIntent();
			}
			else
			{
				// We disagree. Fight it out!
				int pilot1Roll = ship1.getPilot().rollPilotSkill();
				int pilot2Roll = ship2.getPilot().rollPilotSkill();
				if (pilot1Roll > pilot2Roll)
				{
					// Ship1 wins
					intent = s2sr.getShip1DistanceIntent();
					log.info(ship1.getName() + " gains the upper hand in maneuvers against " + ship2.getName() + "!");
				}
				else if (pilot2Roll > pilot1Roll)
				{
					// Ship2 wins
					intent = s2sr.getShip2DistanceIntent();
					log.info(ship2.getName() + " gains the upper hand in maneuvers against " + ship1.getName() + "!");
				}
				else
				{
					// Tie maintains Status Quo
					intent = ShipToShipRelation.DistanceIntent.STATUS_QUO;
					log.info(ship1.getName() + " and " + ship2.getName() + " maneuver, but neither gains the upper hand.");
				}
			}
			
			// Ships move relative to each other
			if (intent.equals(ShipToShipRelation.DistanceIntent.MOVE_CLOSER))
			{
				s2sr.moveCloser();
			}
			else if (intent.equals(ShipToShipRelation.DistanceIntent.MOVE_FARTHER))
			{
				s2sr.moveFarther();
			}
			else if (intent.equals(ShipToShipRelation.DistanceIntent.STATUS_QUO))
			{
				s2sr.moveStatusQuo();
			}
			else
			{
				throw new RuntimeException("Unrecognized ShipToShipRelation.Intent: " + intent);
			}
			
//			// Set whether ships are facing toward or away from each other
//			s2sr.setFacing(ship1.getPilot().rollPilotSkill(), ship2.getPilot().rollPilotSkill());

			log.debug("Status: " + s2sr);
		}
	}

	private void moveMissiles()
	{
		log.trace("moveMissiles()");
		
		for (Ship ship : this.getShips())
		{
			List<Missile> missiles = ship.getActiveMissiles();
			for (Missile missile : missiles)
			{
				log.debug("Moving missile " + missile.getSimpleDescription());
				missile.move();
			}
		}
	}
	
	/**
	 * Returns a list of all missiles from all ships currently active in this combat.
	 * @return all active missiles
	 */
	public List<Missile> getActiveMissiles()
	{
		List<Missile> missiles = new ArrayList<Missile>();
		for (Ship ship : this.getShips())
		{
			// Missiles must retain Target Lock and are thus always visible
			missiles.addAll(ship.getActiveMissiles());
		}
		return missiles;
	}
	
	/**
	 * Call this when the player has finished selecting Intents and is ready to proceed
	 * @param player
	 */
	public void finalizePlayerIntents(Player player)
	{
		this.playersReadyMap.put(player, true);
	}

	public boolean isPlayerFinalized(Player player)
	{
		return this.playersReadyMap.get(player).booleanValue();
	}
	
	private void getPlayerIntents()
	{
		log.trace("getPlayerIntents()");
		this.playerIntents.clear();
		// TODO: get Player Intents
	}

	private void handlePlayerIntents()
	{
		log.trace("handlePlayerIntents()");
		TreeSet<Ship> sortedShips = new TreeSet<Ship>(this.ships.values());
		// Crew and Gunners act
		for (Ship ship : sortedShips.descendingSet())
		{
			log.info(ship.toString());
			if (ship.isActive())
			{
				for (Player player : ship.getAllCrewMembers())
				{
					for (Intent intent : player.getIntents())
					{
						log.debug(player.getName() + " attempting " + intent);
						intent.go();
					}
				}
				
				// Weapons fire
				for (Weapon weapon : ship.getWeapons()) // TODO: testing only, replace with Player Intents
				{
					if (weapon instanceof MissileLauncher)
					{
						MissileLauncher ml = (MissileLauncher) weapon;
						if (! ml.isLoaded())
						{
							try
							{
								ml.loadMissile(Dice.flipACoin() ? new SeekerMissile(ml) : new GuidedMissile(ml));
							} catch (@SuppressWarnings("unused") MissileAlreadyLoadedException e)
							{
								// Ignore
							}
						}
					}
					Target target = null;
					for (Ship possibleTarget : this.ships.values())
					{
						if (possibleTarget != ship)
						{
							target = possibleTarget; // Found it!
							break;
						}
					}
					if (target != null && target.isActive())
					{
						if (weapon.canTarget(target.getDistanceFrom(ship)))
						{
							@SuppressWarnings("unused")
							boolean isSuccess = weapon.fire(weapon.getCurrentPlayer(), target);
						}
						else
						{
							log.info(weapon.getName() + " cannot target " + target.getName() + "!");
						}
					}
				}
			}
			else
			{
				log.debug(ship.getName() + " not acting this round because it is disabled");
			}
		}
	}
	
	private void endCombatRound()
	{
		log.trace("endCombatRound()");
		log.info("********** End of Round " + this.currRound);
		for (Ship ship : this.getShips())
		{
			log.info(ship.getStatus());
			log.info("Active missiles:");
			for (Missile missile : ship.getActiveMissiles())
			{
				log.info(missile.getStatus());
			}
		}
		if (getActiveShips() <= 1)
		{
			this.combatComplete = true;
		}
		if (this.currRound >= this.maxRounds)
		{
			log.info("A stalemate is declared and all remaining ships return home");
			this.combatComplete = true;
		}
		// Winner?
		if (getActiveShips() <= 1)
		{
			for (Ship ship : this.ships.values())
			{
				if (ship.isActive()) log.info(ship.getName() + " wins!");
			}
			this.combatComplete = true;
		}
	}

	public void endCombat()
	{
		log.info("Combat complete after " + this.currRound + " rounds. Final status:");
		for (Ship ship : this.ships.values())
		{
			log.info(ship.toString());
		}
	}
	
	private void resetRound()
	{
		for (Ship ship : this.ships.values())
		{
			ship.resetTargetLockBonus();
			ship.runPassiveSensorScan();
			for (ShipComponent component : ship.getComponents())
			{
				component.setHasBeenUsedThisRound(false);
			}
			for (Weapon weapon : ship.getWeapons())
			{
				weapon.setCriticalHitBonus(0);
				weapon.setCriticalHitExtraDice(0);
				weapon.setOverchargeDRIgnore(0);
				weapon.setOverchargeToHitBonus(0);
				weapon.setOverchargeDamageBonus(0);
				weapon.setFireAtWillPenalty(0);
			}
			for (Player player : ship.getAllCrewMembers())
			{
				player.resetActionsThisRound();
			}
		}

		for (ShipToShipRelation s2sr : this.shipRelations)
		{
			// TODO: Probably should find a way to retain the previous intent for convenience
			s2sr.setShip1DistanceIntent(null);
			s2sr.setShip2DistanceIntent(null);
		}
		this.playersReadyMap.clear();
		for (Player player : this.getPlayers()) this.playersReadyMap.put(player, false);
	}
	
	private void rollInitiatives()
	{
		log.trace("rollInitiatives()");
		for (Ship ship : this.ships.values())
		{
			ship.rollInitiative();
		}
	}

	/**
	 * @return the ships in descending Initiative order
	 */
	public List<Ship> getShipInitiatives()
	{
		List<Ship> ships = new LinkedList<Ship>();
		ships.addAll(this.ships.values());
		log.debug("ship initiative before sorting: " + ships);
		ships.sort(new Comparator<Ship>()
		{
			@Override
			public int compare(Ship thisShip, Ship thatShip)
			{
				int init1 = thisShip.getInitiative();
				int init2 = thatShip.getInitiative();
				if (init1 != init2) return (init1 < init2 ? 1 : -1);

				int pilot1 = thisShip.getPilot().getPilotBonus();
				int pilot2 = thatShip.getPilot().getPilotBonus();
				if (pilot1 != pilot2) return (pilot1 < pilot2 ? 1 : -1);
				
				int shipBonus1 = thisShip.getEngineBonus() + thisShip.getThrustersBonus() + thisShip.getFrame().getManeuverability().getPilotingBonus();
				int shipBonus2 = thatShip.getEngineBonus() + thatShip.getThrustersBonus() + thatShip.getFrame().getManeuverability().getPilotingBonus();
				if (shipBonus1 != shipBonus2) return (shipBonus1 < shipBonus2 ? 1 : -1);
				
				return (Dice.flipACoin() ? 1 : -1); // Fuck it, flip a coin
			}
		});
		log.debug("ship initiative after sorting: " + ships);
		return ships;
	}

	/**
	 * A ship has been disabled and is out of the fight.
	 * 
	 * @param ship
	 */
	public void shipDisabled(Ship ship)
	{
		log.trace("shipDisabled()");
		log.info(ship.getName() + " is disabled and out of the fight!");
		removeShip(ship);
	}

	public void addPlayer(Player player)
	{
		this.players.put(player.getName(), player);
	}

	public Collection<Player> getPlayers()
	{
		return this.players.values();
	}
	
	public Player getPlayer(String name)
	{
		return this.players.get(name);
	}
	
	public void addShip(Ship ship)
	{
		if (this.ships.containsValue(ship))
		{
			log.error(ship + " has already been registered for this combat");
			return;
		}

		for (Ship otherShip : this.ships.values())
		{
			if (otherShip.getFaction().equals(ship.getFaction())) continue;
			ShipToShipRelation s2sr = new ShipToShipRelation(ship, otherShip);
			this.shipRelations.add(s2sr);
		}
		this.ships.put(ship.getId(), ship);
		ship.setController(this);
	}

	public void removeShip(Ship ship)
	{
		log.trace("removeShip()");
		Set<ShipToShipRelation> toBeDeleted = new HashSet<ShipToShipRelation>();
		for (ShipToShipRelation s2sr : this.shipRelations)
		{
			if (s2sr.contains(ship))
			{
				toBeDeleted.add(s2sr);
			}
		}
		this.shipRelations.removeAll(toBeDeleted);
	}

	public int getActiveShips()
	{
		int activeShips = 0;
		for (Ship ship : this.ships.values())
		{
			if (ship.isActive()) activeShips++;
		}
		return activeShips;
	}

	public Distance getDistanceBetween(Ship ship1, Ship ship2)
	{
		if (ship1 == ship2) return Distance.POINT_BLANK;
		Distance distance = null;
		for (ShipToShipRelation s2sr : this.shipRelations)
		{
			if (s2sr.contains(ship1) && s2sr.contains(ship2))
			{
				distance = s2sr.getDistance();
				break;
			}
		}
		if (distance == null)
		{
			log.error("Did not find ShipToShipRelation for " + ship1 + " and " + ship2);
			distance = Distance.BEYOND_EXTREME; // NOTE: failsafe rather than throw exception
		}
		return distance;
	}
	
	// Constructors
	
	public CombatController() {};
	
	// Getters & Setters
	
	public static final CombatController getInstance()
	{
		return cc;
	}
	
	public Collection<Ship> getShips()
	{
		return this.ships.values();
	}

	public Ship getShip(String shipId)
	{
		return this.ships.get(shipId);
	}
	
	public Set<ShipToShipRelation> getShipRelations()
	{
		return this.shipRelations;
	}

	public boolean isCombatComplete()
	{
		return this.combatComplete;
	}

	@SuppressWarnings("unused")
	private void setCombatComplete(boolean combatComplete)
	{
		this.combatComplete = combatComplete;
	}

	public void setMaxRounds(int maxRounds)
	{
		this.maxRounds = maxRounds;
	}

	public int getMaxRounds()
	{
		return this.maxRounds;
	}
	
	/**
	 * @return List of Players who have not yet finalized their actions this round. Sort yourself out!
	 */
	public List<Player> getWaitingOnList()
	{
		List<Player> players = new ArrayList<Player>();
		for (Player player : this.playersReadyMap.keySet())
		{
			if (this.playersReadyMap.get(player).equals(Boolean.FALSE)) players.add(player);
		}
		return players;
	}
}
