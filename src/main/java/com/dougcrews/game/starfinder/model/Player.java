package com.dougcrews.game.starfinder.model;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.intent.ActionNotAvailableException;
import com.dougcrews.game.starfinder.model.intent.ActionType;
import com.dougcrews.game.starfinder.model.intent.IntentNotFoundException;
import com.dougcrews.game.starfinder.model.intent.PlayerIntent;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentFullRoundAction;
import com.dougcrews.game.starfinder.model.intent.PlayerIntentNoAction;
import com.dougcrews.game.starfinder.model.intent.ReactionIntent;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;

public class Player {
	static final Logger log = LogManager.getLogger(Player.class.getName());

	private String name = "Anonymous Player";
	private Ship ship;
	private ShipComponent currentConsole;
	private boolean rollForMe = true;
	private List<PlayerIntent> actionsThisRound = new LinkedList<PlayerIntent>();
	private int multitaskPenalty = 0;
	//	private int calledShotPenalty = 0; // this is baked in to the ShipComponent.fire() logic

	private int pilotBonus = 0;
	private int gunneryBonus = 0;
	private int engineeringBonus = 0;
	private int computersBonus = 0;
	private int diplomacyBonus = 0;
	private int intimidateBonus = 0;
	
	private Queue<Integer> d20Rolls = new LinkedList<Integer>();
	
	public int rollPilotSkill()
	{
		int d20 = this.d20();
		int pilotBonus = this.getPilotBonus();
		int totalRoll = d20 + pilotBonus;
		log.info(this.getName() + " rolls Pilot: (" + d20 + ") + " + pilotBonus + " = " + totalRoll);
		return totalRoll;
	}

	public List<PlayerIntent> getIntents()
	{
		return this.actionsThisRound;
	}
	
	public void resetActionsThisRound()
	{
		this.actionsThisRound.clear();
		this.actionsThisRound.add(new PlayerIntentNoAction(this, ActionType.MOVE));
		this.actionsThisRound.add(new PlayerIntentNoAction(this, ActionType.STANDARD));
		this.actionsThisRound.add(new PlayerIntentNoAction(this, ActionType.REACTION));
		this.multitaskPenalty = 0;
	}
	
	/**
	 * Adds a Player Intent to the queue. They will be executed in the order they were added.
	 * @param intent PlayerIntent to be queued
	 * @throws ActionNotAvailableException if the Player lacks a free matching Action Type
	 */
	public void pushIntent(PlayerIntent intent) throws ActionNotAvailableException
	{
		if (intent instanceof PlayerIntentFullRoundAction)
		{
			// Full Round actions take the place of any actions already queued.
			this.actionsThisRound.clear();
			this.actionsThisRound.add(intent);
			this.actionsThisRound.add(new PlayerIntentNoAction(this, ActionType.MOVE));
			this.actionsThisRound.add(new PlayerIntentNoAction(this, ActionType.STANDARD));
			this.actionsThisRound.add(new PlayerIntentNoAction(this, ActionType.STANDARD));
		}
		else
		{
			PlayerIntent unusedIntent = this.getUnusedIntent(intent.getActionType());
			if (unusedIntent == null)
			{
				throw new ActionNotAvailableException("No " + intent.getActionType().getName() + " actions remaining this round!");
			}
			log.debug("Replacing unused intent " + unusedIntent + " with " + intent);
			this.actionsThisRound.remove(unusedIntent);
		
			// Full Round actions replace all existing actions 
			if (intent.getActionType().equals(ActionType.FULLROUND))
			{
				this.actionsThisRound.clear();
			}
			
			this.actionsThisRound.add(intent);
		}
	}
	
	/**
	 * The list of player intents stored is intended as a FIFO queue, but we need to be able
	 * to randomly delete from anywhere in the list. This returns the first PlayerIntent to be
	 * executed.
	 * It does not execute the intent.
	 * @return First PlayerIntent to be added to the queue, removing it from the queue
	 */
	public PlayerIntent popNextIntent()
	{
		PlayerIntent intent = null;
		if (this.actionsThisRound.size() > 0)
		{
			intent = this.actionsThisRound.remove(0);
		}
		return intent;
	}
	
	/**
	 * Remove the specified Player Intent from the queue, replacing it with a PlayerIntentNoAction intent of the
	 * matching ActionType. This is a maintenance operation only; to retrieve the next Intent queued, use
	 * #popIntent() instead.
	 * @param intent PlayerIntent to be removed
	 * @throws IntentNotFoundException if the Intent was not found
	 * @throws ActionNotAvailableException if the 
	 */
	public void removeIntent(PlayerIntent intent) throws IntentNotFoundException, ActionNotAvailableException
	{
		if (this.actionsThisRound.remove(intent))
		{
			// Replace with a placeholder.
			this.pushIntent(new PlayerIntentNoAction(intent.getPlayer(), intent.getActionType()));
		}
		else
		{
			throw new IntentNotFoundException("Intent not found: " + intent);
		}
	}

