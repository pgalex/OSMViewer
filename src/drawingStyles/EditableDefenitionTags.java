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
	 * Create empty editable tags
	 */
	public EditableDefenitionTags()
	{
		super();
	}

	/**
	 * Add tag
	 *
	 * @param pTag adding tag
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
	 * @param pIndex index of tag
	 * @throws TagIndexOutOfBoundsException index of deleting tag is out of bounds
	 */
	public void remove(int pIndex) throws TagIndexOutOfBoundsException
	{
		if (pIndex < 0 || pIndex >= tags.size())
		{
			throw new TagIndexOutOfBoundsException(this, pIndex, 0, tags.size());
		}

		tags.remove(pIndex);
	}
}
