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
	 * Create with default color
	 */
	public IOColor()
	{
		color = DEFAULT_COLOR;
	}

	/**
	 * Create with color
	 *
	 * @param pColor color. if null, will be used default value
	 */
	public IOColor(Color pColor)
	{
		color = pColor;
		initializeNullFields();
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
	 * Initializ null null fields with defaul value
	 */
	private void initializeNullFields()
	{
		if (color == null)
		{
			color = DEFAULT_COLOR;
		}
	}

	/**
	 * Get storing color
	 *
	 * @return color
	 */
	public Color getColor()
	{
		return color;
	}
}
