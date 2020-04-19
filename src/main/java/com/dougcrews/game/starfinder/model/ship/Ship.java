package com.dougcrews.game.starfinder.model.ship;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.controller.CombatController;
import com.dougcrews.game.starfinder.model.Dice;
import com.dougcrews.game.starfinder.model.Distance;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.Target;
import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.component.AIModule;
import com.dougcrews.game.starfinder.model.ship.component.Armor;
import com.dougcrews.game.starfinder.model.ship.component.CommGrid;
import com.dougcrews.game.starfinder.model.ship.component.Communications;
import com.dougcrews.game.starfinder.model.ship.component.DuplicateComponentException;
import com.dougcrews.game.starfinder.model.ship.component.Engine;
import com.dougcrews.game.starfinder.model.ship.component.Flak;
import com.dougcrews.game.starfinder.model.ship.component.InsufficientPowerException;
import com.dougcrews.game.starfinder.model.ship.component.PassiveSensors;
import com.dougcrews.game.starfinder.model.ship.component.PowerCore;
import com.dougcrews.game.starfinder.model.ship.component.SecurityModule;
import com.dougcrews.game.starfinder.model.ship.component.SensorArray;
import com.dougcrews.game.starfinder.model.ship.component.ShieldGenerator;
import com.dougcrews.game.starfinder.model.ship.component.ShipBuildException;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;
import com.dougcrews.game.starfinder.model.ship.component.StealthField;
import com.dougcrews.game.starfinder.model.ship.component.Thrusters;
import com.dougcrews.game.starfinder.model.ship.component.TractorBeam;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.MissileLauncher;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

public class Ship implements Target, Comparable<Ship>
{
	private static final Logger log = LogManager.getLogger(Ship.class.getName());
	
	protected String name;
	protected Tier tier;
	protected Frame frame;
	protected int currHP;
	protected Faction faction;
	protected Player pilot;
	protected Player captain;
	protected Set<Player> crewMembers = new HashSet<Player>();
	protected Map<String, ShipComponent> componentsMap = new HashMap<String, ShipComponent>();
	protected int initiative = -1;
	protected int targetLockBonus; // Due to crew actions or other effects; reset on being used
	protected CombatController controller;
	protected List<ShipComponent> poweredComponents = new ArrayList<ShipComponent>();
	protected Map<Ship, Boolean> facingMap = new HashMap<Ship, Boolean>();
	protected Map<Ship, Distance> preferredDistanceMap = new HashMap<Ship, Distance>();
	protected Map<Ship, Boolean> preferredFacingForeMap = new HashMap<Ship, Boolean>();
	protected Map<Ship, DetectedShip> detectedShipsMap = new HashMap<Ship, DetectedShip>();
	protected Map<ShipComponent, Integer> detectedComponentsMap = new HashMap<ShipComponent, Integer>();

	@Override
	public int hitShields(int damage, boolean causeDamage, boolean useDR, int ignoreDR)
	{
		log.trace("hitShields()");
		int damageRemaining = damage;
		int damageDone = Math.min(damage, this.getCurrShields()); // Can't do more damage than shields value
		// For weapons that are absorbed by shields
		if (damageDone > 0 && useDR)
		{
			damageRemaining -= damageDone;
			if (ignoreDR > 0)
			{
				log.debug(ignoreDR + " points of Damage Reduction are ignored");
				damageRemaining += ignoreDR;
			}
			log.info("Shields absorb " + damageDone + " damage");
		}
		else
		{
			log.debug("Damage is unaffected by shields");
		}

		// For weapons that damage the shields
		if (damageDone > 0 && causeDamage)
		{
			while (damageDone > 0)
			{
				int randomShield = (int)(Math.random() * this.getShieldGeneratorComponents().size());
				ShieldGenerator shield = this.getShieldGeneratorComponents().get(randomShield);
				if (shield.isActive())
				{
					int damageToThisShield = Math.min(damageDone, shield.getCurrHP());
					shield.setCurrHP(shield.getCurrHP() - damageToThisShield);
					damageDone -= damageToThisShield;
					log.info(shield.getName() + " damaged for " + damageToThisShield + " points!");
					if (shield.getCurrHP() == 0)
					{
						log.info(shield.getName() + " is disabled by battle damage!");
					}
				}
			}
		}
		else
		{
			log.debug("Shields are unaffected by damage");
		}
		return damageRemaining;
	}

	@Override
	public int hitArmor(int damage, boolean causeDamage, boolean useDR, int ignoreDR)
	{
		log.trace("hitArmor()");
		int damageRemaining = damage;
		int damageDone = Math.min(damage, this.getCurrArmor()); // Can't do more damage than armor value
		// For weapons that are absorbed by armor
		if (damageDone > 0 && useDR)
		{
			damageRemaining -= damageDone;
			if (ignoreDR > 0)
			{
				log.debug(ignoreDR + " points of Damage Reduction are ignored");
				damageRemaining += ignoreDR;
			}
			log.info("Armor absorbs " + damageDone + " damage");
		}
		else
		{
			log.debug("Damage is unaffected by armor");
		}

		// For weapons that damage the armor
		if (damageDone > 0 && causeDamage)
		{
			while (damageDone > 0)
			{
				int randomArmor = (int)(Math.random() * this.getArmorComponents().size());
				Armor armor = this.getArmorComponents().get(randomArmor);
				if (armor.isActive())
				{
					int damageToThisArmor = Math.min(damageDone, armor.getCurrHP());
					armor.setCurrHP(armor.getCurrHP() - damageToThisArmor);
					damageDone -= damageToThisArmor;
					log.info(armor.getName() + " damaged for " + damageToThisArmor + " points!");
					if (armor.getCurrHP() == 0)
					{
						log.info(armor.getName() + " is disabled by battle damage!");
					}
				}
			}
		}
		else
		{
			log.debug("Armor is unaffected by damage");
		}
		return damageRemaining;
	}

