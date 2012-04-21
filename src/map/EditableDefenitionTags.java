package map;

import java.util.ArrayList;
import map.exceptions.TagIsNullException;

/**
 * Defenition tags with "edit" methods
 *
 * @author pgalex
 */
public class EditableDefenitionTags extends DefenitionTags
{
	/**
	 * Default construutor
	 */
	public EditableDefenitionTags()
	{
		super();
	}

	/**
	 * Constructor
	 *
	 * @param pTags array of tags
	 */
	public EditableDefenitionTags(ArrayList<MapTag> pTags)
	{
		super(pTags);
	}

	/**
	 * Add tag
	 *
	 * @param pTag tag
	 * @throws TagIsNullException tag is null
	 */
	public void add(MapTag pTag) throws TagIsNullException
	{
		if (pTag == null)
			throw new TagIsNullException(this);
		tags.add(pTag);
	}

	/**
	 * Clear tags array
	 */
	public void clear()
	{
		tags.clear();
	}

	/**
	 * Remove elemet with index
	 *
	 * @param pIndex index of element in tags array
	 * @throws ArrayIndexOutOfBoundsException index out of bounds
	 */
	public void remove(int pIndex) throws ArrayIndexOutOfBoundsException
	{
		if (pIndex < 0 || pIndex >= tags.size())
			throw new ArrayIndexOutOfBoundsException();
		tags.remove(pIndex);
	}
}
