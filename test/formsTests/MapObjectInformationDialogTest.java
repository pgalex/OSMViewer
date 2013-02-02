package formsTests;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import forms.MapObjectInformationDialog;
import java.awt.Dialog;
import map.MapPoint;
import map.MapPosition;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests of MapObjectInformationDialog class
 * @author pgalex
 */
public class MapObjectInformationDialogTest
{
	/**
	 * Test showing information about null map object
	 */
	@Test
	public void showingInformationNullMapObject()
	{
		try
		{
			MapObjectInformationDialog dialog = new MapObjectInformationDialog(null, Dialog.ModalityType.MODELESS);
			MapObjectDrawSettings someDrawSettings = new MapObjectDrawSettings();
			dialog.showMapObjectInformation(null, someDrawSettings);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test showing information with null map object draw settings
	 */
	@Test
	public void showingInformationNullDrawSettings()
	{
		try
		{
			MapObjectInformationDialog dialog = new MapObjectInformationDialog(null, Dialog.ModalityType.MODELESS);
			MapPoint someMapObject = new MapPoint(new MapPosition(), 0, new DefenitionTags());
			MapObjectDrawSettings someDrawSettings = new MapObjectDrawSettings();
			dialog.showMapObjectInformation(someMapObject, null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}
