package com.dougcrews.game.starfinder.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistanceTest
{

	@BeforeEach
	void setUp() throws Exception
	{
	}

	@AfterEach
	void tearDown() throws Exception
	{
	}

	@Test
	void testGetDistancePenalty()
	{
		assertEquals(0, Distance.POINT_BLANK.getDistancePenalty());
		assertEquals(-2, Distance.SHORT.getDistancePenalty());
		assertEquals(-4, Distance.MEDIUM.getDistancePenalty());
		assertEquals(-6, Distance.LONG.getDistancePenalty());
		assertEquals(-8, Distance.EXTREME.getDistancePenalty());
		assertEquals(-10, Distance.BEYOND_EXTREME.getDistancePenalty());
	}

	@Test
	void testIncrement()
	{
		assertEquals(Distance.SHORT, Distance.POINT_BLANK.increment());
		assertEquals(Distance.MEDIUM, Distance.SHORT.increment());
		assertEquals(Distance.LONG, Distance.MEDIUM.increment());
		assertEquals(Distance.EXTREME, Distance.LONG.increment());
		assertEquals(Distance.BEYOND_EXTREME, Distance.EXTREME.increment());
		assertEquals(Distance.BEYOND_EXTREME, Distance.BEYOND_EXTREME.increment());
	}

	@Test
	void testDecrement()
	{
		assertEquals(Distance.EXTREME, Distance.BEYOND_EXTREME.decrement());
		assertEquals(Distance.LONG, Distance.EXTREME.decrement());
		assertEquals(Distance.MEDIUM, Distance.LONG.decrement());
		assertEquals(Distance.SHORT, Distance.MEDIUM.decrement());
		assertEquals(Distance.POINT_BLANK, Distance.SHORT.decrement());
		assertEquals(Distance.POINT_BLANK, Distance.POINT_BLANK.decrement());
	}

	@Test
	void testMax()
	{
		assertEquals(Distance.SHORT, Distance.max(Distance.POINT_BLANK, Distance.SHORT));
		assertEquals(Distance.SHORT, Distance.max(Distance.SHORT, Distance.SHORT));
		assertEquals(Distance.SHORT, Distance.max(Distance.SHORT, Distance.SHORT));
		assertEquals(Distance.EXTREME, Distance.max(Distance.POINT_BLANK, Distance.EXTREME));
		assertEquals(Distance.BEYOND_EXTREME, Distance.max(Distance.BEYOND_EXTREME, Distance.EXTREME));
	}

	@Test
	void testMin()
	{
		assertEquals(Distance.EXTREME, Distance.min(Distance.BEYOND_EXTREME, Distance.EXTREME));
		assertEquals(Distance.SHORT, Distance.min(Distance.SHORT, Distance.EXTREME));
		assertEquals(Distance.EXTREME, Distance.min(Distance.EXTREME, Distance.EXTREME));
		assertEquals(Distance.SHORT, Distance.min(Distance.BEYOND_EXTREME, Distance.SHORT));
		assertEquals(Distance.POINT_BLANK, Distance.min(Distance.SHORT, Distance.POINT_BLANK));
	}

}
