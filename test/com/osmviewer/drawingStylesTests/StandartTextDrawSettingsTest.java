package com.osmviewer.drawingStylesTests;

import com.osmviewer.IOTesting.IOTester;
import com.osmviewer.drawingStyles.StandartTextDrawSettings;
import java.awt.Color;
import java.awt.Font;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * StandartTextDrawSettings class tests
 * @author pgalex
 */
public class StandartTextDrawSettingsTest
{
	/**
	 * Test setting null text color
	 */
	@Test
	public void setNullTextColorTest()
	{
		try
		{
			StandartTextDrawSettings settings = new StandartTextDrawSettings();
			settings.setColor(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null text font
	 */
	@Test
	public void setNullTextFontTest()
	{
		try
		{
			StandartTextDrawSettings settings = new StandartTextDrawSettings();
			settings.setFont(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with null font
	 */
	@Test
	public void creatingWithNullFontTest()
	{
		try
		{
			StandartTextDrawSettings settings = new StandartTextDrawSettings(Color.CYAN, null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating with null text color text
	 */
	@Test
	public void creatingWithNullTextColorTest()
	{
		try
		{
			StandartTextDrawSettings settings = new StandartTextDrawSettings(null, null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	
}
