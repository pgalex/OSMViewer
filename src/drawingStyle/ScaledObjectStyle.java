package drawingStyle;

/**
 * Параметры отображения на определенном масштабе
 * @author abc
 */
public class ScaledObjectStyle
{
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
