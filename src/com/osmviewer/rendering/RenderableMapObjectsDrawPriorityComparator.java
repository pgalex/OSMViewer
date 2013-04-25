package com.osmviewer.rendering;

import java.util.Comparator;

/**
 * Comparator for sorting map objects by draw priority
 *
 * @author pgalex
 */
public class RenderableMapObjectsDrawPriorityComparator implements Comparator<RenderableMapObject>
{
	/**
	 * Comapare objects
	 *
	 * @param object1 object 1
	 * @param object2 object 2
	 * @return 0 - eqaul, -1 - 1 less 2, +1 - 1 more 2
	 */
	@Override
	public int compare(RenderableMapObject object1, RenderableMapObject object2)
	{
		RenderableMapObjectDrawSettings object1Style = object1.getDrawSettings();
		RenderableMapObjectDrawSettings object2Style = object2.getDrawSettings();

		if (object1Style == null || object2Style == null)
		{
			return 0;
		}

		if (object1.determineDrawPriotity() < object2.determineDrawPriotity())
		{
			return -1;
		}
		if (object1.determineDrawPriotity() > object2.determineDrawPriotity())
		{
			return 1;
		}

		return 0;
	}
}
