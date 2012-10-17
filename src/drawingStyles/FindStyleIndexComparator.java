package drawingStyles;

import java.util.Comparator;

/**
 * Comparator, using to find style index
 *
 * @author pgalex
 */
public class FindStyleIndexComparator implements Comparator<MapObjectDrawSettings>
{
	/**
	 * Compare map object draw settings by defenition tags count
	 *
	 * @param object1 fisrt object. Must be not null
	 * @param object2 second object. Must be not null
	 * @return -1 - first have more tags than second and will have more priority
	 * for finding, 0 - tags count is equal, 1 - first have less tags than second
	 * and will have less priority for finding
	 */
	@Override
	public int compare(MapObjectDrawSettings object1, MapObjectDrawSettings object2)
	{
		if (object1 == null || object2 == null)
		{
			throw new NullPointerException();
		}

		if (object1.getDefenitionTags().count() > object2.getDefenitionTags().count())
		{
			return -1;
		}

		if (object1.getDefenitionTags().count() < object2.getDefenitionTags().count())
		{
			return 1;
		}

		return 0;
	}
}
