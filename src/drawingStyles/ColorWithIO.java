package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Encapsulate Color with read/write
 *
 * @author Евгений
 */
public class ColorWithIO implements WritableMapData, ReadableMapData
{
	/**
	 * Default storingColor
	 */
	private static final Color DEFAULT_COLOR = Color.BLACK;
	/**
	 * Storing storingColor
	 */
	private Color storingColor;

	/**
	 * Create with default storingColor
	 */
	public ColorWithIO()
	{
		storingColor = DEFAULT_COLOR;
	}

	/**
	 * Create with storingColor
	 *
	 * @param colorToStore storing Color. Must be not null
	 * @throws IllegalArgumentException storing Color is null
	 */
	public ColorWithIO(Color colorToStore) throws IllegalArgumentException
	{
		if (colorToStore == null)
		{
			throw new IllegalArgumentException();
		}

		storingColor = colorToStore;
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
			output.writeInt(storingColor.getRed());
			output.writeInt(storingColor.getGreen());
			output.writeInt(storingColor.getBlue());
			output.writeInt(storingColor.getAlpha());
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Read storingColor from stream
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
			storingColor = new Color(r, g, b, a);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Set new storing storingColor
	 *
	 * @param colorToSet new storing storingColor
	 * @throws IllegalArgumentException new storing storingColor is null
	 */
	public void setStoringColor(Color colorToSet) throws IllegalArgumentException
	{
		if (colorToSet == null)
		{
			throw new IllegalArgumentException();
		}

		storingColor = colorToSet;
	}

	/**
	 * Get storing storingColor
	 *
	 * @return storing storingColor
	 */
	public Color getStoringColor()
	{
		return storingColor;
	}
}
