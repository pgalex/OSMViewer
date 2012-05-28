package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
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
	 * Color
	 */
	private Color color;

	/**
	 * Default constructor
	 */
	public IOColor()
	{
		color = DEFAULT_COLOR;
	}

	/**
	 * Constructor with color parameters
	 *
	 * @param pR red
	 * @param pG green
	 * @param pB blue
	 * @param pA alpha
	 */
	public IOColor(int pR, int pG, int pB, int pA)
	{
		color = new Color(pR, pG, pB, pA);
	}

	/**
	 * Constructor with color pointer
	 *
	 * @param pColor color pointer
	 */
	public IOColor(Color pColor)
	{
		color = pColor;
		initializeNullField();
	}

	/**
	 * Write into stream
	 *
	 * @param pOutput write stream
	 * @throws IOException write error
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeInt(color.getRed());
			pOutput.writeInt(color.getGreen());
			pOutput.writeInt(color.getBlue());
			pOutput.writeInt(color.getAlpha());
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Read color from stream
	 *
	 * @param pInput stream
	 * @throws IOException read error
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			int r = pInput.readInt();
			int g = pInput.readInt();
			int b = pInput.readInt();
			int a = pInput.readInt();
			color = new Color(r, g, b, a);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Default values into null fields
	 */
	private void initializeNullField()
	{
		if (color == null)
			color = DEFAULT_COLOR;
	}

	/**
	 * Get color
	 *
	 * @return color
	 */
	public Color getColor()
	{
		return color;
	}
}
