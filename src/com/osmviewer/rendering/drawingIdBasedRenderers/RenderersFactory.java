package com.osmviewer.rendering.drawingIdBasedRenderers;

/**
 * Factory of renderers for each renderer type that can be determined by drawing
 * id
 *
 * @author preobrazhentsev
 */
public class RenderersFactory
{
	/**
	 * Create renderer by type
	 *
	 * @param rendererType type of renderer. Must be not null
	 * @return renderer, created by drawing id. Null if can not create
	 * @throws IllegalArgumentException rendererType is null
	 */
	public static DrawingIdBasedRenderer createRenderer(String rendererType) throws IllegalArgumentException
	{
		if (rendererType == null)
		{
			throw new IllegalArgumentException("rendererName is null");
		}

		if ("polygon".equals(rendererType))
		{
			return new PolygonRenderer();
		}

		return null;
	}
}
