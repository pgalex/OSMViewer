package drawingStyles;

/**
 * Defenition tags that can be changed
 *
 * @author pgalex
 */
public class EditableDefenitionTags extends DefenitionTags
{
	/**
	 * Create empty editable definition tags
	 */
	public EditableDefenitionTags()
	{
		super();
	}

	/**
	 * Add tag
	 *
	 * @param tagToAdd tag to add
	 * @throws IllegalArgumentException adding tag is null
	 */
	public void add(MapTag tagToAdd) throws IllegalArgumentException
	{
		if (tagToAdd == null)
		{
			throw new IllegalArgumentException();
		}

		tags.add(tagToAdd);
	}

	/**
	 * Remove all tags
	 */
	public void clear()
	{
		tags.clear();
	}

	/**
	 * Remove tag with index
	 *
	 * @param index index of tag to remove
	 * @throws IllegalArgumentException index of deleting tag is incorrect
	 */
	public void remove(int index) throws IllegalArgumentException
	{
		if (index < 0 || index >= tags.size())
		{
			throw new IllegalArgumentException();
		}

		tags.remove(index);
	}
}
