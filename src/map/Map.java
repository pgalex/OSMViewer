package map;

import drawingStyle.StyleViewer;

/**
 * Interface of map - container of map objects
 *
 * @author pgalex
 */
public interface Map
{
	/**
	 * Sort all objects by draw priority
	 * 
	 * @param pStyleViewer Style viewer to find object draw priority
	 */
	public abstract void sortObjectByDrawPriority(StyleViewer pStyleViewer);
	
}
