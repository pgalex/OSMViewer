package com.osmviewer.map;

/**
 * Handler of map loading results
 *
 * @author pgalex
 */
public interface MapLoadingHandler
{
	/**
	 * All parts of map finished loading
	 *
	 */
	public void wholeMapLoaded();

	/**
	 * Part of map finished loading
	 *
	 */
	public void partOfMapLoaded();
}
