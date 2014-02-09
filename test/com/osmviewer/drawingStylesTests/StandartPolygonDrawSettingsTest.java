package com.osmviewer.drawingStylesTests;

import com.osmviewer.IOTesting.IOTester;
import com.osmviewer.drawingStyles.ImageWithIO;
import com.osmviewer.drawingStyles.PolygonFiller;
import com.osmviewer.drawingStyles.PolygonFillersFactory;
import com.osmviewer.drawingStyles.StandartLineDrawSettings;
import com.osmviewer.drawingStyles.StandartPolygonDrawSettings;
import java.awt.Color;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * StandartPolygonDrawSettings class tests
 *
 * @author abc
 */
public class StandartPolygonDrawSettingsTest
{
	/**
	 * Test setting null border draw settings
	 */
	@Test
	public void setNullBorderDrawSettingsTest()
	{
		try
		{
			StandartPolygonDrawSettings testStyle = new StandartPolygonDrawSettings();
			testStyle.setBorderDrawSettings(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null filler
	 */
	@Test
	public void setNullFillerTest()
	{
		try
		{
			StandartPolygonDrawSettings testStyle = new StandartPolygonDrawSettings();
			testStyle.setFiller(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating texture filler
	 */
	@Test
	public void creatingTexturePaintTest()
	{
		try
		{
			ImageWithIO fillIcon = new ImageWithIO("test/supportFiles/testIcon.png");
			PolygonFiller filler = PolygonFillersFactory.createTextureFiller(fillIcon.getImage());
			StandartPolygonDrawSettings polygonStyle = new StandartPolygonDrawSettings();
			polygonStyle.setFiller(filler);
			Paint paint = polygonStyle.getPaint();
			
			assertTrue(paint instanceof TexturePaint);
		}
		catch (IOException ex)
		{
			fail();
		}
	}

	/**
	 * Test creating color paint
	 */
	@Test
	public void creatingSolidColorPaintTest()
	{
		PolygonFiller filler = PolygonFillersFactory.createColorFiller(Color.GREEN);
		StandartPolygonDrawSettings polygonStyle = new StandartPolygonDrawSettings();
		polygonStyle.setFiller(filler);
		Paint paint = polygonStyle.getPaint();
		
		assertTrue(paint instanceof Color);
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			float[] pattern = new float[4];
			pattern[0] = 2;
			pattern[1] = 3;
			pattern[2] = 4;
			pattern[3] = 5;
			StandartLineDrawSettings borderStyle = new StandartLineDrawSettings();
			borderStyle.setColor(Color.CYAN);
			borderStyle.setWidth(10);
			borderStyle.setPattern(pattern);
			
			ImageWithIO fillIcon = new ImageWithIO("test/supportFiles/testIcon.png");
			PolygonFiller filler = PolygonFillersFactory.createTextureFiller(fillIcon.getImage());
			StandartPolygonDrawSettings writedStyle = new StandartPolygonDrawSettings();
			writedStyle.setFiller(filler);
			writedStyle.setBorderDrawSettings(borderStyle);
			writedStyle.setNotDrawBorder();
			writedStyle.setDrawInnerPart();
			
			writedStyle.writeToStream(IOTester.createTestOutputStream());
			
			StandartPolygonDrawSettings readStyle = new StandartPolygonDrawSettings();
			readStyle.readFromStream(IOTester.createTestInputStream());
			
			assertEquals(writedStyle.getFiller().getType(), readStyle.getFiller().getType());
			assertEquals(writedStyle.getBorderDrawSettings().getColor(), readStyle.getBorderDrawSettings().getColor());
			assertEquals(writedStyle.getBorderDrawSettings().getWidth(), readStyle.getBorderDrawSettings().getWidth(), 0.00001f);
			assertEquals(writedStyle.isDrawBorder(), readStyle.isDrawBorder());
			assertEquals(writedStyle.isDrawInnerPart(), readStyle.isDrawInnerPart());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
