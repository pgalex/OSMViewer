package map;

import drawingStyle.MapObjectStyle;
import drawingStyle.StyleViewer;
import java.util.Comparator;
import map.exceptions.StyleViewerIsNullException;

/**
 * Comparator for sorting map objects by draw priority
 *
 * @author pgalex
 */
public class MapObjectDrawPriorityComparator implements Comparator<MapObject>
{
	/**
	 * Viewer for finding draw priority of objects
	 */
	private StyleViewer styleViewer;

	/**
	 * Constructor
	 *
	 * @param pStyleViewer Viewer for finding draw priority of objects
	 * @throws StyleViewerIsNullException style viewer is null
	 */
	public MapObjectDrawPriorityComparator(StyleViewer pStyleViewer) throws StyleViewerIsNullException
	{
		if (pStyleViewer == null)
			throw new StyleViewerIsNullException();

		styleViewer = pStyleViewer;
	}

	/**
	 * Comapare objects
	 *
	 * @param pObject1 object 1
	 * @param pObject2 object 2
	 * @return 0 - eqaul, -1 - 1 less 2, +1 - 1 more 2
	 */
	@Override
	public int compare(MapObject pObject1, MapObject pObject2)
	{
		if (pObject1 == null || pObject2 == null)
			return 0;
		if (styleViewer == null)
			return 0;
		if (pObject1.getStyleIndex() == null || pObject2.getStyleIndex() == null)
			return 0;

		MapObjectStyle object1Style = styleViewer.getMapObjectStyle(pObject1.getStyleIndex());
		MapObjectStyle object2Style = styleViewer.getMapObjectStyle(pObject2.getStyleIndex());
		if (object1Style == null || object2Style == null)
			return 0;

		if (object1Style.getDrawPriority() < object2Style.getDrawPriority())
			return -1;
		if (object1Style.getDrawPriority() > object2Style.getDrawPriority())
			return 1;
		return 0;
	}
}
