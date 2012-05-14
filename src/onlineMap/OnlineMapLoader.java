package onlineMap;

/**
 * Creates connection to openstreetmap.org and loads map data and convertes it
 * to online map for rendering
 *
 * @author pgalex
 */
public class OnlineMapLoader
{
	/**
	 * Parser for converting .osm xml data to map objects
	 */
	private OnlineOSMParser onlineParser;

	/**
	 * Default constructor
	 */
	public OnlineMapLoader()
	{
		onlineParser = new OnlineOSMParser();
	}
}
