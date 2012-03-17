package drawingStyle;

import java.io.IOException;
import map.DefenitionTags;

/**
 * Main interface of drawingStyle without "set" methods. Optimized for runtime
 * work. Access to drawing styles only by special id
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
	 */
	public void loadFromFile(String pFileName) throws IOException;

	/**
	 * Get id of map object drawing style
	 *
	 * @param pDefenitionTags tags of map object
	 * @return id of style of object with that defenition tags
	 */
	public int getStyleId(DefenitionTags pDefenitionTags);

	/**
	 * Get map object drawing style by id
	 *
	 * @param pStyleId id of style
	 * @return map object drawing style
	 * @throws ArrayIndexOutOfBoundsException if style with this id not found
	 */
	public MapObjectStyle getMapObjectStyle(int pStyleId) throws ArrayIndexOutOfBoundsException;
}
