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
	private Font font;

	/**
	 * Create with defaut values
	 */
	public FontWithIO()
	{
		font = DEFAULT_FONT;
	}

	/**
	 * Create with font
	 *
	 * @param fontToStore storing font. If null will be set default font
	 */
	public FontWithIO(Font fontToStore)
	{
		font = fontToStore;
		initializeNullFields();
	}

	/**
	 * Auto-initialize null fields
	 */
	private void initializeNullFields()
	{
		if (font == null)
		{
			font = DEFAULT_FONT;
		}
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
	 * @param output output stream
	 * @throws IOException write error
	 */
	@Override
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			output.writeUTF(font.getFamily());
			output.writeInt(font.getStyle());
			output.writeInt(font.getSize());
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Get storing font
	 *
	 * @return storing font
	 */
	public Font getFont()
	{
		return font;
	}
}