	/**
	 * Return the first PlayerIntentNoAction found of the ActionType specified.
	 * Note that a Move action can be substituted for a Standard action, but not the other way.
	 * @param actionType Action Type to look for
	 * @return first matching instance of an unspecified action of the correct type
	 */
	public PlayerIntent getUnusedIntent(ActionType actionType)
	{
		PlayerIntent unusedIntent = null;
		for (PlayerIntent intent : this.actionsThisRound)
		{
			if (intent instanceof PlayerIntentNoAction)
			{
				if (actionType.equals(ActionType.MOVE))
				{
					if (intent.getActionType().equals(ActionType.MOVE))
					{
						unusedIntent = intent;
					}
				}
				if (actionType.equals(ActionType.STANDARD))
				{
					if (intent.getActionType().equals(ActionType.STANDARD))
					{
						unusedIntent = intent;
					}
				}
				if (actionType.equals(ActionType.REACTION))
				{
					if (intent.getActionType().equals(ActionType.REACTION))
					{
						unusedIntent = intent;
					}
				}
				
				// Edge Case: A Standard can be used to fulfill a Move intent
				if (unusedIntent == null && actionType.equals(ActionType.MOVE) && intent.getActionType().equals(ActionType.STANDARD))
				{
					unusedIntent = intent;
				}
			}
		}
		return unusedIntent;
	}

	public Set<PlayerIntent> getDefaultIntents()
	{
		Set<PlayerIntent> intents = new HashSet<PlayerIntent>();
		intents.add(new PlayerIntentNoAction(this, ActionType.MOVE));
		intents.add(new PlayerIntentNoAction(this, ActionType.STANDARD));
		intents.add(new PlayerIntentNoAction(this, ActionType.FULLROUND));
		// TODO: IntentSwitchConsole
		// TODO: IntentReadPassiveSensors
		// TODO: IntentStrapInOut
		// TODO: IntentAssist
		// TODO: IntentMultitask
		return intents;
	}
	
	public Set<PlayerIntent> getPossibleIntents()
	{
		Set<PlayerIntent> intents = this.getDefaultIntents();
		if (this.ship.getCaptain().equals(this))
		{
			// TODO: IntentCalledTarget
			// TODO: IntentInspiringSpeech
			// TODO: IntentGiveMeMorePower
			// TODO: IntentINeedItNow
			// TODO: IntentGetEm
		}
		if (this.ship.getPilot().equals(this))
		{
			// TODO: IntentEvasiveManeuvers
			// TODO: IntentFlipItGood
			// TODO: IntentNeedForSpeed
		}
//		for (Ship ship : this.getShip().getDetectedShips())
		{
			// TODO: IntentFlyby
			// TODO: IntentRam
			// TODO: IntentBaitTheTrap
		}
		return intents;
	}

	public Set<ReactionIntent> getPossibleReactions()
	{
		Set<ReactionIntent> intents = new HashSet<ReactionIntent>();
		for (Missile missile : this.ship.getDetectedMissiles())
		{
			if (missile.getTarget().equals(this.ship) || this.ship.getComponents().contains(missile.getTarget()))
			{
				// TODO: IntentEvadeMissile
			}
		}
		return intents;
	}

	public boolean isFinalized()
	{
		return this.ship.getController().isPlayerFinalized(this);
	}
	
	/**
	 * Returns a random value between 1 and 20.
	 * If {@link #isRollForMe()} is set, it will always be rolled randomly.
	 * If not, and there are saved dice rolls (via {@link #pushD20(int)}, it
	 * will return the next one. When there are no more saved dice rolls,
	 * the system will return random values. 
	 * @return random value from 1 to 20
	 */
	public int d20()
	{
		if (this.rollForMe)
		{
			return Dice.d(20);
		}
		return this.popD20();
	}
	
	public void pushD20(int dieRoll) // Yay!, Autoboxing!
	{
		this.d20Rolls.add(dieRoll);
	}
	
	public int popD20()
	{
		Integer d20 = this.d20Rolls.poll();
		return (d20 == null ? Dice.d(20) : d20.intValue());
	}
	
