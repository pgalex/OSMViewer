package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Tags, describes map object
 *
 * @author pgalex
 */
public class DefenitionTags implements ReadableMapData, WritableMapData
{
	/**
	 * Array of tags
	 */
	protected ArrayList<Tag> tags;

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
			throw new IllegalArgumentException();
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

	/**
	 * Is all tags of this defenition tags including in tagsForComparing
	 *
	 * @param tagsForComparing tags for comparing
	 * @return is tagsForComparing contains this all of this tags
	 * @throws IllegalArgumentException tagsForComparing is null
	 */
	public boolean includingIn(DefenitionTags tagsForComparing) throws IllegalArgumentException
	{
		// (Сравнить теги без учета их порядка. Каждый тег из defenitionTags
		// должен входить в tagsForComparing )

		if (tagsForComparing == null)
		{
			throw new IllegalArgumentException();
		}

		if (tags.size() > tagsForComparing.count())
		{
			return false;
		}
		// null tags are equal
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
			boolean thisTagFoundInCompatingTags = false;

			for (int i = 0; i < tagsForComparing.count(); i++)
			{
				Tag comparingTag = tagsForComparing.get(i);

				if (thisTag.compareTo(comparingTag))
				{
					thisTagFoundInCompatingTags = true;
					break;
				}
			}

			if (!thisTagFoundInCompatingTags)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			tags.clear();
			int tagsCount = input.readInt();
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
	@Override
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
