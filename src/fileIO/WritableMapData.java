package fileIO;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Интерфейс для класс, который может записывать себя в файл
 * @author abc
 */
public interface WritableMapData
{
	/**
	 * Записать в поток
	 * @param pOutput поток вывода
	 * @throws IOException запись не удалась
	 */
	public abstract void WriteToStream(DataOutputStream pOutput) throws IOException;
}
