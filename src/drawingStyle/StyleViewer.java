package drawingStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import map.DefenitionTags;

/**
 * Main interface of drawingStyle without "set" methods. Optimized for runtime
 * work. Access to drawing styles only by index. All styles sorted by tags count
 *
 * @author pgalex
 */
public interface StyleViewer
{
	/**
	 * Load drawing style information from file
	 *
	 * @param pFileName file name
	 * @throws IOException reading error
	 * @throws FileNotFoundException can not load file 
	 */
	public void loadFromFile(String pFileName) throws IOException, FileNotFoundException;

	/**
	 * Get index of map object drawing style
	 *
	 * @param pDefenitionTags tags of map object
	 * @return index of style of object with that defenition tags
	 * @throws ArrayStoreException object not found 
	 */
	public int getStyleIndex(DefenitionTags pDefenitionTags) throws ArrayStoreException;

	/**
	 * Get map object drawing style by index
	 *
	 * @param pIndex index of style
	 * @return map object drawing style
	 * @throws ArrayIndexOutOfBoundsException if style with this index not found
	 */
	public MapObjectStyle getMapObjectStyle(int pIndex) throws ArrayIndexOutOfBoundsException;
}
