package com.dougcrews.game.starfinder.model.intent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.component.Flak;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;
import com.dougcrews.game.starfinder.model.ship.component.weapon.Missile;

public class PlayerIntentComponentFlak extends PlayerIntentComponentAbstract
{
	private static final Logger log = LogManager.getLogger(PlayerIntentComponentFlak.class.getName());

	private Missile missile;
	
	@Override
	public void execute()
	{
		assert (this.getComponent() instanceof Flak);
		
		log.info(this.getPlayer().getName() + " fires a Flak barrage at " + this.getMissile().getName());
		Flak flak = (Flak) this.getComponent();
		flak.fire(getMissile());
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ":" + this.missile.getName();
	}

	// Constructors
	
	public PlayerIntentComponentFlak(Player player, ShipComponent component, Missile missile)
	{
		super(ActionType.STANDARD, player, component);
		this.missile = missile;
	}
	
	// Getters & Setters

	public Missile getMissile()
	{
		return this.missile;
	}

	public void setMissile(Missile missile)
	{
		this.missile = missile;
	}

	@Override
	protected String getName()
	{
		return "Flak";
	}
}
