package drawingStyles.forms;

/**
 * Item of draw priority list
 *
 * @author pgalex
 */
public class DrawingPriorityListItem implements Comparable<DrawingPriorityListItem>
{
	private Integer drawPriority;
	private String description;

	public DrawingPriorityListItem(int itemDrawPriority, String itemDescription)
	{
		drawPriority = itemDrawPriority;
		description = itemDescription;
	}

	@Override
	public int compareTo(DrawingPriorityListItem t)
	{
		return drawPriority.compareTo(t.drawPriority);
	}

	@Override
	public String toString()
	{
		return drawPriority.toString() + " - " + description;
	}
}
