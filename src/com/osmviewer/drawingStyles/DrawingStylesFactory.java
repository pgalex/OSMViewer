package com.osmviewer.drawingStyles;

/**
 * Using for creating DrawSettingsViewer
 *
 * @author pgalex
 */
public class DrawingStylesFactory
{
	/**
	 * Create standart draw settings viewer
	 *
	 * @return standart draw settings viewer
	 */
	static public DrawSettingsViewer createStandartDrawSettingsViewer()
	{
		return new StandartDrawSettingsContainer();
	}
}