	@Override
	public int hitHull(int damage, boolean causeDamage, boolean useDR, int ignoreDR)
	{
		log.trace("hitHull()");
		int damageRemaining = damage;
		int damageDone = Math.min(damage, this.currHP); // Can't do more damage than Hull Points
		// For weapons that are absorbed by hull
		if (damageDone > 0 && useDR)
		{
			damageRemaining -= damageDone;
			if (ignoreDR > 0)
			{
				log.debug(ignoreDR + " points of Damage Reduction are ignored");
				damageRemaining += ignoreDR;
			}
			log.info("Hull absorbs " + damageDone + " damage");
		}
		else
		{
			log.debug("Damage is unaffected by hull");
		}
		// For weapons that damage the hull
		if (damageDone > 0 && causeDamage)
		{
			this.currHP -= damageDone;
			log.info(this.getName() + " hull hit for " + damageDone + " damage!");
		}
		else
		{
			log.debug("Hull is unaffected by damage");
		}
		
		if (this.currHP <= 0)
		{
			log.debug(this.getName() + " is disabled by hull damage");
		}
		return damageRemaining;
	}
	
	@Override
	public Distance getDistanceFrom(Ship ship)
	{
		return this.getController().getDistanceBetween(this, ship);
	}
	
	public Distance getDistanceFrom(Target target)
	{
		Ship ship = null;
		if (target instanceof Ship)
		{
			ship = (Ship)target;
		}
		else if (target instanceof ShipComponent)
		{
			ship = ((ShipComponent)target).getShip();
		}
		
		if (ship == null) throw new RuntimeException("Unrecognized Target type");
		
		return getDistanceFrom(ship);
	}
	
	@Override
	public int getTargetLock()
	{
		int targetLock = 10 + getThrustersBonus() + getStealthFieldBonus() + getSizeMod() - getCurrArmor();
		int targetLockBonus = getTargetLockBonus();
		log.debug("TL = " + targetLock + " + " + targetLockBonus + " = " + (targetLock + targetLockBonus));
		
		return targetLock + targetLockBonus;
	}
	
	/**
	 * Total power available from all active Power Cores. Damaged cores will contribute a proportionally
	 * smaller amount of power.
	 * @return gross power available to ship
	 */
	public int getTotalPowerAvailable()
	{
		int totalPower = 0;
		for (PowerCore powerCore : this.getPowerCoreComponents())
		{
			if (powerCore.isActive()) totalPower += powerCore.getCurrHP();
		}
		return totalPower;
	}

	/**
	 * Total power used by all active components which use power.
	 * @return gross power used by all active components
	 */
	public int getTotalPowerUsed()
	{
		int usedPower = 0;
		for (ShipComponent component : this.getPoweredComponents())
		{
			// Power Cores are excluded because they require negative power to run (they provide power)
			if (component.isActive()) usedPower += Math.max(0, component.getPowerRequired());
		}
		return usedPower;
	}

	/**
	 * Net power available for additional components. Damaged or turned off components do not
	 * require power. Most components require 1 PCU to function, but some (notably large weapons)
	 * require additional power.
	 * @return total PCUs available minus total PCUs currently used
	 */
	public int getNetPowerAvailable()
	{
		return (this.getTotalPowerAvailable() - this.getTotalPowerUsed());
	}
	
	/**
	 * Called when one of the Power Cores is damaged. If insufficient total power is available for all
	 * active components, one or more will be shut down at random due to brownout.
	 */
	public void onPowerCoreDamaged()
	{
		// Select one or more random components to turn off
		while (this.getNetPowerAvailable() < 0)
		{
			// Generate list of components which require power
			List<ShipComponent> activeComponents = new ArrayList<ShipComponent>();
			for (ShipComponent component : this.getPoweredComponents())
			{
				if (component.isActive()) activeComponents.add(component);
			}
			if (activeComponents.size() == 0)
			{
				return; // Edge case: no components to shut down
			}
			int randomComponent = (int)(Math.random() * activeComponents.size());
			ShipComponent component = activeComponents.get(randomComponent);
			if (component.isLostPower()) continue;
			component.setLostPower(true);
			log.info(component.getShip().getName() + " " + component.getName() + " shuts down due to brownout!");
		}
	}
	
	public int getSpeed()
	{
		return this.getEngineBonus();
	}
	
	/**
	 * Returns bonus (positive) or penalty (negative) to Target Lock due to ship size.
	 * @return bonus or penalty due to ship size
	 */
	public int getSizeMod()
	{
		return this.getFrame().getSize().getTargetLockBonus();
	}

	/**
	 * Returns relative turn radius of ship. Lower values are more maneuverable.
	 * @return Turn value of ship
	 */
	public int getTurnRadius()
	{
		return this.getFrame().getManeuverability().getTurnRadius() + this.getArmorBonus() - this.getThrustersBonus();
	}

	/**
	 * Add a bonus (or penalty, if negative) to the current TL bonus due to crew actions or other
	 * events. Bonuses and penalties are additive, and are reset on first use.
	 * @param bonus Temporary bonus or penalty to TL for this ship
	 */
	public void addTargetLockBonus(int bonus)
	{
		this.targetLockBonus += bonus;
	}
	
	/**
	 * Use the accumulated bonuses and penalties to Target Lock due to crew actions or other events.
	 * This bonus is reset at the beginning of every combat round.
	 * @return Total accumulated temporary bonuses and penalties to Target Lock
	 */
	public int getTargetLockBonus()
	{
		return this.targetLockBonus;
	}
	
	/**
	 * Resets the temporary Target Lock cumulative bonus (or penalty) from crew actions or other
	 * events.
	 */
	public void resetTargetLockBonus()
	{
		this.targetLockBonus = 0;
	}
	
