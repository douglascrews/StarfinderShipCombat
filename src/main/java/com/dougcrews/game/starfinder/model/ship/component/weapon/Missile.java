package com.dougcrews.game.starfinder.model.ship.component.weapon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Distance;
import com.dougcrews.game.starfinder.model.Movable;
import com.dougcrews.game.starfinder.model.Target;

public abstract class Missile implements Movable
{
	private static final Logger log = LogManager.getLogger(Missile.class.getName());
	
	private Target target;
	private MissileLauncher launcher;
	private Distance distanceFromTarget;
	private boolean hasTL;
	private boolean isApproachingTarget;
	private boolean isCrit = false;
	
	@Override
	public void move()
	{
		if (this.hasTL)
		{
			this.setDistanceFromTarget(this.distanceFromTarget.decrement());
			if (this.distanceFromTarget.equals(Distance.POINT_BLANK))
			{
				// Has TL and PB range == Hit!
				this.onHitTarget();
			}
		}
		else
		{
			if (this.distanceFromTarget.equals(Distance.POINT_BLANK))
			{
				// Missile is passing by its target and continuing outward
				this.setApproachingTarget(false);
			}
			
			if (this.isApproachingTarget())
			{
				this.setDistanceFromTarget(this.distanceFromTarget.decrement());
			}
			else
			{
				if (this.getDistanceFromTarget().equals(Distance.BEYOND_EXTREME))
				{
					log.info(this.getName() + " passes out of range.");
					this.onMissileInactive();
				}
				else
				{
					this.setDistanceFromTarget(this.distanceFromTarget.increment());
				}
			}
		}
	}
	
	public void onHitTarget()
	{
		log.info(this.getName() + " hits " + this.target.getName() + "!");
		int rawDamage = this.getLauncher().rollDamage(this.isCrit);
		this.getLauncher().doDamage(this.target, rawDamage);
		
		this.onMissileInactive();
	}

	public void launch(Target target)
	{
		this.target = target;
		this.distanceFromTarget = this.getLauncher().getShip().getDistanceFrom(target);
		this.setHasTL(true);
		this.launcher.addFiredMissile(this);
		this.launcher.unloadMissile();
	}

	/**
	 * Called when a missile either hits and is destroyed, or passes beyond control
	 * range and is lost.
	 */
	public void onMissileInactive()
	{
		// The missile exploded, so let's clean up some references
		this.setTarget(null);
		this.setHasTL(false);
		this.getLauncher().removeFiredMissile(this);
		this.setLauncher(null);
	}
	
	public void onTargetLockLost()
	{
		this.setHasTL(false);
	}
	
	public void onTargetLockRegained()
	{
		this.setHasTL(true);
		this.setApproachingTarget(true);
	}
	
	public String getSimpleDescription()
	{
		return this.getName() + " (" + this.getLauncher().getShip().getName() + ")";
	}
	
	@Override
	public String toString()
	{
		return this.getStatus();
	}
	
	// Constructors
	
	public Missile(MissileLauncher launcher)
	{
		assert launcher != null;
		this.launcher = launcher;
		this.isApproachingTarget = true;
	}
	
	// Getters & Setters

	public abstract String getGenericName();
	
	public String getName()
	{
		return this.getLauncher().getSize().getDescription() + " " + this.getGenericName();
	} 

	public String getStatus()
	{
		return this.getName() + " from " + this.getLauncher().getShip().getName() + " targeting "
				+ this.getTarget().getName() + " at " + this.getDistanceFromTarget().getDescription() + " range and "
				+ (this.isApproachingTarget ? "closing" : "moving away");
	}

	public Target getTarget()
	{
		return this.target;
	}

	public void setTarget(Target target)
	{
		this.target = target;
	}

	public MissileLauncher getLauncher()
	{
		return this.launcher;
	}

	public void setLauncher(MissileLauncher launcher)
	{
		this.launcher = launcher;
	}

	public Distance getDistanceFromTarget()
	{
		return this.distanceFromTarget;
	}

	public void setDistanceFromTarget(Distance distanceFromTarget)
	{
		this.distanceFromTarget = distanceFromTarget;
	}

	public boolean hasTL()
	{
		return this.hasTL;
	}

	public void setHasTL(boolean hasTL)
	{
		this.hasTL = hasTL;
	}

	public boolean isApproachingTarget()
	{
		return this.isApproachingTarget;
	}

	public void setApproachingTarget(boolean isApproachingTarget)
	{
		this.isApproachingTarget = isApproachingTarget;
	}
	
	public int getTL()
	{
		return 20 + this.launcher.getShip().getTier().getTierBonus();
	}

	public boolean isCrit()
	{
		return this.isCrit;
	}

	public void setCrit(boolean isCrit)
	{
		this.isCrit = isCrit;
	}
}
