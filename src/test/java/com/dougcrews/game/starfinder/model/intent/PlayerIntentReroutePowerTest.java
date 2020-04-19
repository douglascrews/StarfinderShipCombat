package com.dougcrews.game.starfinder.model.intent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.ship.DetectedShip;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;
import com.dougcrews.game.starfinder.model.ship.component.ShipComponent;

class PlayerIntentReroutePowerTest
{
	private Ship ship;
	private ShipComponent component1;
	private ShipComponent component2;
	
	@BeforeEach
	void setUp() throws Exception
	{
		this.ship = new Ship(Tier.ONE, Frame.FIGHTER);
		this.component1 = new ShipComponent(this.ship)
		{
			@Override
			public String getGenericName()
			{
				return "Unit Test Component 1";
			}

			@Override
			public String getDescription()
			{
				return "Unit Test Component";
			}

			@Override
			public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
			{
				Set<PlayerIntent> intents = super.getDefaultIntents(targetShip);
				return intents;
			}

			@Override
			public Set<ReactionIntent> getPossibleReactions()
			{
				Set<ReactionIntent> reactions = super.getDefaultReactions();
				return reactions;
			}

			@Override
			public int getComponentBonus()
			{
				return 0;
			}
		};
		this.component2 = new ShipComponent(this.ship)
		{
			@Override
			public String getGenericName()
			{
				return "Unit Test Component 2";
			}

			@Override
			public String getDescription()
			{
				return "Unit Test Component";
			}

			@Override
			public Set<PlayerIntent> getPossibleIntents(DetectedShip targetShip)
			{
				Set<PlayerIntent> intents = super.getDefaultIntents(targetShip);
				return intents;
			}

			@Override
			public Set<ReactionIntent> getPossibleReactions()
			{
				Set<ReactionIntent> reactions = super.getDefaultReactions();
				return reactions;
			}

			@Override
			public int getComponentBonus()
			{
				return 0;
			}
		};
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.ship = null;
		this.component1 = null;
		this.component2 = null;
	}

	@Test
	void testExecute()
	{
		this.component1.setEnabled(true);
		this.component2.setEnabled(false);
		
		assertTrue(this.component1.isEnabled());
		assertFalse(this.component2.isEnabled());
		PlayerIntentComponentReroutePower intent = new PlayerIntentComponentReroutePower(this.ship.getAICrewmember(), this.component1, this.component2);
		intent.execute();
		assertFalse(this.component1.isEnabled());
		assertTrue(this.component2.isEnabled());
	}

}
