package drawingStyles;

import java.awt.Paint;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to fill (draw) inside part of polygon on map
 *
 * @author pgalex
 */
public interface PolygonFiller
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

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	public void readFromStream(DataInputStream input) throws IOException;

	/**
	 * Write into stream
	 *
	 * @param output output stream
	 * @throws IOException writing error
	 */
	public void writeToStream(DataOutputStream output) throws IOException;
}
