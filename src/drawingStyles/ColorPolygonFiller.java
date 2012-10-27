package drawingStyles;

import java.awt.Color;
import java.awt.Paint;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Polygon filler - by one color (solid filling)
 *
 * @author pgalex
 */
public class ColorPolygonFiller implements PolygonFiller
{
	/**
	 * Color for filling
	 */
	private ColorWithIO fillColor;

	/**
	 * Create with color
	 *
	 * @param colorForFilling filling color
	 * @throws IllegalArgumentException colorForFilling is null
	 */
	public ColorPolygonFiller(Color colorForFilling) throws IllegalArgumentException
	{
		if (colorForFilling == null)
		{
			throw new IllegalArgumentException();
		}
		
		fillColor = new ColorWithIO(colorForFilling);
	}

	/**
	 * Get filler type
	 *
	 * @return filler type
	 */
	@Override
	public PolygonFillerType getType()
	{
		return PolygonFillerType.BY_COLOR;
	}

	/**
	 * Get paint for drawing using filler
	 *
	 * @return paint for drawing by filler
	 */
	@Override
	public Paint getPaint()
	{
		return fillColor.getStoringColor();
	}

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			fillColor.readFromStream(input);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}

	/**
	 * Write into stream
	 *
	 * @param output output stream
	 * @throws IOException writing error
	 */
	@Override
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			fillColor.writeToStream(output);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}
}
