package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Dice;
import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

public class PlayerIntentWeaponOvercharge extends PlayerIntentWeaponAbstract
{
	private static final Logger log = LogManager.getLogger(PlayerIntentWeaponOvercharge.class.getName());

	private Type type;
	
	/**
	 * d20 + Engineering vs. 10 + (max weapon damage)
	 */
	@Override
	public void execute()
	{
		int d20 = Dice.d(20);
		int engineeringBonus = this.player.getEngineeringBonus();
		int totalBonus = d20 + engineeringBonus;
		int maxWeaponDamage = this.weapon.getDamageDiceNumber() * this.weapon.getDamageDiceType();
		int successTarget = 10 + maxWeaponDamage;
		if (Dice.isCriticalFail(d20, successTarget, totalBonus))
		{
			log.info(this.player.getName() + " fumbles in an Overcharge on " + this.weapon.getName() + "! The weapon loses power!");
			this.weapon.setLostPower(true);
		}
		else // not a Critical Fail
		{
			boolean success = (d20 + engineeringBonus >= successTarget);
			log.info(this.player.getName() + " attempts " + this.type.getDescription() + ": (" + d20 + ") + " + engineeringBonus + " vs. DC " + (10+maxWeaponDamage) + " -> " + (success ? "Success!" : "Fail."));
			if (success)
			{
				if (this.type.equals(Type.CRIT_RANGE))
				{
					int critBonus = this.weapon.getCriticalHitBonus() + 1;
					this.weapon.setCriticalHitBonus(critBonus);
				}
				else if (this.type.equals(Type.CRIT_DIE))
				{
					int critExtraDie = this.weapon.getCriticalHitExtraDice() + 1;
					this.weapon.setCriticalHitExtraDice(critExtraDie);
				}
				else if (this.type.equals(Type.IGNORE_DR))
				{
					// TODO: This is probably overpowered as currently implemented
					int overchargeDRIgnore = this.weapon.getOverchargeDRIgnore() + 1;
					this.weapon.setOverchargeDRIgnore(overchargeDRIgnore);
				}
				else if (this.type.equals(Type.TO_HIT))
				{
					int toHitBonus = this.weapon.getOverchargeToHitBonus() + 1;
					this.weapon.setOverchargeToHitBonus(toHitBonus);
				}
				else if (this.type.equals(Type.DAMAGE))
				{
					int damageBonus = this.weapon.getOverchargeDamageBonus() + (1 * this.weapon.getDamageDiceNumber());
					this.weapon.setOverchargeDamageBonus(damageBonus);
				}
				else
				{
					log.error("Unrecognized Overcharge type: " + this.actionType.getName());
				}
			}
		}
	}

	@Override
	public String toString()
	{
		return super.toString() + ":" + this.type.getName();
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.type == null) ? 0 : this.type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object that)
	{
		if (this == that) return true;
		if (!super.equals(that)) return false;
		if (getClass() != that.getClass()) return false;
		PlayerIntentWeaponOvercharge thatIntent = (PlayerIntentWeaponOvercharge) that;
		if (this.type != thatIntent.type) return false;
		return true;
	}
	
	// Constructors

	public PlayerIntentWeaponOvercharge(Player player, Weapon weapon, Type type)
	{
		super(ActionType.MOVE, player, weapon);
		this.type = type;
	}
	
	// Getters & Setters

	public Type getType()
	{
		return this.type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	@Override
	protected String getName()
	{
		return "Overcharge";
	}

	// Subclasses
	
	public enum Type
	{
		CRIT_RANGE ("Critical Range", "Crit Range +1 (i.e., 19-20"),
		CRIT_DIE ("Critical Die", "+1 die of damage on a Crit"),
		IGNORE_DR ("Ignore DR", "Ignore 1st point of Damage Reduction"),
		DAMAGE ("+1 Damage", "+1 Damage, multiplied on Critical Hit"),
		TO_HIT ("+1 To Hit", "+1 To Hit");

		private String name;
		private String description;
		
		private Type(String name, String description)
		{
			this.name = name;
			this.description = description;
		}

		public String getName()
		{
			return this.name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getDescription()
		{
			return this.description;
		}

		public void setDescription(String description)
		{
			this.description = description;
		}
	}
}
