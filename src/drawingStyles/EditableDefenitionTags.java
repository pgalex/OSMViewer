package drawingStyles;

import drawingStyles.exceptions.IncorrectParameterException;
import drawingStyles.exceptions.TagIsNullException;

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
			throw new TagIsNullException();
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
	 * @throws IncorrectParameterException index of deleting tag is incorrect
	 */
	public void remove(int index) throws IncorrectParameterException
	{
		if (index < 0 || index >= tags.size())
		{
			throw new IncorrectParameterException();
		}

		tags.remove(index);
	}
}
