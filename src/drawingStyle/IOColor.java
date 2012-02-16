package drawingStyle;

import fileIO.WritableMapData;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Цвет, реализующий чтение/запись
 *
 * @author abc
 */
public class IOColor extends Color implements WritableMapData
{
	/**
	 * Конструктор по умолчанию
	 */
	public IOColor()
	{
		super(0, 0, 0, 0);
	}

	/**
	 * Конструктор
	 *
	 * @param pR красный
	 * @param pG зелёный
	 * @param pB синий
	 * @param pA прозрачность
	 */
	public IOColor(int pR, int pG, int pB, int pA)
	{
		super(pR, pG, pB, pA);
	}
	
	/**
	 * Конструктор по стандартному цвету
	 * @param pColor цвет
	 */
	public IOColor(Color pColor)
	{
		super(pColor.getRed(), pColor.getGreen(), pColor.getBlue(), pColor.getAlpha());
	}

	/**
	 * Записать в поток
	 *
	 * @param pOutput поток записи
	 * @throws IOException ошибка при записи
	 */
	@Override
	public void WriteToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeInt(getRed());
			pOutput.writeInt(getGreen());
			pOutput.writeInt(getBlue());
			pOutput.writeInt(getAlpha());
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Прочитать цвет из потока. Выделена т.к. Color не имееет функций установки
	 * нового цвета кроме как в конструкторе
	 *
	 * @param pInput поток для чтения
	 * @return прочитанный цвет
	 * @throws IOException ошибка при чтении
	 */
	public static IOColor ReadFromStream(DataInputStream pInput) throws IOException
	{
		IOColor result = null;
		try
		{
			int r = pInput.readInt();
			int g = pInput.readInt();
			int b = pInput.readInt();
			int a = pInput.readInt();
			result = new IOColor(r, g, b, a);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
		return result;
	}
}
