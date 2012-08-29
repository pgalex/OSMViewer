package IO;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Interface for object that can read itself from stream
 * @author abc
 */
public interface ReadableMapData
{
	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	public abstract void readFromStream(DataInputStream input) throws IOException;
}