	private static int exponent(int radix, int exponent)
	{
		int result = 1;
		for (int ii = 0; ii < exponent; ii++)
		{
			result *= radix;
		}
		return result;
	}

	public void addComponent(ShipComponent component)
		throws InsufficientPowerException, DuplicateComponentException, ShipBuildException
	{
		// Can only add components designed for this ship
		assert component.getShip().equals(this);
		
		int buildPointsUsed = this.getFrame().getBuildPointCost();
		for (ShipComponent existingComponent : this.componentsMap.values())
		{
			int componentSizeBuildPoints = exponent(2, existingComponent.getSize().getSize());
			buildPointsUsed += componentSizeBuildPoints;
			if (existingComponent.isArmored()) buildPointsUsed += componentSizeBuildPoints;
			if (existingComponent.isHidden()) buildPointsUsed += componentSizeBuildPoints;
			if (existingComponent.isResistant()) buildPointsUsed += componentSizeBuildPoints;
			if (existingComponent.isShielded()) buildPointsUsed += componentSizeBuildPoints;
		}
		if (buildPointsUsed > this.getTier().getBuildPoints())
		{
			throw new ShipBuildException("Build Points exceeded. Used " + buildPointsUsed + ", max " + this.getTier().getBuildPoints());
		}
		
		if (this.getNetPowerAvailable() < component.getPowerRequired())
		{
			throw new InsufficientPowerException("Not enough PCUs available to power this component");
		}
		
		// Check if it's already added and if not, give it a useful name
		int number = 1;
		for (ShipComponent existingComponent : this.componentsMap.values())
		{
			if (existingComponent == component)
			{
				throw new DuplicateComponentException("Component " + component.getName() + " is already installed");
			}
			if (existingComponent.getClass().equals(component.getClass())) number++;
		}
		component.setName(component.getName() + " " + number);

		this.componentsMap.put(component.getId(), component);

		// Keep track of some oft-used component types
//		if (component instanceof Weapon) this.getWeapons().add((Weapon)component);
		if (component.getPowerRequired() > 0)
		{
			this.getPoweredComponents().add(component);
		}
	}
	
	public void removeComponent(ShipComponent component)
	{
		this.componentsMap.remove(component.getName());
		this.getWeapons().remove(component);
		this.getPoweredComponents().remove(component);
	}
	
	/**
	 * A ship's ability to target other ships with weapons or offensive components
	 * is limited by its active and undamaged Passive Sensors and Sensor Arrays.
	 * Whichever of those has the farthest range is the maximum range to target.
	 * A ship can always target at Point Blank range, regardless of Sensor component
	 * status.
	 * @return maximum range at which another ship can be targeted by weapons or components
	 */
	public Distance getMaxTargetDistance()
	{
		Distance maxSensorDistance = Distance.POINT_BLANK;
		Distance passiveRange = this.getPassiveSensorRange();
		if (passiveRange.compareTo(maxSensorDistance) > 0)
		{
			maxSensorDistance = passiveRange;
		}
		Distance activeRange = this.getSensorArrayRange();
		if (activeRange.compareTo(maxSensorDistance) > 0)
		{
			maxSensorDistance = activeRange;
		}
		return maxSensorDistance;
	}
	
	/**
	 * Returns a list of all missiles still active, fired by one of our weapons.
	 * A missile is active if it has not exited the battlefield or exploded on a target.
	 * @return all missiles fired by my weapons which are still active
	 */
	public List<Missile> getActiveMissiles()
	{
		List<Missile> missiles = new ArrayList<Missile>();
		for (Weapon weapon : this.getWeapons())
		{
			if (weapon instanceof MissileLauncher)
			{
				MissileLauncher ml = (MissileLauncher) weapon;
				missiles.addAll(ml.getFiredMissiles());
			}
		}
		return missiles;
	}
	
	public List<Missile> getMissilesTargetingMe()
	{
		List<Missile> missiles = new ArrayList<Missile>();
		for (Ship ship : this.getController().getShips())
		{
			for (Missile missile : ship.getActiveMissiles())
			{
				if (missile.getTarget() == this)
				{
					if (this.canDetect(missile)) missiles.add(missile);
				}
			}
		}
		return missiles;
	}

	public List<Ship> getTargetableShips()
	{
		List<Ship> ships = new ArrayList<Ship>();
		Distance maxTargetDistance = this.getMaximumTargetingRange();
		for (Ship ship : this.getController().getShips())
		{
			Distance distanceToShip = this.getDistanceFrom(ship);
			if (maxTargetDistance.compareTo(distanceToShip) >= 0)
			{
				if (this.getDetectedShips().containsKey(ship))
				{
					ships.add(ship);
				}
			}
		}
		return ships;
	}
	
	public DetectedShip getDetectedShip(Ship otherShip)
	{
		DetectedShip detectedShip = null;
		if (this.getDetectedShips().containsKey(otherShip))
		{
			detectedShip = this.getDetectedShips().get(otherShip);
		}
		return detectedShip;
	}

	/**
	 * Returns the list of matching ship components of the specified type.
	 * @param <T> Subclass of ShipComponent to return
	 * @param cls Class to compare
	 * @return list of the specified component type
	 */
	@SuppressWarnings("unchecked")
	protected <T extends ShipComponent> List<T> getComponents(Class<T> cls)
	{
		List<T> components = new ArrayList<T>();
		for (ShipComponent component : this.getComponents())
		{
			if (component.getClass().equals(cls)) components.add((T) component);
		}
		return components;
	}
	
