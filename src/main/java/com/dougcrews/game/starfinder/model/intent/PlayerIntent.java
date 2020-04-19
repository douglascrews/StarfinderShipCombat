package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Player;

public abstract class PlayerIntent extends Intent
{
	@SuppressWarnings("unused")
	private static final Logger log = LogManager.getLogger(PlayerIntent.class.getName());

	protected final Player player;

	@Override
	public String toString()
	{
		return super.toString() + (this.player != null ? ":" + this.player.getName() : ""); 
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.actionType == null) ? 0 : this.actionType.hashCode());
		result = prime * result + ((this.player == null) ? 0 : this.player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object that)
	{
		if (this == that) return true;
		if (that == null) return false;
		if (getClass() != that.getClass()) return false;
		PlayerIntent thatIntent = (PlayerIntent) that;
		if (this.actionType != thatIntent.actionType) return false;
		if (this.player == null)
		{
			if (thatIntent.player != null) return false;
		} 
		else if (!this.player.equals(thatIntent.player)) return false;
		
		return true;
	}
	
	// Constructors

	public PlayerIntent(ActionType actionType, Player player)
	{
		super(actionType);
		this.player = player;
	}
	
	// Getters & Setters

	public Player getPlayer()
	{
		return this.player;
	}
}
