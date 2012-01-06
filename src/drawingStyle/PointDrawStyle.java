package drawingStyle;

import java.awt.Image;

/**
 * Класс стиля рисования точки
 * @author abc
 */
public class PointDrawStyle
{
	/**
	 * Значок
	 */
	private Image icon;

	/**
	 * Конструктор
	 * @param pIcon значок
	 */
	public PointDrawStyle(Image pIcon)
	{
		icon = pIcon;
	}

	/**
	 * Конструктор
	 */
	public PointDrawStyle()
	{
		icon = null;
	}

	/**
	 * @return the icon
	 */
	public Image getIcon()
	{
		return icon;
	}

	/**
	 * 
	 * @param pIcon Новый значок
	 */
	public void setIcon(Image pIcon)
	{
		icon = pIcon;
	}
}
