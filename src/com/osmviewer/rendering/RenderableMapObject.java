package com.osmviewer.rendering;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;

/**
 * Map object that can be rendered
 *
 * @author pgalex
 */
public interface RenderableMapObject
{
	/**
	 * Determine draw prority using draw settings
	 *
	 * @return draw prority
	 * @throws NullPointerException draw settings not set
	 */
	public int determineDrawPriotity() throws NullPointerException;

	/**
	 * Get defenition tags
	 *
	 * @return defenition tags
	 */
	public DefenitionTags getDefenitionTags();

	/**
	 * Get draw settings
	 *
	 * @return draw settings of map object. Null if not defined
	 */
	public RenderableMapObjectDrawSettings getDrawSettings();

	/**
	 * Accept rendering visitor
	 *
	 * @param renderingVisitor renderable map objects visitor to accept
	 * @throws IllegalArgumentException renderingVisitor is null
	 */
	public void acceptRenderingVisitor(RenderableMapObjectsVisitor renderingVisitor) throws IllegalArgumentException;
}
