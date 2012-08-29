package drawingStyles;

import java.util.Comparator;

/**
 * Comparator, using to find style index
 *
 * @author pgalex
 */
public class FindStyleIndexComaprator implements Comparator<MapObjectDrawSettings>
{
	/**
	 * Compare map object draw settings by defenition tags count
	 *
	 * @param pObject1
	 * @param pObject2
	 * @return -1 - first have more tags than second, 0 - same tags count, 1 -
	 * less
	 */
	@Override
	public int compare(MapObjectDrawSettings pObject1, MapObjectDrawSettings pObject2)
	{
		if (pObject1 == null || pObject2 == null)
		{
			throw new NullPointerException();
		}

		if (pObject1.getDefenitionTags().size() > pObject2.getDefenitionTags().size())
		{
			return -1;
		}

		if (pObject1.getDefenitionTags().size() < pObject2.getDefenitionTags().size())
		{
			return 1;
		}

		return 0;
	}
}
