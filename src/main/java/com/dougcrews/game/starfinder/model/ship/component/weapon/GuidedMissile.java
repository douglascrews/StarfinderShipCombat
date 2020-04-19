package com.dougcrews.game.starfinder.model.ship.component.weapon;

public class GuidedMissile extends Missile
{
	/**
	 * Guided missiles which lose Target Lock will continue on course, passing by their 
	 * target and eventually lost to control attempts. If the ship that fired it is able
	 * to regain Target Lock, the missile resumes course (turning around 180 degrees if
	 * necessary) to hone in on their target.
	 */
	@Override
	public void onTargetLockLost()
	{
		super.onTargetLockLost();
	}

	// Constructors
	
	public GuidedMissile(MissileLauncher launcher)
	{
		super(launcher);
	}

	// Getters & Setters
	
	@Override
	public String getGenericName()
	{
		return "Guided Missile";
	}
}
