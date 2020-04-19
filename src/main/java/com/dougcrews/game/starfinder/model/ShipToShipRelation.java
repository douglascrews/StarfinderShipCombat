package com.dougcrews.game.starfinder.model;

import java.security.InvalidParameterException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.ship.Ship;

public class ShipToShipRelation
{
	private static final Logger log = LogManager.getLogger(ShipToShipRelation.class.getName());
	
	private Ship ship1;
	private Ship ship2;
	private Distance distance;
//	// Is the specified ship facing toward the other ship?
//	private boolean ship1Facing;
//	private boolean ship2Facing;
	// What does the specified ship want to do about distance?
	private DistanceIntent ship1DistanceIntent;
	private DistanceIntent ship2DistanceIntent;
//	// Does the specified ship want to face the other ship?
//	private FacingIntent ship1FacingIntent;
//	private FacingIntent ship2FacingIntent;
//	// Does the specified ship want the other ship facing them?
//	private FacingIntent ship1FacingThemIntent;
//	private FacingIntent ship2FacingThemIntent;

	public static enum DistanceIntent
	{
		MOVE_CLOSER, STATUS_QUO, MOVE_FARTHER
	};

//	public static enum FacingIntent
//	{
//		FACE_FORE, DONT_CARE, FACE_AFT
//	};
	
	public boolean contains(Ship ship)
	{
		if (this.ship1 == ship)
			return true;
		if (this.ship2 == ship)
			return true;
		return false;
	}

	public void moveCloser()
	{
		switch (this.distance)
		{
		case BEYOND_EXTREME:
			this.distance = Distance.EXTREME;
			break;
		case EXTREME:
			this.distance = Distance.LONG;
			break;
		case LONG:
			this.distance = Distance.MEDIUM;
			break;
		case MEDIUM:
			this.distance = Distance.SHORT;
			break;
		case SHORT:
			this.distance = Distance.POINT_BLANK;
			break;
		case POINT_BLANK:
			this.distance = Distance.POINT_BLANK;
			break;
		default:
			throw new RuntimeException("Invalid enum value " + this.distance);
		}
		
//		if (this.getShip1DistanceIntent().equals(DistanceIntent.MOVE_CLOSER))
//		{
//			this.setShip1Facing(true);
//		}
//		else if (this.getShip1DistanceIntent().equals(DistanceIntent.MOVE_FARTHER))
//		{
//			this.setShip1Facing(false);
//		}
//		
//		if (this.getShip2DistanceIntent().equals(DistanceIntent.MOVE_CLOSER))
//		{
//			this.setShip2Facing(true);
//		}
//		else if (this.getShip2DistanceIntent().equals(DistanceIntent.MOVE_FARTHER))
//		{
//			this.setShip2Facing(false);
//		}
	}

	public void moveFarther()
	{
		switch (this.distance)
		{
		case POINT_BLANK:
			this.distance = Distance.SHORT;
			break;
		case SHORT:
			this.distance = Distance.MEDIUM;
			break;
		case MEDIUM:
			this.distance = Distance.LONG;
			break;
		case LONG:
			this.distance = Distance.EXTREME;
			break;
		case EXTREME:
			this.distance = Distance.BEYOND_EXTREME;
			break;
		case BEYOND_EXTREME:
			this.distance = Distance.BEYOND_EXTREME;
			break;
		default:
			throw new RuntimeException("Invalid enum value " + this.distance);
		}
	}

