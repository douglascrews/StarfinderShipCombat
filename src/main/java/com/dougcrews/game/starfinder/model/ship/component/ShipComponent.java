package com.dougcrews.game.starfinder.model.ship.component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Distance;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.Target;
import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentComponentEnableDisable;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentWeaponCalledShot;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

public abstract class ShipComponent implements Target
{
	private static final Logger log = LogManager.getLogger(ShipComponent.class.getName());

	protected String name;
	protected Size size = Size.SMALL;
	protected Ship ship;
	protected Player currentPlayer;
	protected boolean hasBeenUsedThisRound = false;
	protected boolean enabled = true;
	protected boolean armored = false;
	protected boolean shielded = false;
	protected boolean hidden = false; // TODO: Implement hidden
	protected boolean resistant = false; // TODO: Implement resistant
	protected boolean lostPower = false;
	protected int maxHP;
	protected int currHP;
	protected int currArmor = 0;
	protected int currShields = 0;

	abstract public String getGenericName();

	abstract public String getDescription();

	public void enable()
	{
		if (this.enabled)
		{
			log.warn(this.getName() + " is already enabled!");
		}
		
		this.enabled = true;
		
		if (this.ship.getNetPowerAvailable() < 0)
		{
			this.ship.onPowerCoreDamaged();
		}
	}
	
	public void disable()
	{
		if (! this.enabled)
		{
			log.warn(this.getName() + " is already disabled!");
		}
		
		this.enabled = false;
	}

