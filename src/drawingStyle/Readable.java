package drawingStyle;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Интерфейс для класс способного считывать себя из файла
 * @author abc
 */
public interface Readable
{
	/**
	 * Считать из потока
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
	 */
	public abstract void ReadFromStream(DataInputStream pInput) throws IOException;
}