	public void moveStatusQuo()
	{
		log.debug(this.getShip1().getName() + " and " + this.getShip2().getName() + " retain status quo.");
	}

// TODO: Revisit Facing when we have Fore/Aft shields implemented; until then, it's irrelevant
//	/**
//	 * Sets whether each ship in this relationship is facing toward or away from the other.
//	 * If Distance is Medium or longer:<ul>
//	 * <li>A ship attempting to move closer is facing toward the enemy
//	 * <li>A ship attempting to move farther is facing away from the enemy
//	 * <li>A ship attempting status quo may choose their facing to the enemy, or allow it to be random
//	 * @see FacingIntent
//	 * </ul>
//	 * If Distance is Short or Point Blank:<ul>
//	 * <li>Winner of the Pilot contest chooses their facing to enemy, and enemy's facing to them
//	 * <li>Tie results in both being determined randomly
//	 * </u>
//	 * @param pilot1Roll First pilot's rolled Pilot check
//	 * @param pilot2Roll Second pilot's rolled Pilot check
//	 */
//	public void setFacing(int pilot1Roll, int pilot2Roll)
//	{
//		// --------- Ship1 ---------
//		if (this.getDistance().compareTo(Distance.MEDIUM) < 0) // Distance is Short or Point Blank
//		{
//			// Winner of the pilot contest chooses their Facing to the loser, and loser's Facing to themself
//			// Tie results in Facing of both determined randomly.
//			if (pilot1Roll > pilot2Roll) // Ship1 wins
//			{
//				// Set ship1 facing toward the other ship
//				if (this.getShip1FacingIntent().equals(FacingIntent.FACE_FORE))
//				{
//					// Ship1 wants to face toward ship2
//					this.setShip1Facing(true);
//				}
//				else if (this.getShip1FacingIntent().equals(FacingIntent.FACE_AFT))
//				{
//					// Ship1 wants to face away from ship2
//					this.setShip1Facing(false);
//				}
//				else
//				{
//					// Ship1 does not care; determine randomly
//					this.setShip1Facing(Dice.flipACoin());
//				}
//			}
//			else if (pilot2Roll > pilot1Roll) // Ship2 wins
//			{
//				if (this.getShip2FacingThemIntent().equals(FacingIntent.FACE_FORE))
//				{
//					// Ship2 wants ship1 to face toward ship2
//					this.setShip1Facing(true);
//				}
//				else if (this.getShip2FacingThemIntent().equals(FacingIntent.FACE_AFT))
//				{
//					// Ship2 wants ship1 to face away from ship2
//					this.setShip1Facing(false);
//				}
//				else
//				{
//					// Ship2 does not care; determine randomly
//					this.setShip1Facing(Dice.flipACoin());
//				}
//			}
//			else // Tie; determine randomly
//			{
//				this.setShip1Facing(Dice.flipACoin());
//			}
//		}
//		else if (this.getDistance().compareTo(Distance.SHORT) > 0) // Distance is Medium or longer
//		{
//			if (this.getShip1DistanceIntent().equals(DistanceIntent.MOVE_CLOSER))
//			{
//				this.setShip1Facing(true);
//			}
//			else if (this.getShip1DistanceIntent().equals(DistanceIntent.MOVE_FARTHER))
//			{
//				this.setShip1Facing(false);
//			}
//			else if (this.getShip1DistanceIntent().equals(DistanceIntent.STATUS_QUO))
//			{
//				if (this.getShip1FacingIntent().equals(FacingIntent.FACE_FORE))
//				{
//					this.setShip1Facing(true);
//				}
//				else if (this.getShip1FacingIntent().equals(FacingIntent.FACE_AFT))
//				{
//					this.setShip1Facing(false);
//				}
//				else
//				{
//					// Doesn't care; retain previous facing
//				}
//			}
//			else
//			{
//				throw new RuntimeException("Unrecognized Intent: " + this.getShip1DistanceIntent());
//			}
//		}
//		else
//		{
//			throw new RuntimeException("Error in distance logic");
//		}
//
//		// --------- Ship2 ---------
//		if (this.getDistance().compareTo(Distance.MEDIUM) < 0) // Distance is Short or Point Blank
//		{
//			// Winner of the pilot contest chooses their Facing to the loser, and loser's Facing to themself
//			// Tie results in Facing of both determined randomly.
//			if (pilot2Roll > pilot1Roll) // Ship2 wins
//			{
//				// Set ship2 facing toward the other ship
//				if (this.getShip2FacingIntent().equals(FacingIntent.FACE_FORE))
//				{
//					// Ship2 wants to face toward ship1
//					this.setShip2Facing(true);
//				}
//				else if (this.getShip2FacingIntent().equals(FacingIntent.FACE_AFT))
//				{
//					// Ship2 wants to face away from ship1
//					this.setShip2Facing(false);
//				}
//				else
//				{
//					// Ship2 does not care; determine randomly
//					this.setShip2Facing(Dice.flipACoin());
//				}
//			}
//			else if (pilot1Roll > pilot2Roll) // Ship1 wins
//			{
//				if (this.getShip1FacingThemIntent().equals(FacingIntent.FACE_FORE))
//				{
//					// Ship1 wants ship2 to face toward ship1
//					this.setShip2Facing(true);
//				}
//				else if (this.getShip1FacingThemIntent().equals(FacingIntent.FACE_AFT))
//				{
//					// Ship1 wants ship2 to face away from ship1
//					this.setShip2Facing(false);
//				}
//				else
//				{
//					// Ship1 does not care; determine randomly
//					this.setShip2Facing(Dice.flipACoin());
//				}
//			}
//			else // Tie; determine randomly
//			{
//				this.setShip2Facing(Dice.flipACoin());
//			}
//		}
//		else if (this.getDistance().compareTo(Distance.SHORT) > 0) // Distance is Medium or longer
//		{
//			if (this.getShip2DistanceIntent().equals(DistanceIntent.MOVE_CLOSER))
//			{
//				this.setShip2Facing(true);
//			}
//			else if (this.getShip2DistanceIntent().equals(DistanceIntent.MOVE_FARTHER))
//			{
//				this.setShip2Facing(false);
//			}
//			else if (this.getShip2DistanceIntent().equals(DistanceIntent.STATUS_QUO))
//			{
//				if (this.getShip2FacingIntent().equals(FacingIntent.FACE_FORE))
//				{
//					this.setShip2Facing(true);
//				}
//				else if (this.getShip2FacingIntent().equals(FacingIntent.FACE_AFT))
//				{
//					this.setShip2Facing(false);
//				}
//				else
//				{
//					// Doesn't care; retain previous facing
//				}
//			}
//			else
//			{
//				throw new RuntimeException("Unrecognized Intent: " + this.getShip2DistanceIntent());
//			}
//		}
//		else
//		{
//			throw new RuntimeException("Error in distance logic");
//		}
//	}
	
