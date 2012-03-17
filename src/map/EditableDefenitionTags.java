package map;

import java.util.ArrayList;

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
	 */
	public void add(MapTag pTag)
	{
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
	 */
	public void remove(int pIndex)
	{
		if (pIndex < 0 || pIndex >= tags.size())
			return;
		tags.remove(pIndex);
	}
}
