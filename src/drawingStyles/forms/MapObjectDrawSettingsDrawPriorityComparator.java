package drawingStyles.forms;

import drawingStyles.MapObjectDrawSettings;
import java.util.Comparator;
import map.MapObject;

/**
 * Comparing map object draw settings by draw priority. Less draw priority -
 * less index
 *
 * @author pgalex
 */
public class MapObjectDrawSettingsDrawPriorityComparator implements Comparator<MapObjectDrawSettings>
{
	/**
	 * Comapare draw settings
	 *
	 * @param settings1 draw settings 1
	 * @param settings2 draw settings 2
	 * @return 0 - eqaul, -1 - 1 less 2, +1 - 1 more 2
	 */
	@Override
	public int compare(MapObjectDrawSettings settings1, MapObjectDrawSettings settings2)
	{
		if (settings1 == null || settings2 == null)
		{
			return 0;
		}

		if (settings1.getDrawPriority() < settings2.getDrawPriority())
		{
			return -1;
		}
		if (settings1.getDrawPriority() > settings2.getDrawPriority())
		{
			return 1;
		}

		return 0;
	}
}