	public abstract Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip);
	
	protected Set<PlayerIntent> getDefaultIntents(@SuppressWarnings("unused") DetectedShip targetShip)
	{
		Set<PlayerIntent> intents = new HashSet<PlayerIntent>();
		if (this.isActive())
		{
		}
		if (this.isBroken())
		{
		}
		if (this.isDamaged())
		{
			// TODO: IntentPatchTheThing
			// TODO: IntentFixTheThing
		}
		if (this.isLostPower())
		{
			// TODO: IntentRestorePower
		}
		if (this.ship.getPoweredComponents().contains(this))
		{
			intents.add(new PlayerIntentComponentEnableDisable(this.currentPlayer, this));
		}
		// TODO: IntentBoostPower
		return intents;
	}

	public abstract Set<ReactionIntent> getPossibleReactions();
	
	protected Set<ReactionIntent> getDefaultReactions()
	{
		Set<ReactionIntent> intents = new HashSet<ReactionIntent>();
		return intents;
	}
	
	protected String getBadgeInner()
	{
		if (this.isBroken()) return "X";
		if (this.isLostPower()) return "P";
		if (! this.isEnabled()) return "x";
		if (this.currHP < this.getMaxHP()) return "" + this.currHP;
		return " ";
	}
	
	public String getBadgeFor(Ship ship)
	{
		String badgeStatus;
		if (ship.equals(this.ship))
		{
			badgeStatus = this.getBadgeInner();
		}
		else
		{
			if (ship.getDetectedComponents().containsKey(this))
			{
				// TODO: this is ignoring age of last scan
				badgeStatus = "" + this.currHP; // TODO: getBadgeInner()?
			}
			else
			{
				badgeStatus = " ";
			}
		}
		return "[" + badgeStatus + "]";
	}
	
	/**
	 * A ShipComponent is active if:
	 * <ul>
	 * <li>It is not disabled by the operator
	 * <li>It is not broken
	 * <li>It has not lost power
	 * </ul>
	 * 
	 * @return true if the component is functional
	 */
	@Override
	public boolean isActive()
	{
		boolean enabled = this.isEnabled();
		boolean broken = this.isBroken();
		boolean lostPower = this.isLostPower();
		return (enabled) && (!broken) && (!lostPower);
	}

	/**
	 * A ShipComponent is damaged if its current Hull Points are less than its
	 * maximum Hull Points.
	 * 
	 * @return true if the component is damaged; false if not
	 */
	public boolean isDamaged()
	{
		return (this.getCurrHP() < this.getMaxHP());
	}

	/**
	 * A ShipComponent is broken if its Hull Points are zero.
	 * 
	 * @return true if the component is currently broken
	 */
	public boolean isBroken()
	{
		return (this.currHP <= 0);
	}

	public int getPowerRequired()
	{
		return (this.enabled ? 1 * this.getSize().getSize() : 0);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder().append(this.getName());
		if (!this.isEnabled() || this.isBroken())
			sb.append(" [");
		if (!this.isEnabled())
			sb.append("disabled");
		if (!this.isEnabled() && this.isBroken())
			sb.append(",");
		if (this.isBroken())
			sb.append("broken");
		if (!this.isEnabled() || this.isBroken())
			sb.append("]");
		return sb.toString();
	}

	/**
	 * Returns the status of this component for a simple (no-markup) display. Outer:
	 * Enabled: "[ ]" Disabled: "{ }" Inner: Undamaged: " " Damaged: " 3 " where 3
	 * is the current Hull Points Lost Power: " P " Broken: " X " For example, a
	 * disabled and broken component will display as "{X}".
	 * 
	 * @return Simple non-markup status in the format described above
	 */
	public String getSimpleStatus()
	{
		boolean damaged = this.isDamaged();
		boolean broken = this.isBroken();
		boolean lostPower = this.isLostPower();
		StringBuilder sb = new StringBuilder().append(this.isEnabled() ? "[" : "{")
				.append(broken ? "X" : (lostPower ? "P" : (damaged ? this.getCurrHP() : " ")))
				.append(this.isEnabled() ? "]" : "}");
		return sb.toString();
	}

	@Override
	public Distance getDistanceFrom(Ship ship)
	{
		return this.ship.getDistanceFrom(ship);
	}

	/**
	 * Getting a Target Lock on a ShipComponent implies a Called Shot penalty.
	 */
	@Override
	public int getTargetLock()
	{
		return this.ship.getTargetLock() + PlayerIntentWeaponCalledShot.CALLED_SHOT_PENALTY;
	}

	@Override
	public int hitShields(int damage, boolean causeDamage, boolean useDR, int ignoreDR)
	{
		log.trace("hitShields()");
		// If shields are not installed or active, pass through all damage
		if (!this.isShielded())
			return damage;

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
			log.info(this.getName() + " shields absorb " + damageDone + " damage");
		} else
		{
			log.debug("Damage is unaffected by " + this.getName() + " shields");
		}

		// For weapons that damage the shields
		if (damageDone > 0 && causeDamage)
		{
			this.currShields -= damageDone;
			log.info(this.getName() + " shields hit for " + damageDone + " damage!");
		} else
		{
			log.debug(this.getName() + "shields are unaffected by damage");
		}
		return damageRemaining;
	}

	@Override
	public int hitArmor(int damage, boolean causeDamage, boolean useDR, int ignoreDR)
	{
		log.trace("hitArmor()");
		// If armor is not installed or active, pass through all damage
		if (!this.isArmored())
			return damage;

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
			log.info(this.getName() + " armor absorbs " + damageDone + " damage");
		} else
		{
			log.debug("Damage is unaffected by " + this.getName() + " armor");
		}

		// For weapons that damage the armor
		if (damageDone > 0 && causeDamage)
		{
			this.currArmor -= damageDone;
			log.info(this.getName() + " armor hit for " + damageDone + " damage!");
		} else
		{
			log.debug(this.getName() + " armor is unaffected by damage");
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
			log.info(this.getName() + " absorbs " + damageDone + " damage");
		} else
		{
			log.debug("Damage is unaffected by " + this.getName());
		}
		// For weapons that damage the hull
		if (damageDone > 0 && causeDamage)
		{
			this.currHP -= damageDone;
			log.info(this.getName() + " hit for " + damageDone + " damage!");
		} else
		{
			log.debug(this.getName() + " is unaffected by damage");
		}

		if (this.currHP <= 0)
		{
			log.debug(this.getName() + " is disabled by hull damage");
		}
		return damageRemaining;
	}

	public int getDefaultHullPoints()
	{
		return 5;
	}

	/**
	 * Returns the Build Points required to install this component. Shielded,
	 * Armored, Hidden, and Resistant features increase Build Points. Larger
	 * components require proportionally more Build Points to install, and features
	 * such as Shielded, Armored, Hidden, and Resistant also require proportionally
	 * more Build Points to enable.
	 * 
	 * @return total Build Points required to install this component
	 */
	public int getBuildPoints() // TODO: Unit Test
	{
		int buildPoints = 1; // default for a normal component
		buildPoints += (this.isShielded() ? 1 : 0);
		buildPoints += (this.isArmored() ? 1 : 0);
		buildPoints += (this.isHidden() ? 1 : 0);
//		buildPoints += (this.isResistant() ? 1 : 0); // TODO: Implement isResistant()
		buildPoints *= this.getSize().getSize();
		return buildPoints;
	}

	public int getDefaultShieldSize()
	{
		return 5;
	}

	public int getDefaultArmorSize()
	{
		return 1;
	}

	// Constructors

	public ShipComponent(Ship ship)
	{
		this(ship, false, false, false, false);
	}

	public ShipComponent(Ship ship, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		this.ship = ship;
		this.armored = armored;
		this.shielded = shielded;
		this.hidden = hidden;
		this.resistant = resistant;

		this.size = Size.SMALL; // All ship components are small unless specifically overridden (see Weapon)
		this.maxHP = this.getDefaultHullPoints() * this.getSize().getSize();
		this.currHP = this.maxHP;
		this.currArmor = this.getMaxArmor();
		this.currShields = this.getMaxShields();
	}

	// Getters & Setters

	@Override
	public String getName()
	{
		return (this.name == null ? this.getGenericName() : this.name);
	}

	public void setName(String name)
	{
		this.name = name;
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

	/**
	 * Returns the total bonus for all active, operating instances of this component type.
	 * @return current bonus for this component type
	 */
	public abstract int getComponentBonus();
	
	public String getConsoleStatus()
	{
		StringBuffer sb = new StringBuffer();
		if (this.isBroken())
		{
			sb.append("(Broken) ");
		}
		if (! this.isEnabled())
		{
			sb.append("(Disabled) ");
		}
		if (this.isLostPower())
		{
			sb.append("(Power Loss) ");
		}
		if (this.getSize().compareTo(Size.SMALL) > 0 || this instanceof Weapon)
		{
			sb.append(this.getSize().getDescription() + " ");
		}
		sb.append(this.getGenericName())
			.append(" (Bonus: +" + this.getComponentBonus() + " ")
			.append("HP: " + this.currHP + "/" + this.maxHP + ")");
		if (this.isArmored())
		{
			sb.append(" Armor: " + this.currArmor + "/" + this.getMaxArmor());
		}
		if (this.isShielded())
		{
			sb.append(" Shields: " + this.currShields + "/" + this.getMaxShields());
		}
		if (this.isHidden())
		{
			sb.append(" Hidden");
		}
		if (this.isResistant())
		{
			sb.append(" Resistant");
		}
		sb.append(" PCUs needed:" + this.getPowerRequired());
		return sb.toString();
	}
	
	public int getMaxShields()
	{
		return (this.isShielded() ? this.getDefaultShieldSize() * this.getSize().getSize() : 0);
	}

	public int getMaxArmor()
	{
		return (this.isArmored() ? this.getDefaultArmorSize() * this.getSize().getSize() : 0);
	}

	public Size getSize()
	{
		return this.size;
	}

	// No setSize(Size) because this should be Constructor-only
	
	public Ship getShip()
	{
		return this.ship;
	}

	// No setShip(Ship) because this should be Constructor-only

	public Player getCurrentPlayer()
	{
		return this.currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer)
	{
		this.currentPlayer = currentPlayer;
	}

	public boolean isHasBeenUsedThisRound()
	{
		return this.hasBeenUsedThisRound;
	}

	public void setHasBeenUsedThisRound(boolean hasBeenUsedThisRound)
	{
		this.hasBeenUsedThisRound = hasBeenUsedThisRound;
	}

	/**
	 * Is the component current enabled for use?
	 * Some reasons a component might be disabled are:<ul>
	 * <li> It has been manually disabled to save power
	 * <li> It has been disabled by operator error (Critical Fail on a task)
	 * <li> It has been manually disabled to prevent it from being targeted
	 * </ul>
	 * Note that an enabled component may still be unusable due to battle damage, lack of power, etc.
	 * @return true if the component is enabled for operation; false if not
	 */
	public boolean isEnabled()
	{
		return this.enabled;
	}

	public void setEnabled(boolean isEnabled)
	{
		this.enabled = isEnabled;
	}

	public boolean isArmored()
	{
		return this.armored;
	}

	@SuppressWarnings("unused")
	private void setArmored(boolean armored)
	{
		this.armored = armored;
	}
	
	public boolean isShielded()
	{
		return this.shielded;
	}

	@SuppressWarnings("unused")
	private void setShielded(boolean shielded)
	{
		this.shielded = shielded;
	}

	public boolean isHidden()
	{
		return this.hidden;
	}

	@SuppressWarnings("unused")
	private void setHidden(boolean hidden)
	{
		this.hidden = hidden;
	}

	public boolean isResistant()
	{
		return this.resistant;
	}

	@SuppressWarnings("unused")
	private void setResistant(boolean resistant)
	{
		this.resistant = resistant;
	}

	/**
	 * ShipComponents may lose power on Critical Fail results of dice rolls. It does
	 * not affect their damaged or enabled status, but they are inactive until a
	 * crewmember restores power.
	 * 
	 * @return true if the component has lost power; false if not
	 */
	public boolean isLostPower()
	{
		return this.lostPower;
	}

	public void setLostPower(boolean lostPower)
	{
		this.lostPower = lostPower;
	}

	public int getMaxHP()
	{
		return this.maxHP;
	}

	public int getCurrHP()
	{
		return this.currHP;
	}

	public void setCurrHP(int currHP)
	{
		this.currHP = currHP;
	}

	public int getCurrArmor()
	{
		return (this.armored ? this.currArmor : 0);
	}

	public void setCurrArmor(int currArmor)
	{
		this.currArmor = currArmor;
	}

	public int getCurrShields()
	{
		return (this.shielded ? this.currShields : 0);
	}

	public void setCurrShields(int currShields)
	{
		this.currShields = currShields;
	}

	// TODO: Builders for everything!

	public enum Size
	{
		SMALL(1, "Small"), MEDIUM(2, "Medium"), LARGE(3, "Large"), CAPITAL(4, "Capital");

		private final int size;
		private final String description;

		Size(int size, String description)
		{
			this.size = size;
			this.description = description;
		}

		@Override
		public String toString()
		{
			return this.description + " (" + this.size + ")";
		}

		public int getSize()
		{
			return this.size;
		}

		public String getDescription()
		{
			return this.description;
		}
	}
}