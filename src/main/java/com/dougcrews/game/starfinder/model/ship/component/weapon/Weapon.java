package com.dougcrews.game.starfinder.model.ship.component.weapon;

import java.security.InvalidParameterException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Dice;
import com.dougcrews.game.starfinder.model.Distance;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.Target;
import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentComponentEnableDisable;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentWeaponCalledShot;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentWeaponFire;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentWeaponFireAtWill;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentWeaponOvercharge;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentWeaponOvercharge.Type;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;

public abstract class Weapon extends ShipComponent
{
	private static final Logger log = LogManager.getLogger(Weapon.class.getName());
	
	protected boolean affectedByShields;
	protected boolean affectsShields;
	protected boolean affectedByArmor;
	protected boolean affectsArmor;
	protected boolean affectedByHull;
	protected boolean affectsHull;

	protected int criticalHitBonus = 0;
	protected int criticalHitExtraDice = 0;
	protected int overchargeDRIgnore = 0;
	protected int overchargeToHitBonus = 0;
	protected int overchargeDamageBonus = 0;
	protected int fireAtWillPenalty = 0;

	@Override
	public String getName()
	{
		return this.getSize().getDescription() + " " + this.getGenericName();
	}
	
	/**
	 * By default weapons do damage immediately. Some weapon types will override this method.
	 * @param target
	 */
	public void onFireSuccessful(Target target, boolean isCrit)
	{
		log.trace("onFireSuccessful()");

		int rawDamage = rollDamage(isCrit);
		log.debug("raw damage = " + rawDamage);

		// Damage is immediate
		doDamage(target, rawDamage);
	}

	/**
	 * Is the roll a Critical Hit? It is if:<ul>
	 * <li> it is a hit, and
	 * <li> the raw die roll is equal to or higher than the Weapon's critical threshold, and
	 * <li> a natural 20 is not needed to hit
	 * </ul>
	 * @param d20
	 * @param targetLock
	 * @param totalBonuses
	 * @return true if the die roll was a Critical Hit; false if not
	 */
	public boolean isCriticalHit(int d20, int targetLock, int totalBonuses)
	{
		boolean isHit = (d20 + totalBonuses) >= targetLock;
		if (! isHit) return false;
		
		boolean isDieRollCrit = (d20 >= (20 + this.getCriticalHitBonus()));
		if (! isDieRollCrit) return false;
		
		boolean canHitOn19 = (19 + totalBonuses) >= targetLock;
		if (! canHitOn19) return false;
		
		log.info("Critical Hit!");
		return true;
	}

	public void doDamage(Target target, int rawDamage)
	{
		log.trace("doDamage()");
		
		int damageRemaining = target.hitShields(rawDamage, this.affectsShields, this.affectedByShields, this.overchargeDRIgnore);
		if (damageRemaining > 0)
		{
			damageRemaining = target.hitArmor(damageRemaining, this.affectsArmor, this.affectedByArmor, this.overchargeDRIgnore);
		}
		if (damageRemaining > 0)
		{
			target.hitHull(damageRemaining, this.affectsHull, this.affectedByHull, this.overchargeDRIgnore);
		}
	}

	protected int rollDamage(boolean isCrit)
	{
		int damage = 0;
		for (int ii = 0; ii < this.getDamageDiceNumber(); ii++)
		{
			int diceDamage = (int)(Math.random() * this.getDamageDiceType() + 1);
			damage += diceDamage;
		}
		log.debug("raw damage = " + damage);
		
		damage += this.getOverchargeDamageBonus();

		int critDamage = 0;
		if (isCrit)
		{
			for (int ii = 0; ii < this.getDamageDiceNumber(); ii++)
			{
				int diceDamage = (int)(Math.random() * this.getDamageDiceType() + 1);
				critDamage += diceDamage;
			}
			
			for (int ii = 0; ii < this.getCriticalHitExtraDice(); ii++)
			{
				int diceDamage = (int)(Math.random() * this.getDamageDiceType() + 1);
				critDamage += diceDamage;
			}

			log.debug("crit damage = " + critDamage);
		}
		damage += critDamage;

		return damage;
	}

