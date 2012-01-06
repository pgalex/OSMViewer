package drawingStyle;

import sun.java2d.loops.DrawPolygons;

/**
 * Параметры отображения на определенном масштабе
 * @author abc
 */
public class ScaledObjectStyle
{
	/**
	 * стиль точки
	 */
	public PointDrawStyle pointStyle;
	/**
	 * стиль линии
	 */
	public LineDrawStyle lineStyle;
	/**
	 * стиль многоугольника
	 */
	public PolygonDrawStyle polygonStyle;
	/**
	 * Рисовать ли точку на данном масштабе
	 */
	public boolean drawPoint;
	/**
	 * Рисовать ли линию на данном масштабе
	 */
	public boolean drawLine;
	/**
	 * Рисовать ли многоугольник на данном масштабе
	 */
	public boolean drawPolygon;

	/**
	 * Конструктор
	 */
	public ScaledObjectStyle()
	{
		pointStyle = new PointDrawStyle();
		lineStyle = new LineDrawStyle();
		polygonStyle = new PolygonDrawStyle();
		drawPoint = false;
		drawLine = false;
		drawPolygon = false;
	}
}
