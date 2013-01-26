package rendering.selectng;

import java.util.Comparator;

/**
 * Selecting objects comparator. Comparing by draw priority of associated object
 *
 * @author pgalex
 */
public class SelectingObjectsDrawPriorityComparator implements Comparator<SelectingObject>
{
	/**
	 * Compare selecting objects by associated map object draw priority
	 *
	 * @param selectingObject1 first selecting object
	 * @param selectingObject2 second selecting object
	 * @return -1 - first have more priority, 0 - equal, 1 - first have less
	 * priority
	 */
	@Override
	public int compare(SelectingObject selectingObject1, SelectingObject selectingObject2)
	{
		if (selectingObject1.getAssociatedObjectDrawPriority() > selectingObject2.getAssociatedObjectDrawPriority())
		{
			return -1;
		}
		else
		{
			if (selectingObject1.getAssociatedObjectDrawPriority() < selectingObject2.getAssociatedObjectDrawPriority())
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
	}
}
