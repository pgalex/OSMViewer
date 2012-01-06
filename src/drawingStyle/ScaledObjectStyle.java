package drawingStyle;

/**
 * Параметры отображения на определенном масштабе
 * @author abc
 */
public class ScaledObjectStyle
{
	/**
	 * стиль точки
	 */
	private PointDrawStyle pointStyle;
	/**
	 * стиль линии
	 */
	private LineDrawStyle lineStyle;
	/**
	 * стиль многоугольника
	 */
	private PolygonDrawStyle polygonStyle;

	/**
	 * Конструктор
	 */
	public ScaledObjectStyle()
	{
		pointStyle = new PointDrawStyle();
		lineStyle = new LineDrawStyle();
		polygonStyle = new PolygonDrawStyle();
	}

	/**
	 * @return the pointStyle
	 */
	public PointDrawStyle getPointStyle()
	{
		return pointStyle;
	}

	/**
	 * @param pPointStyle the pPointStyle to set
	 */
	public void setPointStyle(PointDrawStyle pPointStyle)
	{
		pointStyle = pPointStyle;
	}

	/**
	 * @return the lineStyle
	 */
	public LineDrawStyle getLineStyle()
	{
		return lineStyle;
	}

	/**
	 * @param pLineStyle the pLineStyle to set
	 */
	public void setLineStyle(LineDrawStyle pLineStyle)
	{
		lineStyle = pLineStyle;
	}

	/**
	 * @return the polygonStyle
	 */
	public PolygonDrawStyle getPolygonStyle()
	{
		return polygonStyle;
	}

	/**
	 * @param pPolygonStyle the pPolygonStyle to set
	 */
	public void setPolygonStyle(PolygonDrawStyle pPolygonStyle)
	{
		polygonStyle = pPolygonStyle;
	}
}
