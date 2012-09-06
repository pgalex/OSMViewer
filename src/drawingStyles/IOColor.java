package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import drawingStyles.exceptions.ColorIsNullException;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Encapsulate Color with read/write
 *
 * @author Евгений
 */
public class IOColor implements WritableMapData, ReadableMapData
{
	/**
	 * Default color
	 */
	private static final Color DEFAULT_COLOR = Color.BLACK;
	/**
	 * Storing color
	 */
	private Color color;

	/**
	 * Create with default color
	 */
	public IOColor()
	{
		color = DEFAULT_COLOR;
	}

	/**
	 * Create with color
	 *
	 * @param colorToStore storing color. Must be not null
	 * @throws ColorIsNullException storing color is null
	 */
	public IOColor(Color colorToStore) throws ColorIsNullException
	{
		if (colorToStore == null)
		{
			throw new ColorIsNullException();
		}

		color = colorToStore;
	}

	/**
	 * Write into stream
	 *
	 * @param output write stream
	 * @throws IOException write error
	 */
	@Override
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			output.writeInt(color.getRed());
			output.writeInt(color.getGreen());
			output.writeInt(color.getBlue());
			output.writeInt(color.getAlpha());
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Read color from stream
	 *
	 * @param input stream
	 * @throws IOException read error
	 */
	@Override
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			int r = input.readInt();
			int g = input.readInt();
			int b = input.readInt();
			int a = input.readInt();
			color = new Color(r, g, b, a);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Set new storing color
	 *
	 * @param colorToSet new storing color
	 * @throws ColorIsNullException new storing color is null
	 */
	public void setColor(Color colorToSet) throws ColorIsNullException
	{
		if (colorToSet == null)
		{
			throw new ColorIsNullException();
		}

		color = colorToSet;
	}

	/**
	 * Get storing color
	 *
	 * @return storing color
	 */
	public Color getColor()
	{
		return color;
	}
}
