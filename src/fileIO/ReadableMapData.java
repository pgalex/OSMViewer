package fileIO;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Интерфейс для класс способного считывать себя из файла
 * @author abc
 */
public interface ReadableMapData
{
	/**
	 * Считать из потока
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
	 */
	public abstract void readFromStream(DataInputStream pInput) throws IOException;
}
