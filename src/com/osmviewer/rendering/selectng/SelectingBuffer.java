package com.osmviewer.rendering.selectng;

import com.osmviewer.rendering.RenderableMapObject;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Stores interperation of objects, drawen on target canvas, specialized for
 * selection (using to find object at point on target canvas)
 *
 * @author pgalex
 */
public class SelectingBuffer
{
	/**
	 * Selecting objects of buffer
	 */
	private final LinkedList<SelectingObject> selectingObjects;

	/**
	 * Create empty
	 */
	public SelectingBuffer()
	{
		selectingObjects = new LinkedList<>();
	}

	/**
	 * Get count of storing selecting objects
	 *
	 * @return count of storing selecting objects
	 */
	public int getSelectingObjectCount()
	{
		return selectingObjects.size();
	}

	/**
	 * Get storing selecting object by index
	 *
	 * @param index index of selecting object
	 * @return storing selecting object by index
	 * @throws IllegalArgumentException index is out of bounds
	 */
	public SelectingObject getSelectingObject(int index) throws IllegalArgumentException
	{
		if (index < 0 || index >= selectingObjects.size())
		{
			throw new IllegalArgumentException("index is out of bounds");
		}
		
		return selectingObjects.get(index);
	}

	/**
	 * Find map objects associated with buffer's selecting objects, hits by point
	 *
	 * @param point point for finding map objects at it
	 * @return map objects at point, sorted by draw priority (more draw priority -
	 * less index). Empty if no objects found
	 * @throws IllegalArgumentException point is null
	 */
	public RenderableMapObject[] findObjectsAtPoint(Point2D point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException("point is null");
		}

		ArrayList<SelectingObject> selectingObjectsAtPoint = findSelectingObjectsAtPoint(point);

		RenderableMapObject[] mapObjectsAtPoint = new RenderableMapObject[selectingObjectsAtPoint.size()];
		for (int i = 0; i < selectingObjectsAtPoint.size(); i++)
		{
			mapObjectsAtPoint[i] = selectingObjectsAtPoint.get(i).getAssociatedMapObject();
		}
		return mapObjectsAtPoint;
	}

	/**
	 * Find selecting objects of buffer at point
	 *
	 * @param point point for finding selecting objects at
	 * @return selecting objects of buffer, hits by point. Empty if no objects
	 * found. Sorted by associated map objects draw priority
	 * @throws IllegalArgumentException point is null
	 */
	private ArrayList<SelectingObject> findSelectingObjectsAtPoint(Point2D point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException("point is null");
		}

		ArrayList<SelectingObject> selectingObjectsAtPoint = new ArrayList<>();
		for (SelectingObject selectingObject : selectingObjects)
		{
			if (selectingObject.isHitsByPoint(point))
			{
				selectingObjectsAtPoint.add(selectingObject);
			}
		}
		Collections.sort(selectingObjectsAtPoint, new SelectingObjectsDrawPriorityComparator());

		return selectingObjectsAtPoint;
	}

	/**
	 * Add selecting object to buffer
	 *
	 * @param selectingObjectToAdd adding selecting object
	 * @throws IllegalArgumentException selectingObjectToAdd is null
	 */
	public void addSelectingObject(SelectingObject selectingObjectToAdd) throws IllegalArgumentException
	{
		if (selectingObjectToAdd == null)
		{
			throw new IllegalArgumentException("selectingObjectToAdd is null");
		}

		selectingObjects.add(selectingObjectToAdd);
	}

	/**
	 * Remove all selecting objects
	 */
	public void clear()
	{
		selectingObjects.clear();
	}
}
