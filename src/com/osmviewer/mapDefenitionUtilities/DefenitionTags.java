package com.osmviewer.mapDefenitionUtilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
	private ArrayList<Tag> tags;

	/**
	 * Create empty defenition tags
	 */
	public DefenitionTags()
	{
		tags = new ArrayList<Tag>();
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
	 * @param index index of tag
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
	 * @param tagToAdd tag to add
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
	 * @param index index of tag to remove
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
	 * Is all tags of this defenition tags including in tagsForComparing
	 *
	 * @param tagsForComparing tags for comparing
	 * @return is tagsForComparing contains all of this tags
	 * @throws IllegalArgumentException tagsForComparing is null
	 */
	public boolean includingIn(DefenitionTags tagsForComparing) throws IllegalArgumentException
	{
		// (Сравнить теги без учета их порядка. Каждый тег из defenitionTags
		// должен входить в tagsForComparing )

		if (tagsForComparing == null)
		{
			throw new IllegalArgumentException("tagsForComparing is null");
		}

		if (tags.size() > tagsForComparing.count())
		{
			return false;
		}
		// empty tags are equal
		if (tags.isEmpty() && tagsForComparing.isEmpty())
		{
			return true;
		}
		if (tags.isEmpty() && !tagsForComparing.isEmpty())
		{
			return false;
		}

		for (Tag thisTag : tags)
		{
			if (!tagsForComparing.contains(thisTag))
			{
				return false;
			}
		}

		return true;
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

		for (Tag tag : tags)
		{
			if (tag.equals(tagToFind))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			int tagsCount = input.readInt();
			tags = new ArrayList<Tag>(tagsCount);
			for (int i = 0; i < tagsCount; i++)
			{
				Tag readingTag = new Tag();
				readingTag.readFromStream(input);
				tags.add(readingTag);
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Write into stream
	 *
	 * @param output output stream
	 * @throws IOException writing error
	 */
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			output.writeInt(tags.size());
			for (Tag tag : tags)
			{
				tag.writeToStream(output);
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