	public static DistanceIntent getIntentFor(Distance preferredDistance, Distance currentDistance)
	{
		int delta = preferredDistance.compareTo(currentDistance);
		DistanceIntent intent = null;
		if (delta < 0) intent = DistanceIntent.MOVE_CLOSER;
		else if (delta == 0) intent = DistanceIntent.STATUS_QUO;
		else if (delta > 0) intent = DistanceIntent.MOVE_FARTHER;
		assert intent != null;
		return intent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.ship1 == null) ? 0 : this.ship1.hashCode());
		result = prime * result + ((this.ship2 == null) ? 0 : this.ship2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) return true;
		if (that == null) return false;
		if (getClass() != that.getClass()) return false;
		ShipToShipRelation thatS2SR = (ShipToShipRelation) that;
		// Objects are equal if both ships are in both
		return (this.contains(thatS2SR.getShip1()) && this.contains(thatS2SR.getShip2()));
	}

	/**
	 * <pre>{@code
	 * Left side ship: 
	 * » intends to close 
	 * - intends to keep status quo 
	 * « intends to escape
	 * >ShipName> facing other ship
	 * <ShipName< facing away 
	 * Distance: One dot for every Distance range (Low/Medium/Long/Extreme/Beyond Extreme)
	 * Zero dots indicates Point Blank range
	 * Right side ship: 
	 * « intends to close 
	 * - intends to keep status quo
	 * » intends to escape
	 * <ShipName< facing other ship
	 * >ShipName> facing away
	 * Example: Right side ship chasing Left side ship at Medium range
	 * [»>ChasingShip>]...[>RunningShip>»]
	 * Example: Both ships on collision course at Short range
	 * at Extreme range [»>LeftShip>].[<RightShip<«]
	 * Example: Both ships facing each other at Point Blank range, but intending to run away
	 * [«>LeftShip>][<RightShip<»]
	 * }</pre>
	 */
	@Override
	public String toString()
	{
//		String s1Intent = "";
//		switch (this.ship1DistanceIntent)
//		{
//		case MOVE_CLOSER:
//			s1Intent = "»";
//			break;
//		case STATUS_QUO:
//			s1Intent = "-";
//			break;
//		case MOVE_FARTHER:
//			s1Intent = "«";
//			break;
//		}
//		String s1 = s1Intent + (this.ship1Facing ? ">" : "<") + this.ship1.getName() + (this.ship1Facing ? ">" : "<");
//		String s2Intent = "";
//		switch (this.ship2DistanceIntent)
//		{
//		case MOVE_CLOSER:
//			s2Intent = "«";
//			break;
//		case STATUS_QUO:
//			s2Intent = "-";
//			break;
//		case MOVE_FARTHER:
//			s2Intent = "»";
//			break;
//		}
//		String s2 = (this.ship2Facing ? "<" : ">") + this.ship2.getName() + (this.ship2Facing ? "<" : ">") + s2Intent;
		StringBuilder sb = new StringBuilder();
		for (int ii = 0; ii < this.distance.getDistance(); ii++)	sb.append(".");
//		return "ShipToShipRelation[" + s1 + "]" + sb + "[" + s2 + "]";
		return "ShipToShipRelation[" + this.ship1.getName() + "]" + sb + "[" + this.ship2.getName() + "]";
	}

	// Constructors
	
	public ShipToShipRelation(Ship ship1, Ship ship2) throws InvalidParameterException
	{
		if (ship1 == null || ship2 == null)
		{
			throw new InvalidParameterException("ship cannot be null");
		}
		if (ship1 == ship2)
		{
			throw new InvalidParameterException("Ship cannot have a relation with itself");
		}
		if (ship1.getFaction().equals(ship2.getFaction()))
		{
			throw new InvalidParameterException("No need for this between friends");
		}

		// A little jiu-jitsu here to make the Player side display on the left
		if (ship2.getFaction().equals(Ship.Faction.PLAYER))
		{
			this.ship1 = ship2;
			this.ship2 = ship1;
		}
		else
		{
			this.ship1 = ship1;
			this.ship2 = ship2;
		}
		
		// Default distance is Beyond Extreme
		this.distance = Distance.BEYOND_EXTREME;
		
//		// By default both ships are facing each other and intending to close distance
//		this.ship1Facing = true;
//		this.ship2Facing = true;
//		this.ship1DistanceIntent = DistanceIntent.MOVE_CLOSER;
//		this.ship2DistanceIntent = DistanceIntent.MOVE_CLOSER;
		
//		// By default both ships don't care about which way they face
//		this.ship1FacingIntent = FacingIntent.DONT_CARE;
//		this.ship2FacingIntent = FacingIntent.DONT_CARE;
//		
//		// By default both ships don't care about which way their opponent faces them
//		this.ship1FacingThemIntent = FacingIntent.DONT_CARE;
//		this.ship2FacingThemIntent = FacingIntent.DONT_CARE;
	}

	// Getters & Setters

	public Ship getShip1()
	{
		return this.ship1;
	}

	public void setShip1(Ship ship1)
	{
		this.ship1 = ship1;
	}

	public Ship getShip2()
	{
		return this.ship2;
	}

	public void setShip2(Ship ship2)
	{
		this.ship2 = ship2;
	}

	public Distance getDistance()
	{
		return this.distance;
	}

	public void setDistance(Distance distance)
	{
		this.distance = distance;
	}

