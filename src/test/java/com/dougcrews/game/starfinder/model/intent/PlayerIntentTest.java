package com.dougcrews.game.starfinder.model.intent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dougcrews.game.starfinder.model.Player;
import com.dougcrews.game.starfinder.model.ship.Ship;
import com.dougcrews.game.starfinder.model.ship.Ship.Frame;
import com.dougcrews.game.starfinder.model.ship.Ship.Tier;

class PlayerIntentTest
{
	private Player player;
	private PlayerIntent genericMoveIntent;
	private PlayerIntent genericStandardIntent;
	private PlayerIntent genericReactionIntent;
	
	@BeforeEach
	void setUp() throws Exception
	{
		this.player = new Player("Joe UnitTest", new Ship(Tier.ONE, Frame.FIGHTER));
		this.genericMoveIntent = new PlayerIntent(ActionType.MOVE, this.player)
		{
			@Override
			public void execute() {}

			@Override
			protected String getName()
			{
				return "Unit Test Move PlayerIntent";
			}
		};
		this.genericStandardIntent = new PlayerIntent(ActionType.STANDARD, this.player)
		{
			@Override
			public void execute() {}

			@Override
			protected String getName()
			{
				return "Unit Test Standard PlayerIntent";
			}
		};
		this.genericReactionIntent = new PlayerIntent(ActionType.REACTION, this.player)
		{
			@Override
			public void execute() {}

			@Override
			protected String getName()
			{
				return "Unit Test ReactionIntent";
			}
		};
	}

	@AfterEach
	void tearDown() throws Exception
	{
		this.player = null;
		this.genericMoveIntent = null;
		this.genericStandardIntent = null;
		this.genericReactionIntent = null;
	}

	@Test
	void testDefaultPlayerIntents()
	{
		this.player.resetActionsThisRound();
		assertEquals(3, this.player.getIntents().size());
		assertTrue(this.player.getIntents().remove(new PlayerIntentNoAction(this.player, ActionType.MOVE)));
		assertTrue(this.player.getIntents().remove(new PlayerIntentNoAction(this.player, ActionType.STANDARD)));
		assertTrue(this.player.getIntents().remove(new PlayerIntentNoAction(this.player, ActionType.REACTION)));
		assertEquals(0, this.player.getIntents().size());
	}

	@Test
	void testFullRoundPlayerIntents()
	{
		this.player.resetActionsThisRound();
		PlayerIntent fullRoundIntent = new PlayerIntentFullRoundAction(this.player);
		try
		{
			this.player.pushIntent(fullRoundIntent);
		} 
		catch (ActionNotAvailableException e)
		{
			fail(e.toString());
		}
		assertEquals(4, this.player.getIntents().size());
		assertEquals(fullRoundIntent, this.player.popNextIntent());
		assertEquals(new PlayerIntentNoAction(this.player, ActionType.MOVE), this.player.popNextIntent());
		assertEquals(new PlayerIntentNoAction(this.player, ActionType.STANDARD), this.player.popNextIntent());
		assertEquals(new PlayerIntentNoAction(this.player, ActionType.STANDARD), this.player.popNextIntent());
		assertEquals(0, this.player.getIntents().size());
	}

	@Test
	void testAddIntent()
	{
		this.player.resetActionsThisRound();
		try
		{
			this.player.pushIntent(this.genericMoveIntent);
			this.player.pushIntent(this.genericStandardIntent);
		}
		catch (ActionNotAvailableException e)
		{
			fail(e.toString());
		}
		assertEquals(3, this.player.getIntents().size());
		assertEquals(new PlayerIntentNoAction(this.player, ActionType.REACTION), this.player.popNextIntent());
		assertEquals(this.genericMoveIntent, this.player.popNextIntent());
		assertEquals(this.genericStandardIntent, this.player.popNextIntent());
		assertEquals(0, this.player.getIntents().size());
		
		this.player.resetActionsThisRound();
		try
		{
			this.player.pushIntent(this.genericMoveIntent);
			this.player.pushIntent(this.genericReactionIntent);
			this.player.pushIntent(this.genericMoveIntent);
		}
		catch (ActionNotAvailableException e)
		{
			fail(e.toString());
		}
		assertEquals(3, this.player.getIntents().size());
		assertEquals(this.genericMoveIntent, this.player.popNextIntent());
		assertEquals(this.genericReactionIntent, this.player.popNextIntent());
		assertEquals(this.genericMoveIntent, this.player.popNextIntent());
		assertEquals(0, this.player.getIntents().size());
		
		this.player.resetActionsThisRound();
		try
		{
			this.player.pushIntent(this.genericStandardIntent);
			this.player.pushIntent(this.genericMoveIntent);
			this.player.pushIntent(this.genericStandardIntent);
			fail("Expected ActionNotAvailableException");
		}
		catch (@SuppressWarnings("unused") ActionNotAvailableException e)
		{
			// Expected; Ignore
		}
		
		this.player.resetActionsThisRound();
		try
		{
			this.player.pushIntent(this.genericReactionIntent);
			this.player.pushIntent(this.genericReactionIntent);
			fail("Expected ActionNotAvailableException");
		}
		catch (@SuppressWarnings("unused") ActionNotAvailableException e)
		{
			// Expected; Ignore
		}
		
		this.player.resetActionsThisRound();
		try
		{
			this.player.removeIntent(this.player.getIntents().get(2));
			this.player.pushIntent(this.genericReactionIntent);
			this.player.pushIntent(this.genericReactionIntent);
			fail("Expected ActionNotAvailableException");
		}
		catch (@SuppressWarnings("unused") ActionNotAvailableException e)
		{
			// Expected; Ignore
		}
		catch (IntentNotFoundException e)
		{
			fail(e.toString());
		}
	}
}
