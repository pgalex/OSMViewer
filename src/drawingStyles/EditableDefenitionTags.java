package drawingStyles;

import map.exceptions.TagIndexOutOfBoundsException;
import map.exceptions.TagIsNullException;

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
	 * @param pTag tag to add
	 * @throws TagIsNullException adding tag is null
	 */
	public void add(MapTag pTag) throws TagIsNullException
	{
		if (pTag == null)
		{
			throw new TagIsNullException(this);
		}

		tags.add(pTag);
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
	 * @throws TagIndexOutOfBoundsException index of deleting tag is out of bounds
	 */
	public void remove(int index) throws TagIndexOutOfBoundsException
	{
		if (index < 0 || index >= tags.size())
		{
			throw new TagIndexOutOfBoundsException(this, index, 0, tags.size());
		}

		tags.remove(index);
	}
}