	@Override
	public String toString() {
		return this.name + " (controlling " + (this.currentConsole == null ? "nothing" : this.currentConsole.getName()) + " on " + this.ship.getName() + ")";
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.currentConsole == null) ? 0 : this.currentConsole.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		result = prime * result + ((this.ship == null) ? 0 : this.ship.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object that)
	{
		if (this == that) return true;
		if (that == null) return false;
		if (getClass() != that.getClass()) return false;
		Player thatPlayer = (Player) that;
		
		if (this.currentConsole == null)
		{
			if (thatPlayer.currentConsole != null) return false;
		} 
		else if (! this.currentConsole.equals(thatPlayer.currentConsole)) return false;
		
		if (this.name == null)
		{
			if (thatPlayer.name != null) return false;
		} 
		else if (! this.name.equals(thatPlayer.name)) return false;
		
		if (this.ship == null)
		{
			if (thatPlayer.ship != null) return false;
		}
		else if (! this.ship.equals(thatPlayer.ship)) return false;
		
		return true;
	}

	// Constructors
	
	public Player(String name, Ship ship) throws InvalidParameterException
	{
		this.name = (name != null && name.length() > 0 ? name : "Anonymous Player");
		assert ship != null;
		this.ship = ship;
	}

	public Player(String name, Ship ship, int pilotBonus, int gunneryBonus, int engineeringBonus, int computersBonus, int diplomacyBonus, int intimidateBonus)
	{
		this(name, ship);
		this.pilotBonus = pilotBonus;
		this.gunneryBonus = gunneryBonus;
		this.engineeringBonus = engineeringBonus;
		this.computersBonus = computersBonus;
		this.diplomacyBonus = diplomacyBonus;
		this.intimidateBonus = intimidateBonus;
	}
	
	// Getters & Setters

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Ship getShip() {
		return this.ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public ShipComponent getCurrentConsole() {
		return this.currentConsole;
	}

	public void setCurrentConsole(ShipComponent currentConsole) {
		this.currentConsole = currentConsole;
	}

	public boolean isRollForMe() {
		return this.rollForMe;
	}

	public void setRollForMe(boolean rollForMe) {
		this.rollForMe = rollForMe;
	}

	public int getMultitaskPenalty()
	{
		return this.multitaskPenalty;
	}

	public void setMultitaskPenalty(int multitaskPenalty)
	{
		this.multitaskPenalty = multitaskPenalty;
	}

//	public int getCalledShotPenalty()
//	{
//		return this.calledShotPenalty;
//	}
//
//	public void setCalledShotPenalty(int calledShotPenalty)
//	{
//		this.calledShotPenalty = calledShotPenalty;
//	}

	public int getPilotBonus() {
		if (this.multitaskPenalty != 0)
		{
			log.info(this.getName() + " suffers a multitasking penalty to Piloting");
		}
		return this.pilotBonus + this.multitaskPenalty;
	}

	public void setPilotBonus(int pilotBonus) {
		this.pilotBonus = pilotBonus;
	}

	public int getGunneryBonus() {
		if (this.multitaskPenalty != 0)
		{
			log.info(this.getName() + " suffers a " + this.multitaskPenalty + " Multitasking penalty to Gunnery");
		}

//		if (this.calledShotPenalty != 0)
//		{
//			log.info(this.getName() + " suffers a " + this.calledShotPenalty + " Called Shot penalty to Gunnery");
//		}

		return this.gunneryBonus + this.multitaskPenalty;
	}

	public void setGunneryBonus(int gunneryBonus) {
		this.gunneryBonus = gunneryBonus;
	}

	public int getEngineeringBonus() {
		if (this.multitaskPenalty != 0)
		{
			log.info(this.getName() + " suffers a multitasking penalty to Engineering");
		}
		return this.engineeringBonus + this.multitaskPenalty;
	}

	public void setEngineeringBonus(int engineeringBonus) {
		this.engineeringBonus = engineeringBonus;
	}

	public int getComputersBonus() {
		return this.computersBonus;
	}

	public void setComputersBonus(int computersBonus) {
		if (this.multitaskPenalty != 0)
		{
			log.info(this.getName() + " suffers a multitasking penalty to Computers");
		}
		this.computersBonus = computersBonus + this.multitaskPenalty;
	}

	public int getDiplomacyBonus() {
		if (this.multitaskPenalty != 0)
		{
			log.info(this.getName() + " suffers a multitasking penalty to Diplomacy");
		}
		return this.diplomacyBonus + this.multitaskPenalty;
	}

	public void setDiplomacyBonus(int diplomacyBonus) {
		this.diplomacyBonus = diplomacyBonus;
	}

	public int getIntimidateBonus() {
		if (this.multitaskPenalty != 0)
		{
			log.info(this.getName() + " suffers a multitasking penalty to Intimidate");
		}
		return this.intimidateBonus + this.multitaskPenalty;
	}

	public void setIntimidateBonus(int intimidateBonus) {
		this.intimidateBonus = intimidateBonus;
	}
}