	/**
	 * Returns the total bonus from all active components of the specified type.
	 * Loss of power, battle damage, and being manually disabled will cause a component to not provide 
	 * a bonus.
	 * @param <T> Subclass of ShipComponent to count
	 * @param cls Class to compare
	 * @return total bonus from the specified component type
	 */
	protected <T extends ShipComponent> int getComponentBonus(Class<T> cls)
	{
		int bonus = 0;
		for (T component : this.getComponents(cls))
		{
			if (component.isActive()) bonus++; // NOTE: should this be multiplied by component Size?
		}
		return bonus;
	}
	
	public ShipComponent getComponent(String name)
	{
		return this.componentsMap.get(name);
	}
	
	/**
	 * Returns the first unclaimed ShipComponent of the type specified.
	 * @return null if no matching component is available
	 */
	public ShipComponent getAvailableComponent(String name)
	{
		ShipComponent component = null;
		for (ShipComponent sc : this.getComponents())
		{
			if (sc.getName().contentEquals(name))
			{
				if (sc.getCurrentPlayer() == null)
				{
					component = sc;
					break;
				}
			}
		}
		return component;
	}

	public int rollInitiative()
	{
		log.trace("rollInitiative()");
		Player pilot = this.getPilot();
		if (pilot == null)
		{
			log.info("ALERT: " + this.getName() + " pilot not found. GEE-M autopilot engaged.");
		}
		int pilotBonus = (pilot == null ? this.getAIBonus() : pilot.getPilotBonus());
		int shipBonus = this.getSpeed() - this.getTurnRadius();
		int d20 = Dice.d(20);
		this.initiative = d20 + pilotBonus + shipBonus;
		log.info("Initiative roll " + this.getName() + ": (" + d20 + ") + " + (pilotBonus + shipBonus) + " = " + this.getInitiative());
		return this.initiative;
	}

	/**
	 * Returns the maximum range at which the installed and active Passive Sensors can operate. Multiple Passive Sensor
	 * components increase the range, up to a maximum of Beyond Extreme.
	 * @return maximum range at which Passive Sensors can operate
	 */
	public Distance getPassiveSensorRange()
	{
		Distance distance = Distance.POINT_BLANK;
		for (int ii = 0; ii < this.getPassiveSensorsBonus(); ii++)
		{
			distance = distance.increment();
		}
		return Distance.min(distance, Distance.EXTREME);
	}

	/**
	 * Returns the maximum range at which the installed and active Sensor Arrays can operate. Multiple Sensor Arrays
	 * components increase the range, up to a maximum of Beyond Extreme.
	 * @return maximum range at which Sensor Arrays can operate
	 */
	public Distance getSensorArrayRange()
	{
		Distance distance = Distance.POINT_BLANK;
		for (int ii = 0; ii < this.getSensorArrayBonus(); ii++)
		{
			distance = distance.increment();
		}
		return Distance.min(distance, Distance.EXTREME);
	}
	
	/**
	 * The maximum range at which sensors can operate is the larger of the Passive Sensor distance and the Sensor
	 * Array distance.
	 * @return maximum range at which any Sensor components can operate
	 */
	public Distance getMaximumSensorRange()
	{
		Distance distance = Distance.max(this.getPassiveSensorRange(), this.getSensorArrayRange());
		return distance;
	}
	
	/**
	 * Weapons and ShipComponent actions can only target ships at the maximum range of the sensor components (both
	 * Passive Sensors and Sensor Arrays). Ships may be detected at Beyond Extreme range, but cannot be targeted farther
	 * than Extreme range.
	 * @return maximum range at which weapons and ship components can target other ships
	 */
	public Distance getMaximumTargetingRange()
	{
		Distance distance = Distance.min(this.getMaximumSensorRange(), Distance.EXTREME);
		return distance;
	}
	
	public Distance getCommGridRange()
	{
		Distance distance = Distance.POINT_BLANK;
		for (int ii = 0; ii < this.getCommGridBonus(); ii++)
		{
			distance = distance.increment();
		}
		return Distance.min(distance, Distance.EXTREME);
	}
	
	public Distance getCommunicationsRange()
	{
		// Communications are designed to be ultra long range
		return Distance.BEYOND_EXTREME;
	}

	public Distance getTractorBeamRange()
	{
		Distance distance = Distance.POINT_BLANK;
		for (int ii = 0; ii < this.getTractorBeamBonus(); ii++)
		{
			distance = distance.increment();
		}
		return Distance.min(distance, Distance.EXTREME);
	}

	/**
	 * Run an automated Passive Sensor scan of the immediate area. Sometimes you find things you didn't see before.
	 */
	public void runPassiveSensorScan()
	{
		for (Ship ship : this.getController().getShips())
		{
			if (ship.equals(this)) continue;
			if (ship.getFaction().equals(this.getFaction())) continue;
			if (this.getDetectedShips().containsKey(ship)) continue;
			int totalStealth = ship.getTotalStealthBonus();
			if (ship.getStealthFieldBonus() < 1 || totalStealth <= 0)
			{
				// Other ship is not cloaked or impossible to cloak enough
				log.debug(this.getName() + " detects " + ship.getName());
				this.getDetectedShips().put(ship, new DetectedShip(ship));
				continue;
			}

			Distance distanceToShip = this.getDistanceFrom(ship);
			Distance maxSensorRange = this.getMaximumSensorRange();
			if (maxSensorRange.compareTo(distanceToShip) >= 0) // Must be within range
			{
				// Passive Sensors 10+AI+Passive vs. StealthField+Countermeasures+SizeMod-NetPowerUsed
				log.debug(this.getName() + " running passive scan");
				int passiveSensorsTotal = 10 + this.getAIBonus() + this.getPassiveSensorsBonus();
				int dc = 10 + ship.getTotalStealthBonus();
				if (passiveSensorsTotal >= dc)
				{
					log.info(this.getAICrewmember() + " has detected a previously unknown ship: " + ship.getName() + "!");
					this.getDetectedShips().put(ship, new DetectedShip(ship));
				}
			}
		}
	}
	
