package com.osmviewer.drawingStyles;

import java.util.Comparator;

/**
 * Comparator, comparing map object draw settings by its tags count
 *
 * @author pgalex
 */
public class MapObjectDrawSettingsTagsCountComparator implements Comparator<StandartMapObjectDrawSettings>
{
	/**
	 * Compare map object draw settings by defenition tags count
	 *
	 * @param object1 fisrt object. Must be not null
	 * @param object2 second object. Must be not null
	 * @return -1 - first have more tags than second, 0 - tags count is equal, 1 -
	 * first have less tags than second
	 */
	@Override
	public int compare(StandartMapObjectDrawSettings object1, StandartMapObjectDrawSettings object2)
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
