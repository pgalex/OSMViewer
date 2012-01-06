package drawingStyle;

import java.awt.Color;

/**
 * Стиль многоугольника (замкнутой линии)
 * @author abc
 */
public class PolygonDrawStyle
{
	/**
	 * Цвет заполнения
	 */
	private Color fillColor;
	// fill image
	/**
	 * Стиль рисования границы
	 */
	private LineDrawStyle borderDrawStyle;

	/**
	 * Конструктор
	 */
	public PolygonDrawStyle()
	{
		fillColor = Color.GRAY;
		borderDrawStyle = new LineDrawStyle();
	}

	/**
	 * @return the fillColor
	 */
	public Color getFillColor()
	{
		return fillColor;
	}

	/**
	 * @param pFillColor the fillColor to set
	 */
	public void setFillColor(Color pFillColor)
	{
		fillColor = pFillColor;
	}

	/**
	 * @return the borderDrawStyle
	 */
	public LineDrawStyle getBorderDrawStyle()
	{
		return borderDrawStyle;
	}

	/**
	 * @param pBorderDrawStyle the borderDrawStyle to set
	 */
	public void setBorderDrawStyle(LineDrawStyle pBorderDrawStyle)
	{
		borderDrawStyle = pBorderDrawStyle;
	}
}
