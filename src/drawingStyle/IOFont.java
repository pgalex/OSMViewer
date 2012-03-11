package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Обёртка Font
 *
 * @author Евгений
 */
public class IOFont implements ReadableMapData, WritableMapData
{
	/**
	 * Шрифт по умолчанию
	 */
	private static final Font DEFAULT_FONT = new Font("Arial", 0, 14);
	/**
	 * Шрифт
	 */
	private Font font;
	
	/**
	 * Конструктор
	 */
	public IOFont()
	{
		font = DEFAULT_FONT;
	}
	
	/**
	 * Конструктор
	 * 
	 * @param pFont шрифт
	 */
	public IOFont(Font pFont)
	{
		font = pFont;
		InitializeNullFields();
	}
	
	/**
	 * Считать из потока
	 * 
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
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
		catch(Exception e)
		{
			throw new IOException(e);
		}		
	}
	
	/**
	 * Записать в поток
	 * 
	 * @param pOutput поток вывода
	 * @throws IOException запись не удалась
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
	 * Инициализация null значениями по умолчанию
	 */
	private void InitializeNullFields()
	{
		if (font == null)
			font = DEFAULT_FONT;
	}
	
	/**
	 * Получить шрифт
	 * 
	 * @return шрифт
	 */
	public Font getFont()
	{
		return font;
	}
}