//	/**
//	 * Is ship1 facing toward ship2?
//	 * @return whether ship1 is facing ship2
//	 */
//	public boolean isShip1Facing()
//	{
//		return this.ship1Facing;
//	}
//
//	/**
//	 * Set whether ship1 is facing toward ship2
//	 * @param ship1Facing is ship1 facing ship2?
//	 */
//	public void setShip1Facing(boolean ship1Facing)
//	{
//		this.ship1Facing = ship1Facing;
//	}
//
//	/**
//	 * Is ship2 facing toward ship1?
//	 * @return whether ship2 is facing ship1
//	 */
//	public boolean isShip2Facing()
//	{
//		return this.ship2Facing;
//	}
//
//	/**
//	 * Set whether ship2 is facing toward ship1
//	 * @param ship2Facing is ship2 facing ship1?
//	 */
//	public void setShip2Facing(boolean ship2Facing)
//	{
//		this.ship2Facing = ship2Facing;
//	}

	public DistanceIntent getShip1DistanceIntent()
	{
		return this.ship1DistanceIntent;
	}

	public void setShip1DistanceIntent(DistanceIntent ship1Intent)
	{
		this.ship1DistanceIntent = ship1Intent;
	}

	public DistanceIntent getShip2DistanceIntent()
	{
		return this.ship2DistanceIntent;
	}

	public void setShip2DistanceIntent(DistanceIntent ship2Intent)
	{
		this.ship2DistanceIntent = ship2Intent;
	}

//	public FacingIntent getShip1FacingIntent()
//	{
//		return this.ship1FacingIntent;
//	}
//
//	public void setShip1FacingIntent(FacingIntent ship1FacingIntent)
//	{
//		this.ship1FacingIntent = ship1FacingIntent;
//	}
//
//	public FacingIntent getShip2FacingIntent()
//	{
//		return this.ship2FacingIntent;
//	}
//
//	public void setShip2FacingIntent(FacingIntent ship2FacingIntent)
//	{
//		this.ship2FacingIntent = ship2FacingIntent;
//	}
//
//	public FacingIntent getShip1FacingThemIntent()
//	{
//		return this.ship1FacingThemIntent;
//	}
//
//	public void setShip1FacingThemIntent(FacingIntent ship1FacingThemIntent)
//	{
//		this.ship1FacingThemIntent = ship1FacingThemIntent;
//	}
//
//	public FacingIntent getShip2FacingThemIntent()
//	{
//		return this.ship2FacingThemIntent;
//	}
//
//	public void setShip2FacingThemIntent(FacingIntent ship2FacingThemIntent)
//	{
//		this.ship2FacingThemIntent = ship2FacingThemIntent;
//	}
}
