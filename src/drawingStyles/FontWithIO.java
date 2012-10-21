package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Encapsulate Font with read/write
 *
 * @author Евгений
 */
public class FontWithIO implements ReadableMapData, WritableMapData
{
	/**
	 * Default font
	 */
	private static final Font DEFAULT_FONT = new Font("Arial", 0, 14);
	/**
	 * Storing font
	 */
	private Font storingFont;

	/**
	 * Create with defaut storingFont
	 */
	public FontWithIO()
	{
		storingFont = DEFAULT_FONT;
	}

	/**
	 * Create with font
	 *
	 * @param fontToStore storing font
	 * @throws IllegalArgumentException fontToStore is null
	 */
	public FontWithIO(Font fontToStore) throws IllegalArgumentException
	{
		if (fontToStore == null)
		{
			throw new IllegalArgumentException();
		}

		storingFont = fontToStore;
	}

	/**
	 * Get storing font
	 *
	 * @return storing font
	 */
	public Font getStoringFont()
	{
		return storingFont;
	}

	/**
	 * Set new storing font
	 *
	 * @param fontToSet new storing font
	 * @throws IllegalArgumentException fontToSet is null
	 */
	public void setStoringFont(Font fontToSet) throws IllegalArgumentException
	{
		if (fontToSet == null)
		{
			throw new IllegalArgumentException();
		}

		storingFont = fontToSet;
	}

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException read error
	 */
	@Override
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			String fontFamily = input.readUTF();
			int fontStyle = input.readInt();
			int fontSize = input.readInt();
			storingFont = new Font(fontFamily, fontStyle, fontSize);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Write into stream
	 *
	 * @param output output stream
	 * @throws IOException write error
	 */
	@Override
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			output.writeUTF(storingFont.getFamily());
			output.writeInt(storingFont.getStyle());
			output.writeInt(storingFont.getSize());
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