	/**
	 * Returns the custom to-hit modifier for this size weapon at the specified range.
	 * Small weapon distance mod =  +2/+0/-2/-4/-6
	 * Medium weapon distance mod = +0/+0/+0/-2/-4
	 * Large weapon distance mod =  -2/+0/+0/+0/-2
	 * Capital weapon distance mod = X/-2/-0/-0/-0 (cannot target at Point Blank range)
	 * @param distance Distance to the target
	 * @return modifier to to-hit rolls for this weapon at the specified distance
	 * @throws InvalidParameterException if a Capital weapon tries to target at Point Blank range,
	 *         or any weapon tries to target at Beyond Extreme range, or for unrecognized Enum values
	 * @see Distance#getDistancePenalty() for other ShipComponent range mods
	 */
	public int getDistanceMod(Distance distance) throws InvalidParameterException
	{
		int distanceMod = 0;
		switch(this.getSize().getSize())
		{
		case 1: // Small
			switch(distance.getDistance())
			{
			case 0: // Point Blank
				distanceMod = +2;
				break;
			case 1: // Short
				distanceMod = 0;
				break;
			case 2: // Medium
				distanceMod = -2;
				break;
			case 3: // Long
				distanceMod = -4;
				break;
			case 4: // Extreme
				distanceMod = -6;
				break;
			case 5: // Beyond Extreme
				throw new InvalidParameterException("Weapons cannot target at Beyond Extreme range");
			default: // Unrecognized Distance
				throw new InvalidParameterException("Unrecognized Distance value: " + distance.getDistance());
			}
			break;
		case 2: // Medium
			switch(distance.getDistance())
			{
			case 0: // Point Blank
				distanceMod = 0;
				break;
			case 1: // Short
				distanceMod = 0;
				break;
			case 2: // Medium
				distanceMod = 0;
				break;
			case 3: // Long
				distanceMod = -2;
				break;
			case 4: // Extreme
				distanceMod = -4;
				break;
			case 5: // Beyond Extreme
				throw new InvalidParameterException("Weapons cannot target at Beyond Extreme range");
			default: // Unrecognized Distance
				throw new InvalidParameterException("Unrecognized Distance value: " + distance.getDistance());
			}
			break;
		case 3: // Large
			switch(distance.getDistance())
			{
			case 0: // Point Blank
				distanceMod = -2;
				break;
			case 1: // Short
				distanceMod = 0;
				break;
			case 2: // Medium
				distanceMod = 0;
				break;
			case 3: // Long
				distanceMod = 0;
				break;
			case 4: // Extreme
				distanceMod = -2;
				break;
			case 5: // Beyond Extreme
				throw new InvalidParameterException("Weapons cannot target at Beyond Extreme range");
			default: // Unrecognized Distance
				throw new InvalidParameterException("Unrecognized Distance value: " + distance.getDistance());
			}
			break;
		case 4: // Capital
			switch(distance.getDistance())
			{
			case 0: // Point Blank
				throw new InvalidParameterException("Capital weapons cannot target at Point Blank range");
			case 1: // Short
				distanceMod = -2;
				break;
			case 2: // Medium
				distanceMod = 0;
				break;
			case 3: // Long
				distanceMod = 0;
				break;
			case 4: // Extreme
				distanceMod = 0;
				break;
			case 5: // Beyond Extreme
				throw new InvalidParameterException("Weapons cannot target at Beyond Extreme range");
			default: // Unrecognized Distance
				throw new InvalidParameterException("Unrecognized Distance value: " + distance.getDistance());
			}
			break;
		default:
			throw new InvalidParameterException("Unrecognized ShipComponent.Size value: " + this.getSize().getSize());
		}
		return distanceMod;
	}

	@Override
	public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
	{
		log.trace("getPossibleIntents()");
		log.debug("detectedShip = " + targetShip);
		Set<PlayerIntent> intents = this.getDefaultIntents(targetShip);
		
		if (! this.hasBeenUsedThisRound && this.isActive())
		{
			if (targetShip != null)
			{
				intents.add(new PlayerIntentWeaponFire(this.currentPlayer, this, targetShip.getActualShip()));
				intents.add(new PlayerIntentWeaponFireAtWill(this.currentPlayer, this, targetShip.getActualShip()));
			}
			for (ShipComponent component : this.getShip().getDetectedComponents().keySet())
			{
				intents.add(new PlayerIntentWeaponCalledShot(this.currentPlayer, this, component));
				intents.add(new PlayerIntentWeaponFireAtWill(this.currentPlayer, this, component));
			}
			intents.add(new PlayerIntentWeaponOvercharge(this.currentPlayer, this, Type.CRIT_DIE));
			intents.add(new PlayerIntentWeaponOvercharge(this.currentPlayer, this, Type.CRIT_RANGE));
			intents.add(new PlayerIntentWeaponOvercharge(this.currentPlayer, this, Type.DAMAGE));
			intents.add(new PlayerIntentWeaponOvercharge(this.currentPlayer, this, Type.IGNORE_DR));
			intents.add(new PlayerIntentWeaponOvercharge(this.currentPlayer, this, Type.TO_HIT));
			
			intents.add(new PlayerIntentComponentEnableDisable(this.currentPlayer, this));
		}
		return intents;
	}

