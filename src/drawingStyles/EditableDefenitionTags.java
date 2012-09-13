package drawingStyles;

import drawingStyles.exceptions.IncorrectParameterException;

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
	 * @throws IncorrectParameterException adding tag is null
	 */
	public void add(MapTag tagToAdd) throws IncorrectParameterException
	{
		if (tagToAdd == null)
		{
			throw new IncorrectParameterException();
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