	/**
	 * You must have at least one active Stealth Field generator to even have a chance at being cloaked.
	 * Stealth Fields, Security Modules, and small size improve your stealth bonus.
	 * Power usage (excluding that needed for the Stealth Fields) and large size decrease your stealth bonus.
	 * @return 0 if no Stealth Fields are active; else Stealth+Countermeasures+SizeMod-(Net Non-Stealth Power)
	 */
	public int getTotalStealthBonus()
	{
		int stealthBonus = this.getStealthFieldBonus();
		if (stealthBonus <= 0)
		{
			// Must have at least one functioning Stealth Field to be cloaked
			return 0;
		}
		int powerUsedExcludingStealth = this.getTotalPowerUsed() - stealthBonus;
		return stealthBonus + this.getCountermeasuresBonus() + this.getSizeMod() - powerUsedExcludingStealth;
	}
	/**
	 * Is the specified ship detected by this ship?
	 * @param ship Possibly hidden ship
	 * @return true if the ship has been detected by Passive Sensor scan or active Sensor Array scan
	 */
	public boolean canDetect(Ship ship)
	{
		return this.getDetectedShips().containsKey(ship);
	}
	
	/**
	 * Is the specified Missile detected by this ship?
	 * @param missile
	 * @return true, because there are no Stealth missiles
	 */
	public boolean canDetect(Missile missile)
	{
		return true;
	}

	public void addCrewMember(Player player)
	{
		assert (player != null);
		this.crewMembers.add(player);
	}

	public void removeCrewMember(Player player)
	{
		assert (player != null);
		this.crewMembers.remove(player);
	}

