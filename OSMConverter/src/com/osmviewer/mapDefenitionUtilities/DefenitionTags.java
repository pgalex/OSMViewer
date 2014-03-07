package com.osmviewer.mapDefenitionUtilities;

import java.util.ArrayList;

/**
 * Tags, describes map object
 *
 * @author pgalex
 */
public class DefenitionTags
{
	/**
	 * Array of tags
	 */
	private final ArrayList<Tag> tags;

	/**
	 * Create empty defenition tags
	 */
	public DefenitionTags()
	{
		tags = new ArrayList<>();
	}

	/**
	 * Get tags count
	 *
	 * @return tags count
	 */
	public int count()
	{
		return tags.size();
	}

	/**
	 * Is tags array empty
	 *
	 * @return is tags array empty
	 */
	public boolean isEmpty()
	{
		return tags.isEmpty();
	}

	/**
	 * Get tag by index
	 *
	 * @param index index of tag. Must be from 0 and less than tags count
	 * @return tag at index. Not null
	 * @throws IllegalArgumentException index have incorrect value
	 */
	public Tag get(int index) throws IllegalArgumentException
	{
		if (index < 0 || index >= tags.size())
		{
			throw new IllegalArgumentException("index is out of bounds");
		}

		return tags.get(index);
	}

	/**
	 * Add tag
	 *
	 * @param tagToAdd tag to add. Must be not null
	 * @throws IllegalArgumentException adding tag is null
	 */
	public void add(Tag tagToAdd) throws IllegalArgumentException
	{
		if (tagToAdd == null)
		{
			throw new IllegalArgumentException("tagToAdd is null");
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
	 * @param index index of tag to remove. Must be from 0 and less than tags
	 * count
	 * @throws IllegalArgumentException index of deleting tag is incorrect
	 */
	public void remove(int index) throws IllegalArgumentException
	{
		if (index < 0 || index >= tags.size())
		{
			throw new IllegalArgumentException("index is out of bounds");
		}

		tags.remove(index);
	}

	/**
	 * Is contains tag
	 *
	 * @param tagToFind tag to find. Must be not null
	 * @return is contains given tag
	 * @throws IllegalArgumentException tagToFind is null
	 */
	public boolean contains(Tag tagToFind) throws IllegalArgumentException
	{
		if (tagToFind == null)
		{
			throw new IllegalArgumentException("tagToFind is null");
		}

		return tags.contains(tagToFind);
	}
}