	@Override
	public Set<ReactionIntent> getPossibleReactions()
	{
		Set<ReactionIntent> intents = this.getDefaultReactions();
		if (this.isActive())
		{
//			for (Ship ship : this.getShip().getDetectedShips())
//			{
//			}
//			for (ShipComponent component : this.getShip().getDetectedComponents().keySet())
//			{
//			}
//			for (Missile missile : this.getShip().getDetectedMissiles())
//			{
//			}
		}
		return intents;
	}

	// Intents
	
	/**
	 * Player fires this weapon at the chosen Target. All bonuses/penalties are calculated and if a hit is
	 * scored, the weapon's onHit() method is called. Some weapons will do damage immediately and others may
	 * have delayed effects.
	 * @param player Player firing the weapon
	 * @param target Target of the weapon
	 * @return true if the firing attempt succeeds at getting a Target Lock
	 */
	public boolean fire(Player player, Target target)
	{
		log.trace("fire()");

		int d20 = player.d20();
		int playerGunneryBonus = player.getGunneryBonus();
		int weaponOverchargeToHitBonus = this.getOverchargeToHitBonus();
		int fireAtWillPenalty = this.getFireAtWillPenalty();
		int distanceMod = this.getDistanceMod(target.getDistanceFrom(this.ship));
		int targetLock = target.getTargetLock();
		int totalRoll = d20 + playerGunneryBonus + weaponOverchargeToHitBonus + fireAtWillPenalty + distanceMod;
		boolean success = (totalRoll >= targetLock);
		log.info(player.getShip().getName() + " fires " + this.getName() + " at " + target.getName() + ": (" + d20 + ") + " + playerGunneryBonus +
			" + " + weaponOverchargeToHitBonus + " + " + fireAtWillPenalty + " + " + distanceMod + " = " + totalRoll + " vs. TL" + targetLock + " -> " + (success ? "Hit!" : "Miss"));

		if (success)
		{
			this.onFireSuccessful(target, this.isCriticalHit(d20, targetLock, playerGunneryBonus + distanceMod));
		}
		return success;
	}
	
	/**
	 * Capital sized weapons cannot target at Point Blank range. This method does not factor in Sensor range.
	 * @param distance Distance of targeted object
	 * @return can this weapon target opponents at the given range, all other factors allowing?
	 */
	public boolean canTarget(Distance distance)
	{
		// TODO: Unit Test
		boolean isCapitalAtPointBlank = (this.getSize().equals(Size.CAPITAL) && distance.equals(Distance.POINT_BLANK));
		if (isCapitalAtPointBlank) log.debug("Capital weapon cannot target at Point Blank range");
		boolean isBeyondExtreme = distance.equals(Distance.BEYOND_EXTREME);
		if (isBeyondExtreme) log.debug("Weapons cannot target at Beyond Extreme range");
		boolean canTarget = !(isCapitalAtPointBlank || isBeyondExtreme);
		return canTarget;
	}
	
	@Override
	public String toString()
	{
		return getName();
	}

	// Constructors
	
	public Weapon(Ship ship, Size size)
	{
		this(ship, size, false, false, false, false);
	}

	public Weapon(Ship ship, Size size, boolean armored, boolean shielded, boolean hidden, boolean resistant)
	{
		this(ship, size, armored, shielded, hidden, resistant, true, true, true, true, true, true);
	}