	/**
	 * Example Output: "GenericName: [ ][3][X][ ]{P}" means 2 active, 1 damaged, 1 broken, and 1 disabled with power loss
	 * @return Simple (non-markup) status of this Ship
	 */
	public String getStatus()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Ship Status:\n");
		sb.append(this.toString()).append("\n");
		sb.append("Captain: " + (this.getCaptain() == null ? "(none)" : this.getCaptain().getName()) + "\n");
		sb.append("Pilot: " + (this.getPilot() == null ? "(none)" : this.getPilot().getName()) + "\n");
		sb.append("Components:\n");
		Map<String, String> componentStatuses = new TreeMap<String, String>();
		for (ShipComponent component : this.getComponents())
		{
			String genericName = component.getGenericName();
			String status = componentStatuses.get(genericName);
			componentStatuses.put(genericName, (status == null ? "" : status) + component.getSimpleStatus());
		}
		for (String key : componentStatuses.keySet())
		{
			sb.append(key + ": " + componentStatuses.get(key) + "\n");
		}
		return sb.toString();
	}
	
	@Override
	public int compareTo(Ship that)
	{
		if (that == this) return 0;
		
		log.debug("Comparing " + this.getName() + " to " + that.getName());
		int compare = (this.getInitiative() - that.getInitiative());
		if (compare == 0)
		{
			log.debug("Initiatives equal, comparing pilot skill");
			Player ourPilot = this.getPilot();
			Player theirPilot = that.getPilot();
			// Autopilots automatically lose initiative contests against live pilots
			if (! ourPilot.equals(this.getAICrewmember()) && theirPilot.equals(that.getAICrewmember()))
			{
				compare = 1;
				log.info(that.getName() + " autopilot is outmaneuvered by " + this.getName() + "!");
			}
			else if (ourPilot.equals(this.getAICrewmember()) && ! theirPilot.equals(that.getAICrewmember()))
			{
				compare = -1;
				log.info(this.getName() + " autopilot is outmaneuvered by " + that.getName() + "!");
			}
			else if (ourPilot.equals(this.getAICrewmember()) && theirPilot.equals(that.getAICrewmember()))
			{
				// It's a tossup between autopilots. Flip a coin.
				compare = (Math.random() < 0.5 ? -1 : 1);
			}
			else
			{
				compare = ourPilot.getPilotBonus() - theirPilot.getPilotBonus();
			}
		}
		while (compare == 0)
		{
			log.debug("Initiatives still equal, it's a Pilot Roll-Off!");
			compare = this.getPilot().rollPilotSkill() - that.getPilot().rollPilotSkill();
		}
		return compare;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder()
			.append(getName()).append(" (")
			.append("HP:" + this.getCurrHP()).append(" ")
			.append("Shields:" + this.getCurrShields()).append(" ")
			.append("Armor:" + this.getCurrArmor()).append(")");
		return sb.toString();
	}

	// Constructors
	
	public Ship(Tier tier, Frame frame)
	{
		this.tier = tier;
		this.frame = frame;
		this.currHP = frame.getDefaultHP() + (tier.getBonusHullPointMultiplier() * frame.getExtraHP());
	}
	
	// Getters & Setters
	
	public String getId()
	{
		String id = null;
		try
		{
			id = URLEncoder.encode(this.getName(), "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			id = this.getName().replace(" ", "%2a");
		}
		return id;
	}
	
	/**
	 * A ship is active while its Hull Points are greater than zero.
	 * @return true if currHP > 0; false if not
	 */
	@Override
	public boolean isActive()
	{
		return (this.getCurrHP() > 0);
	}
	
	public int getCurrArmor()
	{
		int currArmor = 0;
		for (ShipComponent armor : this.getArmorComponents())
		{
			if (! armor.isBroken()) currArmor++;
		}
		return currArmor;
	}
	
	public int getMaxArmor()
	{
		int maxArmor = this.getArmorComponents().size();
		return maxArmor;
	}

	public int getCurrHP()
	{
		return this.currHP;
	}
	
	public int getMaxHP()
	{
		return this.getFrame().getDefaultHP() + (this.getTier().getBonusHullPointMultiplier() * this.getFrame().getExtraHP());
	}
	
	@Override
	public String getName()
	{
		return (this.name != null ? this.name : "Default Ship");
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Tier getTier()
	{
		return this.tier;
	}

	public Frame getFrame()
	{
		return this.frame;
	}
	
	/**
	 * Returns the crewmembers who do not serve in a special (Pilot, Captain) role.
	 * @return crewmembers who are not captain or pilot
	 */
	public Set<Player> getCrewMembers()
	{
		return this.crewMembers;
	}
	
	/**
	 * Returns the all crewmembers, including the pilot and captain.
	 * @return all crewmembers, including captain and pilot
	 */
	public Set<Player> getAllCrewMembers()
	{
		Set<Player> allCrew = new HashSet<Player>(this.crewMembers);
		if (this.getCaptain() != null) allCrew.add(this.getCaptain());
		if (this.getPilot() != null) allCrew.add(this.getPilot());
		return allCrew;
	}

	public Collection<ShipComponent> getComponents()
	{
		return this.componentsMap.values();
	}

	public List<AIModule> getAIModuleComponents()
	{
		return this.getComponents(AIModule.class);
	}

	public List<Armor> getArmorComponents()
	{
		return this.getComponents(Armor.class);
	}
	
	public List<CommGrid> getCommGridComponents()
	{
		return this.getComponents(CommGrid.class);
	}
	
	public List<Communications> getCommunicationsComponents()
	{
		return this.getComponents(Communications.class);
	}
	
	public List<Engine> getEngineComponents()
	{
		return this.getComponents(Engine.class);
	}
	
	public List<Flak> getFlakComponents()
	{
		return this.getComponents(Flak.class);
	}
	
	public List<PassiveSensors> getPassiveSensorsComponents()
	{
		return this.getComponents(PassiveSensors.class);
	}
	
	public List<PowerCore> getPowerCoreComponents()
	{
		return this.getComponents(PowerCore.class);
	}
	
	public List<SecurityModule> getSecurityModuleComponents()
	{
		return this.getComponents(SecurityModule.class);
	}
	
	public List<SensorArray> getSensorArrayComponents()
	{
		return this.getComponents(SensorArray.class);
	}
	
	public List<ShieldGenerator> getShieldGeneratorComponents()
	{
		return this.getComponents(ShieldGenerator.class);
	}

	public List<StealthField> getStealthFieldComponents()
	{
		return this.getComponents(StealthField.class);
	}
	
	public List<Thrusters> getThrustersComponents()
	{
		return this.getComponents(Thrusters.class);
	}
	
	public List<TractorBeam> getTractorBeamComponents()
	{
		return this.getComponents(TractorBeam.class);
	}
	
	public List<Weapon> getWeapons()
	{
		List<Weapon> weapons = new ArrayList<Weapon>();
		for (ShipComponent component : this.getComponents())
		{
			if (component instanceof Weapon)
			{
				weapons.add((Weapon) component);
			}
		}
		return weapons;
	}

	public int getBuildPoints()
	{
		return this.getTier().getBuildPoints();
	}

	public int getInitiative()
	{
		return this.initiative;
	}
	
	public CombatController getController()
	{
		return this.controller;
	}

	public void setController(CombatController controller)
	{
		this.controller = controller;
	}

	public Faction getFaction()
	{
		return this.faction;
	}

	public void setFaction(Faction faction)
	{
		this.faction = faction;
	}

	/**
	 * If your pilot is missing or incapacitated, your friendly local GEE-M module will step in.
	 * @return pilot if assigned, or an autopilot if not
	 */
	public Player getPilot()
	{
		return (this.pilot == null ? new Autopilot(this) : this.pilot);
	}

	public void setPilot(Player pilot)
	{
		this.pilot = pilot;
	}

	/**
	 * If your captain is missing or incapacitated, your friendly local GEE-M module will step in.
	 * WARNING: GEE-M modules are notoriously bad at Charisma skills!
	 * @return captain if assigned, or an autopilot if not
	 */
	public Player getCaptain()
	{
		return (this.captain == null ? new Autopilot(this) : this.captain);
	}
	
	public void setCaptain(Player captain)
	{
		this.captain = captain;
	}

	/**
	 * Default virtual crewmember when the GEE-M activates a console.
	 * @return Default virtual crewmember
	 */
	public Player getAICrewmember()
	{
		return new Autopilot(this);
	}
	
	public List<ShipComponent> getPoweredComponents()
	{
		return this.poweredComponents;
	}

	public int getArmorBonus()
	{
		return this.getComponentBonus(Armor.class);
	}

	public int getAIBonus()
	{
		return this.getComponentBonus(AIModule.class);
	}
	
	public int getCountermeasuresBonus()
	{
		return this.getComponentBonus(SecurityModule.class);
	}
	
	public int getCommGridBonus()
	{
		return this.getComponentBonus(CommGrid.class);
	}
	
	public int getCommunicationsBonus()
	{
		return this.getComponentBonus(Communications.class);
	}
	
	public int getEngineBonus()
	{
		return this.getComponentBonus(Engine.class);
	}
	
	public int getFlakBonus()
	{
		return this.getComponentBonus(Flak.class);
	}
	
	public int getPassiveSensorsBonus()
	{
		return this.getComponentBonus(PassiveSensors.class);
	}
	
	public int getPowerCoreBonus()
	{
		return this.getComponentBonus(PowerCore.class);
	}
	
	public int getSecurityModuleBonus()
	{
		return this.getComponentBonus(SecurityModule.class);
	}
	
	public int getSensorArrayBonus()
	{
		return this.getComponentBonus(SensorArray.class);
	}

	public int getCurrShields()
	{
		int currShields = 0;
		for (ShieldGenerator shield : this.getShieldGeneratorComponents())
		{
			if (shield.isActive()) currShields += shield.getCurrHP();
		}
		return currShields;
	}

	public int getMaxShields()
	{
		int maxShields = 0;
		for (ShieldGenerator shield : this.getShieldGeneratorComponents())
		{
			maxShields += shield.getMaxHP();
		}
		return maxShields;
	}
	
	public int getStealthFieldBonus()
	{
		return this.getComponentBonus(StealthField.class);
	}
	
	public int getThrustersBonus()
	{
		return this.getComponentBonus(Thrusters.class);
	}
	
	public int getTractorBeamBonus()
	{
		return this.getComponentBonus(TractorBeam.class);
	}
	
	public Map<Ship, DetectedShip> getDetectedShips()
	{
		return this.detectedShipsMap;
	}

	public boolean isFacing(Ship otherShip)
	{
		Boolean isFacing = this.facingMap.get(otherShip);
		if (isFacing == null)
		{
			// By default, ships are facingMap each other upon first notice
			isFacing = Boolean.TRUE;
			this.facingMap.put(otherShip, isFacing);
		}
		return isFacing.booleanValue();
	}
	
	public void setFacing(Ship otherShip, boolean isFacing)
	{
		this.facingMap.put(otherShip, Boolean.valueOf(isFacing));
	}
	
	public Distance getPreferredDistanceFrom(Ship otherShip)
	{
		Distance distance = this.preferredDistanceMap.get(otherShip);
		if (distance == null)
		{
			// Try to estimate intelligently what the default distance wanted should be
			distance = this.getMaximumTargetingRange();
			for (Weapon weapon : this.getWeapons())
			{
				// Missiles are better at close range; less chance of getting hacked or evaded
				if (weapon instanceof MissileLauncher) distance = distance.decrement();
				// Capital weapons cannot fire at Point Blank and are best at Extreme range
				if (weapon.getSize().equals(ShipComponent.Size.CAPITAL)) distance = distance.increment();
			}
			
			// Small ships want to get up close, large ships want to keep a distance
			for (int ii = 0; ii < this.getSizeMod() - otherShip.getSizeMod(); ii++) distance = distance.decrement();

			// It would be boring to have everyone at Beyond Extreme and unable to do anything to each other
			distance = Distance.min(distance, Distance.EXTREME);

			this.preferredDistanceMap.put(otherShip, distance);
		}
		return distance;
	}

	public void setPreferredDistanceFrom(Ship otherShip, Distance distance)
	{
		this.preferredDistanceMap.put(otherShip, distance);
	}

	public Distance getPreferredDistanceFrom(DetectedShip otherShip)
	{
		return this.getPreferredDistanceFrom(otherShip.getActualShip());
	}

	public void setPreferredDistanceFrom(DetectedShip otherShip, Distance distance)
	{
		this.preferredDistanceMap.put(otherShip.getActualShip(), distance);
	}
	
	/**
	 * @return ShipComponent, Age Of Last Scan
	 */
	public Map<ShipComponent, Integer> getDetectedComponents()
	{
		return this.detectedComponentsMap;
	}

	@SuppressWarnings("unused")
	private void setDetectedComponents(Map<ShipComponent, Integer> detectedComponents)
	{
		this.detectedComponentsMap = detectedComponents;
	}

	/**
	 * All missiles are automatically detected due to the Target Lock required.
	 */
	public List<Missile> getDetectedMissiles()
	{
		return this.getController().getActiveMissiles();
	}
	
	public void setTargetLockBonus(int targetLockBonus)
	{
		this.targetLockBonus = targetLockBonus;
	}

	// Subclasses
	
	public static enum Faction
	{
		PLAYER, NPC
	}

	public static enum Tier
	{
		ONE_FOURTH ( 25, 0, 0, "1/4" ),
		ONE_THIRD ( 30, 0, 0, "1/3" ),
		ONE_HALF ( 40, 0, 1, "1/2" ),
		ONE (55, 0, 1, "1"),
		TWO ( 75, 0, 2, "2" ),
		THREE ( 95, 0, 3, "3" ),
		FOUR ( 115, 1, 4, "4" ),
		FIVE ( 135, 1, 5, "5" ),
		SIX ( 155, 1, 6, "6" ),
		SEVEN ( 180, 1, 7, "7" ),
		EIGHT ( 205, 2, 8, "8" ),
		NINE ( 230, 2, 9, "9" ),
		TEN ( 270, 2, 10, "10" ),
		ELEVEN ( 310, 2, 11, "11" ),
		TWELVE ( 350, 3, 12, "12" ),
		THIRTEEN ( 400, 3, 13, "13" ),
		FOURTEEN ( 450, 3, 14, "14" ),
		FIFTEEN ( 500, 3, 15, "15" ),
		SIXTEEN ( 600, 4, 16, "16" ),
		SEVENTEEN ( 700, 4, 17, "17" ),
		EIGHTEEN ( 800, 4, 18, "18" ),
		NINETEEN ( 900, 4, 19, "19" ),
		TWENTY ( 1000, 5, 20, "20" );
	
		private final int buildPoints;
		private final int bonusHullPointMultiplier;
		private final int tierBonus;
		private final String description;

		Tier(int buildPoints, int bonusHullPointMultiplier, int tierBonus, String description)
		{
			this.buildPoints = buildPoints;
			this.bonusHullPointMultiplier = bonusHullPointMultiplier;
			this.tierBonus = tierBonus;
			this.description = description;
		}
		
		public int getBuildPoints()
		{
			return this.buildPoints;
		}
		
		public int getBonusHullPointMultiplier()
		{
			return this.bonusHullPointMultiplier;
		}

		public int getTierBonus()
		{
			return this.tierBonus;
		}
		
		public String getDescription()
		{
			return this.description;
		}
	}
	
	public static enum Frame
	{
		RACER(Size.TINY, Maneuverability.PERFECT, 20, 5, 0, 1, 1, 4, "Racer"),
		INTERCEPTOR(Size.TINY, Maneuverability.PERFECT, 30, 5, 0, 1, 1, 6, "Interceptor"),
		FIGHTER(Size.TINY, Maneuverability.GOOD, 35, 5, 0, 1, 2, 8, "Fighter"),
		SHUTTLE(Size.SMALL, Maneuverability.PERFECT, 35, 5, 3, 1, 4, 6, "Shuttle"),
		LIGHT_FREIGHTER(Size.SMALL, Maneuverability.GOOD, 40, 10, 3, 1, 6, 10, "Light Freighter"),
		EXPLORER(Size.MEDIUM, Maneuverability.GOOD, 55, 10, 4, 1, 6, 12, "Explorer"),
		TRANSPORT(Size.MEDIUM, Maneuverability.AVERAGE, 70, 15, 5, 1, 6, 15, "Transport"),
		DESTROYER(Size.LARGE, Maneuverability.AVERAGE, 150, 20, 4, 6, 20, 30, "Destroyer"),
		HEAVY_FREIGHTER(Size.LARGE, Maneuverability.AVERAGE, 120, 20, 8, 6, 20, 40, "Heavy Freighter"),
		BULK_FREIGHTER(Size.HUGE, Maneuverability.POOR, 160, 20, 10, 20, 50, 55, "Bulk Freighter"),
		CRUISER(Size.HUGE, Maneuverability.AVERAGE, 180, 25, 6, 20, 100, 60, "Cruiser"),
		CARRIER(Size.GARGANTUAN, Maneuverability.POOR, 240, 30, 10, 75, 200, 120, "Carrier"),
		BATTLESHIP(Size.GARGANTUAN, Maneuverability.AVERAGE, 280, 40, 8, 100, 300, 150, "Battleship"),
		DREADNOUGHT(Size.COLOSSAL, Maneuverability.CLUMSY, 400, 50, 20, 125, 500, 200, "Dreadnought");

		private Size size;
		private Maneuverability maneuverability;
		private int defaultHP;
		private int extraHP;
		// Damage Threshold (DT) is not used in this system
		// Critical Threshold (CT) is not used in this system
		private int expansionBays;
		private int minCrew;
		private int maxCrew;
		private int buildPointCost;
		private String description;
		
		Frame(Size size, Maneuverability maneuverability, int defaultHP, int extraHP, int expansionBays, int minCrew, int maxCrew, int buildPointCost, String description)
		{
			this.size = size;
			this.maneuverability = maneuverability;
			this.defaultHP = defaultHP;
			this.extraHP = extraHP;
			this.expansionBays = expansionBays;
			this.minCrew = minCrew;
			this.maxCrew = maxCrew;
			this.buildPointCost = buildPointCost;
			this.description = description;
		}

		public Size getSize()
		{
			return this.size;
		}

		public Maneuverability getManeuverability()
		{
			return this.maneuverability;
		}

		public int getDefaultHP()
		{
			return this.defaultHP;
		}

		public int getExtraHP()
		{
			return this.extraHP;
		}

		public int getExpansionBays()
		{
			return this.expansionBays;
		}

		public int getMinCrew()
		{
			return this.minCrew;
		}

		public int getMaxCrew()
		{
			return this.maxCrew;
		}

		public int getBuildPointCost()
		{
			return this.buildPointCost;
		}

		public String getDescription()
		{
			return this.description;
		}
		
		public static enum Size
		{
			TINY (2, "Tiny"),
			SMALL (1, "Small"),
			MEDIUM (0, "Medium"),
			LARGE (-1, "Large"),
			HUGE (-2, "Huge"),
			GARGANTUAN (-4, "Gargantuan"),
			COLOSSAL (-8, "Colossal");
			
			private int targetLockBonus;
			private String description;
			
			Size(int targetLockBonus, String description)
			{
				this.targetLockBonus = targetLockBonus;
				this.description = description;
			}
			
			public int getTargetLockBonus()
			{
				return this.targetLockBonus;
			}
			
			public String getDescription()
			{
				return this.description;
			}
		}
		
		public static enum Maneuverability
		{
			PERFECT (2, 0, "Perfect"),
			GOOD (1, 1, "Good"),
			AVERAGE (0, 2, "Average"),
			POOR (-1, 3, "Poor"),
			CLUMSY (-2, 4, "Clumsy");
			
			private int pilotingBonus;
			private int turnRadius;
			private String description;
			
			Maneuverability(int pilotingBonus, int turnRadius, String description)
			{
				this.pilotingBonus = pilotingBonus;
				this.turnRadius = turnRadius;
				this.description = description;
			}
			
			public int getPilotingBonus()
			{
				return this.pilotingBonus;
			}
			
			public int getTurnRadius()
			{
				return this.turnRadius;
			}
			
			public String getDescription()
			{
				return this.description;
			}
		}
	}
	
	public class Autopilot extends Player
	{
		public Autopilot(Ship ship) throws InvalidParameterException
		{
			super(ship.getName() + " GEE-M", ship);
			this.setComputersBonus(ship.getAIBonus());
			this.setEngineeringBonus(ship.getAIBonus());
			this.setDiplomacyBonus(0);
			this.setGunneryBonus(ship.getAIBonus());
			this.setIntimidateBonus(0);
			this.setPilotBonus(ship.getAIBonus());
			this.setCurrentConsole(new ShipComponent(ship)
			{
				@Override
				public String getDescription()
				{
					return "GEE-M Console";
				}

				@Override
				public String getGenericName()
				{
					return "GEE-M Console";
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
					return 0; // GEE-M does not have access to component consoles and must work behind the scenes
				}
			});
		}
	}
}
