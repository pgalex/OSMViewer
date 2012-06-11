package IO;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Interface for object that can read itself from file
 * @author abc
 */
public interface ReadableMapData
{
	/**
	 * Read from stream
	 *
	 * @param pInput reading stream
	 * @throws IOException reading error
	 */
	public abstract void readFromStream(DataInputStream pInput) throws IOException;
}