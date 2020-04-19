package com.dougcrews.game.starfinder.model.ship;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dougcrews.game.starfinder.model.Distance;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ShipToShipRelation;
import com.dougcrews.game.starfinder.model.ShipToShipRelation.DistanceIntent;
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
import com.dougcrews.game.starfinder.model.ship.component.StealthField;
import com.dougcrews.game.starfinder.model.ship.component.Thrusters;
import com.dougcrews.game.starfinder.model.ship.component.TractorBeam;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

/**
 * This is a Sensor-created image of an actual ship. Very little is known without running
 * Sensor scans.
 */
public class DetectedShip
{
	private final static int UNKNOWN = -1;
	
	protected Ship actualShip;
	protected int currArmor = UNKNOWN;
	protected int maxArmor = UNKNOWN;
	protected int currHP = UNKNOWN;
	protected int maxHP = UNKNOWN;
	protected int currShields = UNKNOWN;
	protected int maxShields = UNKNOWN;
	protected Distance passiveSensorRange = null;
	protected Distance sensorArrayRange = null;
	protected Player pilot = null;
	protected Player captain = null;
	protected Set<Player> crewMembers = new HashSet<Player>();
	protected Set<ShipComponent> components = new HashSet<ShipComponent>();

	public int getColumnForDisplay(Ship ship)
	{
		ShipToShipRelation relation = null;
		for (ShipToShipRelation s2sr : this.actualShip.getController().getShipRelations())
		{
			if (s2sr.contains(ship) && s2sr.contains(this.actualShip))
			{
				relation = s2sr;
				break;
			}
		}
		if (relation == null) throw new RuntimeException("Did not find ShipToShipRelation for " + this + " and " + ship);
		
		if (Distance.BEYOND_EXTREME.equals(relation.getDistance()))
		{
			return (DistanceIntent.MOVE_FARTHER.equals(relation.getShip2DistanceIntent()) ? 0 : 10);
		}
		else if (Distance.EXTREME.equals(relation.getDistance()))
		{
			return (DistanceIntent.MOVE_FARTHER.equals(relation.getShip2DistanceIntent()) ? 1 : 9);
		}
		else if (Distance.LONG.equals(relation.getDistance()))
		{
			return (DistanceIntent.MOVE_FARTHER.equals(relation.getShip2DistanceIntent()) ? 2 : 8);
		}
		else if (Distance.MEDIUM.equals(relation.getDistance()))
		{
			return (DistanceIntent.MOVE_FARTHER.equals(relation.getShip2DistanceIntent()) ? 3 : 7);
		}
		else if (Distance.SHORT.equals(relation.getDistance()))
		{
			return (DistanceIntent.MOVE_FARTHER.equals(relation.getShip2DistanceIntent()) ? 4 : 6);
		}
		else
		{
			return 5;
		}
	}
	
	public String getDisplayNameWithFacing(Ship ship)
	{
		ShipToShipRelation relation = null;
		for (ShipToShipRelation s2sr : this.actualShip.getController().getShipRelations())
		{
			if (s2sr.contains(ship) && s2sr.contains(this.actualShip))
			{
				relation = s2sr;
				break;
			}
		}
		assert relation != null;
		String displayString = this.getName();
//		if (relation != null && this.actualShip.equals(relation.getShip1()))
//		{
//			if (relation.isShip1Facing())
//			{
//				displayString = (this.getColumnForDisplay(ship) <= 5 ? ">>" + this.getName() + ">>" : "<<" + this.getName() + "<<");
//			}
//			else
//			{
//				displayString = (this.getColumnForDisplay(ship) >= 5 ? ">>" + this.getName() + ">>" : "<<" + this.getName() + "<<");
//			}
//		}
//		else
//		{
//			if (relation != null && relation.isShip2Facing())
//			{
//				displayString = (this.getColumnForDisplay(ship) <= 5 ? ">>" + this.getName() + ">>" : "<<" + this.getName() + "<<");
//			}
//			else
//			{
//				displayString = (this.getColumnForDisplay(ship) >= 5 ? ">>" + this.getName() + ">>" : "<<" + this.getName() + "<<");
//			}
//		}
		return displayString;
	}
	
	@Override
	public String toString()
	{
		return this.getName() + " (Detected)";
	}
	
	// Constructors
	
	public DetectedShip(Ship ship)
	{
		this.actualShip = ship;
	}

	// Getters & Setters
	
	public String getName()
	{
		return this.actualShip.getName();
	}
	
	public Ship getActualShip()
	{
		return this.actualShip;
	}
	
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

	public int getTargetLock()
	{
		return this.actualShip.getTargetLock();
	}

	public Tier getTier()
	{
		return this.actualShip.getTier();
	}
	
	public Frame getFrame()
	{
		return this.actualShip.getFrame();
	}
	
	public int getSpeed()
	{
		return this.actualShip.getSpeed();
	}

	public void addComponent(ShipComponent component)
	{
		this.components.add(component);
	}

	public void removeComponent(ShipComponent component)
	{
		this.components.remove(component);
	}

	public List<Missile> getActiveMissiles()
	{
		return this.actualShip.getActiveMissiles();
	}

	public static final String getDesc(int value)
	{
		return (value == UNKNOWN ? "?" : "" + value);
	}

	public static final String getDesc(Distance distance)
	{
		return (distance == null ? "?" : distance.getDescription());
	}

	public String getCurrArmor()
	{
		return getDesc(this.currArmor);
	}

	public String getMaxArmor()
	{
		return getDesc(this.maxArmor);
	}

	public String getCurrShields()
	{
		return getDesc(this.currShields);
	}

	public String getMaxShields()
	{
		return getDesc(this.maxShields);
	}

	public String getCurrHP()
	{
		return getDesc(this.currHP);
	}

	public String getMaxHP()
	{
		return getDesc(this.maxHP);
	}

	public String getPassiveSensorRange()
	{
		return getDesc(this.passiveSensorRange);
	}

	public String getSensorArrayRange()
	{
		return getDesc(this.sensorArrayRange);
	}

	public String getStatus()
	{
		return "TODO Auto-generated method stub";
	}

	public Faction getFaction()
	{
		return this.actualShip.getFaction();
	}

	public Set<ShipComponent> getComponents()
	{
		return this.components;
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
}
