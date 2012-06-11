package IO;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Interface for object that can write itself into file
 *
 * @author abc
 */
public interface WritableMapData
{
	/**
	 * Write into stream
	 *
	 * @param pOutput output stream
	 * @throws IOException writing error
	 */
	public abstract void writeToStream(DataOutputStream pOutput) throws IOException;
}
