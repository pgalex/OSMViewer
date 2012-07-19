package drawingStyles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Gives access to drawing style without editing but optimized for runtime map
 * drawing
 *
 * @author pgalex
 */
public class MapObjectStylesViewer extends MapObjectDrawStylesContainer implements StyleViewer
{
	/**
	 * Static array. Work faster. All styles sorted by tags count
	 */
	private MapObjectStyle[] styles;

	/**
	 * Default constructor
	 */
	public MapObjectStylesViewer()
	{
		super();
		
		styles = new MapObjectStyle[0];
	}

	/**
	 * Read from stream
	 *
	 * @param pInput reading stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			super.readFromStream(pInput);
			
			styles = readStylesFromStream(pInput);
		}
		catch (IOException ex)
		{
			throw new IOException();
		}
	}

	/**
	 * Write into stream
	 *
	 * @param pOutput output stream
	 * @throws IOException writing error
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			super.writeToStream(pOutput);
			
			writeStylesToStream(styles, pOutput);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}

	/**
	 * Get index of map object drawing style
	 *
	 * @param pDefenitionTags tags of map object
	 * @return index of style of object with that defenition tags. null if not
	 * found
	 */
	@Override
	public Integer getStyleIndex(DefenitionTags pDefenitionTags)
	{
		if (pDefenitionTags == null)
			return null;

		return findStyleIndex(styles, pDefenitionTags);
	}

	/**
	 * Get map object drawing style by index
	 *
	 * @param pIndex index of style
	 * @return map object drawing style. null if style with this index not found
	 */
	@Override
	public MapObjectStyle getMapObjectStyle(Integer pIndex)
	{
		if (pIndex == null)
			return null;
		if (pIndex < 0 || pIndex >= styles.length)
			return null;

		return styles[pIndex];
	}

	/**
	 * Get map drawing settings
	 *
	 * @return map drawing settings
	 */
	@Override
	public MapDrawingSettings getMapDrawingSettings()
	{
		return mapDrawingSettings;
	}
}