package com.dougcrews.game.starfinder.model;

import com.dougcrews.game.starfinder.model.ship.component.weapon.Weapon;

public enum Distance
{
	POINT_BLANK (0, "Point Blank"),
	SHORT (1, "Short"),
	MEDIUM (2, "Medium"),
	LONG (3, "Long"),
	EXTREME (4, "Extreme"),
	BEYOND_EXTREME (5, "Beyond Extreme");
	
	private final int distance;
	private final String description;
	
	private Distance(int distance, String description)
	{
		this.distance = distance;
		this.description = description;
	}
	
	/**
	 * Distance penalty for non-weapon ship component actions
	 * @return penalty for ship component actions due to distance between you and target
	 * @see Weapon#getDistanceMod(Distance) for distance modifiers for weapons
	 */
	public int getDistancePenalty()
	{
		return this.distance * -2;
	}
	
	/**
	 * Returns the next Distance value farther away. The maximum is Beyond Extreme.
	 * @return next farther Distance from this
	 */
	public Distance increment()
	{
		Distance distance = null;
		switch(this)
		{
		case POINT_BLANK:
			distance = SHORT;
			break;
		case SHORT:
			distance = MEDIUM;
			break;
		case MEDIUM:
			distance = LONG;
			break;
		case LONG: 
			distance = EXTREME;
			break;
		case EXTREME: 
			distance = BEYOND_EXTREME;
			break;
		case BEYOND_EXTREME:
			distance = BEYOND_EXTREME;
			break;
		}
		return distance;
	}
	
	/**
	 * Returns the next Distance value closer. The minimum is Point Blank.
	 * @return next closer Distance from this
	 */
	public Distance decrement()
	{
		Distance distance = null;
		switch(this)
		{
		case POINT_BLANK:
			distance = POINT_BLANK;
			break;
		case SHORT:
			distance = POINT_BLANK;
			break;
		case MEDIUM:
			distance = SHORT;
			break;
		case LONG: 
			distance = MEDIUM;
			break;
		case EXTREME: 
			distance = LONG;
			break;
		case BEYOND_EXTREME:
			distance = EXTREME;
			break;
		}
		return distance;
	}

	public static Distance max(Distance d1, Distance d2)
	{
		return (d1.getDistance() > d2.getDistance() ? d1 : d2);
	}
	
	public static Distance min(Distance d1, Distance d2)
	{
		return (d1.getDistance() < d2.getDistance() ? d1 : d2);
	}
	
	@Override
	public String toString()
	{
		return this.description + " (" + this.distance + ")";
 	}

	public int getDistance()
	{
		return this.distance;
	}

	public String getDescription()
	{
		return this.description;
	}
}
