package com.osmviewer.mapObjectsXmlIdentification;

import java.util.ArrayList;

/**
 * Contains draw priority value for each drawing id, by reading it from xml file
 *
 * @author preobrazhentsev
 */
public class XmlDrawPriorityContainer
{
	/**
	 * Drawing ids sorted in draw order
	 */
	private final ArrayList<String> drawingIdsInDrawOrder;

	/**
	 * Create empty
	 */
	public XmlDrawPriorityContainer()
	{
		drawingIdsInDrawOrder = new ArrayList<>();
	}

	/**
	 *
	 * @param fileName
	 * @throws IllegalArgumentException
	 */
	public void readFromFile(String fileName) throws IllegalArgumentException
	{
	}

	/**
	 *
	 * @param drawingId
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Integer getDrawPriority(String drawingId) throws IllegalArgumentException
	{
		return null;
	}
}