	/**
	 * Create a new Weapon.
	 * @param size Size of this weapon. This impacts range penalties, HP of this weapon, Build Points required, and more
	 * @param affectedByShields is the damage from this weapon reduced by Shields?
	 * @param affectsShields does this weapon damage Shields?
	 * @param affectedByArmor is the damage from this weapon reduced by Armor?
	 * @param affectsArmor does this weapon damage Armor?
	 * @param affectedByHull is the damage from this weapon reduced by Hull?
	 * @param affectsHull does this weapon damage Hull?
	 */
	public Weapon(Ship ship, Size size, boolean armored, boolean shielded, boolean hidden, boolean resistant, boolean affectedByShields, boolean affectsShields, boolean affectedByArmor, boolean affectsArmor, boolean affectedByHull, boolean affectsHull)
	{
		super(ship, armored, shielded, hidden, resistant);
		this.size = size;
		this.affectedByShields = affectedByShields;
		this.affectsShields = affectsShields;
		this.affectedByArmor = affectedByArmor;
		this.affectsArmor = affectsArmor;
		this.affectedByHull = affectedByHull;
		this.affectsHull = affectsHull;
	}

	// Getters & Setters
	
	@Override
	public int getPowerRequired()
	{
		// Larger weapons require proportionally more power
		return this.getDamageDiceNumber();
	}

	/**
	 * What is the die type for this weapon's base damage? Typical values are d4, d6, d8, ...
	 * @return maximum value of a single die roll when rolling for damage
	 */
	abstract public int getDamageDiceType();

	/**
	 * What is the number of dice for this weapon's base damage? Typical values are 1, 2, 3, 4, ...
	 * @return number of dice to roll when rolling for damage
	 */
	public int getDamageDiceNumber()
	{
		return this.getSize().getSize();
	}

	public int getCriticalHitBonus()
	{
		return this.criticalHitBonus;
	}

	public void setCriticalHitBonus(int criticalHitBonus)
	{
		this.criticalHitBonus = criticalHitBonus;
	}

	public int getCriticalHitExtraDice()
	{
		return this.criticalHitExtraDice;
	}

	public void setCriticalHitExtraDice(int criticalHitExtraDice)
	{
		this.criticalHitExtraDice = criticalHitExtraDice;
	}

	public int getOverchargeDRIgnore()
	{
		return this.overchargeDRIgnore;
	}

	public void setOverchargeDRIgnore(int overchargeDRIgnore)
	{
		this.overchargeDRIgnore = overchargeDRIgnore;
	}

	public int getOverchargeToHitBonus()
	{
		return this.overchargeToHitBonus;
	}

	public void setOverchargeToHitBonus(int overchargeToHitBonus)
	{
		this.overchargeToHitBonus = overchargeToHitBonus;
	}

	public int getOverchargeDamageBonus()
	{
		return this.overchargeDamageBonus;
	}

	public void setOverchargeDamageBonus(int overchargeDamageBonus)
	{
		this.overchargeDamageBonus = overchargeDamageBonus;
	}

	public int getFireAtWillPenalty()
	{
		return this.fireAtWillPenalty;
	}

	public void setFireAtWillPenalty(int fireAtWillPenalty)
	{
		this.fireAtWillPenalty = fireAtWillPenalty;
	}

	@Override
	public int getComponentBonus()
	{
		return this.getOverchargeToHitBonus() + this.getFireAtWillPenalty();
	}
	
	@Override
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
		sb.append(this.getSize().getDescription() + " ");
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
		sb.append("<br/>"); // Horrible!
		sb.append(this.getDistanceStatus(null));
		return sb.toString();
	}
	
	public String getDistanceStatus(DetectedShip target)
	{
		StringBuffer sb = new StringBuffer("To Hit modifiers: ");
		if (target == null)
		{
			sb.append(Distance.POINT_BLANK.getDescription() + " " + Dice.plusMinus(this.getDistanceMod(Distance.POINT_BLANK)) + " ");
			sb.append(Distance.SHORT.getDescription() + " " + Dice.plusMinus(this.getDistanceMod(Distance.SHORT)) + " ");
			sb.append(Distance.MEDIUM.getDescription() + " " + Dice.plusMinus(this.getDistanceMod(Distance.MEDIUM)) + " ");
			sb.append(Distance.LONG.getDescription() + " " + Dice.plusMinus(this.getDistanceMod(Distance.LONG)) + " ");
			sb.append(Distance.EXTREME.getDescription() + " " + Dice.plusMinus(this.getDistanceMod(Distance.EXTREME)) + " ");
		}
		else
		{
			Distance distance = this.getDistanceFrom(target.getActualShip());
			sb.append(distance.getDescription() + " " + Dice.plusMinus(this.getDistanceMod(distance)));
		}
		return sb.toString();
	}
}
