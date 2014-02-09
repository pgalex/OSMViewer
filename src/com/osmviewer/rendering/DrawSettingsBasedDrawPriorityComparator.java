package com.osmviewer.rendering;

/**
 * Comparator for sorting map objects by draw priority using DrawSettingsFinder
 * to get draw priority of comparing map objects
 *
 * @author pgalex
 */
public class DrawSettingsBasedDrawPriorityComparator implements RenderableMapObjectsDrawPriorityComparator
{
	private DrawSettingsFinder drawSettingsFinder;

	public DrawSettingsBasedDrawPriorityComparator(DrawSettingsFinder drawSettingsFinder) throws IllegalArgumentException
	{
		if (drawSettingsFinder == null)
		{
			throw new IllegalArgumentException("drawSettingsFinder is null");
		}

		this.drawSettingsFinder = drawSettingsFinder;
	}

	/**
	 * Comapare objects
	 *
	 * @param object1
	 * @param object2
	 * @return 0 - eqaul, -1 - 1 less 2, +1 - 1 more 2
	 */
	@Override
	public int compare(RenderableMapObject object1, RenderableMapObject object2)
	{
		Integer object1DrawPriority = drawSettingsFinder.findDrawPriority(object1.getDrawingId());
		Integer object2DrawPriority = drawSettingsFinder.findDrawPriority(object2.getDrawingId());

		if (object1DrawPriority == null || object2DrawPriority == null)
		{
			return 0;
		}

		if (object1DrawPriority < object2DrawPriority)
		{
			return -1;
		}
		if (object1DrawPriority > object2DrawPriority)
		{
			return 1;
		}

		return 0;
	}
}
