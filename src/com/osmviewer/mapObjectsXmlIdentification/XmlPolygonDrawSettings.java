package com.osmviewer.mapObjectsXmlIdentification;

import com.osmviewer.rendering.drawingIdBasedRenderers.PolygonDrawSettings;
import java.awt.Color;

/**
 *
 * @author preobrazhentsev
 */
public class XmlPolygonDrawSettings implements PolygonDrawSettings
{

	@Override
	public Color getFillColor()
	{
		return Color.MAGENTA;
	}

}
