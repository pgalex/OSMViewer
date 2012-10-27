package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.awt.Paint;

/**
 * How to fill (draw) inside part of polygon on map
 *
 * @author pgalex
 */
public interface PolygonFiller extends ReadableMapData, WritableMapData
{
	/**
	 * Get filler type
	 *
	 * @return filler type
	 */
	public PolygonFillerType getType();

	/**
	 * Get paint for drawing using filler
	 *
	 * @return paint for drawing by filler
	 */
	public Paint getPaint();
}
