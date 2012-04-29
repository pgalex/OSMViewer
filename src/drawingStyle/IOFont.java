package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Encapsulate Font with read/write
 *
 * @author Евгений
 */
public class IOFont implements ReadableMapData, WritableMapData
{
	/**
	 * Default font
	 */
	private static final Font DEFAULT_FONT = new Font("Arial", 0, 14);
	/**
	 * Font
	 */
	private Font font;

	/**
	 * Constructor
	 */
	public IOFont()
	{
		font = DEFAULT_FONT;
	}

	/**
	 * Constructor with pointer
	 *
	 * @param pFont font pointer
	 */
	public IOFont(Font pFont)
	{
		font = pFont;
		InitializeNullFields();
	}

	/**
	 * Read from stream
	 *
	 * @param pInput read stream
	 * @throws IOException read error
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			String fontFamily = pInput.readUTF();
			int fontStyle = pInput.readInt();
			int fontSize = pInput.readInt();
			font = new Font(fontFamily, fontStyle, fontSize);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
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
			pOutput.writeUTF(font.getFamily());
			pOutput.writeInt(font.getStyle());
			pOutput.writeInt(font.getSize());
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Default values into null fields
	 */
	private void InitializeNullFields()
	{
		if (font == null)
			font = DEFAULT_FONT;
	}

	/**
	 * Get font
	 *
	 * @return font
	 */
	public Font getFont()
	{
		return font;
	}
}
