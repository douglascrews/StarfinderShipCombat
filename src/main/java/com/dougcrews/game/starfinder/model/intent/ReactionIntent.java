package com.dougcrews.game.starfinder.model.intent;

import com.dougcrews.game.starfinder.model.Player;

public abstract class ReactionIntent extends Intent
{
	protected Player player;

	@Override
	public String toString()
	{
		return super.toString() + ":" + this.player.getName();
	}

	// Constructors
	
	public ReactionIntent()
	{
		super(ActionType.REACTION);
	}

	// Getters & Setters
	
	public Player getPlayer()
	{
		return this.player;
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}
}
