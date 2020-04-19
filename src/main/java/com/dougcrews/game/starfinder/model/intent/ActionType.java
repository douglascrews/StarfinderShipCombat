/**
 * 
 */
package com.dougcrews.game.starfinder.model.intent;

/**
 * Each round, each player and NPC is given one of:<ul>
 * <li> Standard + Move + Reaction
 * <li> Move + Move + Reaction
 * <li> Full Round = (Standard + Standard + Move
 */
public enum ActionType
{
	MOVE ("Move", "«M»"),
	STANDARD ("Standard", "«S»"),
	REACTION ("Reaction", "«R»"),
	FULLROUND ("Full Round", "«F»");
	
	private final String name;
	private final String badge;
	
	// Constructors
	
	private ActionType(String name, String badge)
	{
		this.name = name;
		this.badge = badge;
	}

	// Getters & Setters
	
	public String getName()
	{
		return this.name;
	}
	
	public String getBadge()
	{
		return this.badge;
	}
}
