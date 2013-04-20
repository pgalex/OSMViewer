package com.osmviewer.map;

/**
 * Handler of map loading results
 *
 * @author pgalex
 */
public interface MapLoadingHandler
{
	/**
	 * Part of map start loading
	 */
	public void partOfMapStartsLoading();

	/**
	 * All parts of map finished loading
	 *
	 */
	public void wholeMapFinishedLoading();

	/**
	 * Part of map finished loading
	 *
	 */
	public void partOfMapFinisheLoading();
}
