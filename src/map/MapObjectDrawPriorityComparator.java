package map;

import drawingStyles.MapObjectDrawSettings;
import drawingStyles.StyleViewer;
import java.util.Comparator;

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
	 * Create for comparing with style viewer
	 *
	 * @param styleViewerUsingForComparing Viewer for finding draw priority of objects
	 * @throws IllegalArgumentException styleViewerUsingForComparing is null
	 */
	public MapObjectDrawPriorityComparator(StyleViewer styleViewerUsingForComparing) throws IllegalArgumentException
	{
		if (styleViewerUsingForComparing == null)
		{
			throw new IllegalArgumentException();
		}

		styleViewer = styleViewerUsingForComparing;
	}

	/**
	 * Comapare objects
	 *
	 * @param object1 object 1
	 * @param object2 object 2
	 * @return 0 - eqaul, -1 - 1 less 2, +1 - 1 more 2
	 */
	@Override
	public int compare(MapObject object1, MapObject object2)
	{
		if (object1 == null || object2 == null)
		{
			return 0;
		}
		if (styleViewer == null)
		{
			return 0;
		}
		if (object1.getStyleIndex() == null || object2.getStyleIndex() == null)
		{
			return 0;
		}

		MapObjectDrawSettings object1Style = styleViewer.getMapObjectDrawSettings(object1.getStyleIndex());
		MapObjectDrawSettings object2Style = styleViewer.getMapObjectDrawSettings(object2.getStyleIndex());
		if (object1Style == null || object2Style == null)
		{
			return 0;
		}

		if (object1Style.getDrawPriority() < object2Style.getDrawPriority())
		{
			return -1;
		}
		if (object1Style.getDrawPriority() > object2Style.getDrawPriority())
		{
			return 1;
		}
		
		return 0;
	}
}
