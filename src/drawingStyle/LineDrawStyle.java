package drawingStyle;

import java.awt.Color;

/**
 * Стиль рисования линий (не замкнутых путей)
 * @author abc
 */
public class LineDrawStyle
{
	/**
	 * Цвет линии
	 */
	private Color color;
	/**
	 * Тольщина линии
	 */
	private int width;
	// стиль линии

	/**
	 * Конструктор
	 */
	public LineDrawStyle()
	{
		color = Color.BLACK;
		width = 1;
	}

	/**
	 * @return the color
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * @param pСolor новый цвет
	 */
	public void setColor(Color pСolor)
	{
		color = pСolor;
	}

	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @param pWidth новая толщина
	 */
	public void setWidth(int pWidth)
	{
		width = pWidth;
	}
}
