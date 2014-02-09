package com.osmviewer.mapObjectsIdentification;

/**
 * Description of kind of map object
 *
 * @author preobrazhentsev
 */
public class MapObjectDescription
{
	/**
	 * Short name of object
	 */
	private String name;
	/**
	 * Description of map object
	 */
	private String description;

	/**
	 * Create with empty values
	 */
	public MapObjectDescription()
	{
		name = "";
		description = "";
	}

	public String getName()
	{
		return name;
	}

	/**
	 * Set new name
	 *
	 * @param name new name. Must be not null
	 * @throws IllegalArgumentException name is null
	 */
	public void setName(String name) throws IllegalArgumentException
	{
		if (name == null)
		{
			throw new IllegalArgumentException("name is null");
		}

		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	/**
	 * Set new description
	 *
	 * @param description new description. Must be not null
	 * @throws IllegalArgumentException description is null
	 */
	public void setDescription(String description)
	{
		if (description == null)
		{
			throw new IllegalArgumentException("description is null");
		}
		this.description = description;
	}
}
