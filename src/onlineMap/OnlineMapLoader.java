package onlineMap;

import drawingStyle.StyleViewer;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import map.MapBounds;
import map.exceptions.MapBoundsIsNullRuntimeException;
import map.exceptions.MapIsNullRutimeException;
import map.exceptions.StyleViewerIsNullException;

/**
 * Creates connection to openstreetmap.org and loads map data and convertes it
 * to online map for rendering. Knows how to fill Map by osm objects
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

	/**
	 * Load osm data from web and fill map with loaded objects
	 *
	 * @param pLoadingSectorBounds bounds of loading map sector
	 * @param pStyleViewer viewer, uses to assing style index to objects
	 * @param pFillingMap map, where new object will be added
	 * @throws StyleViewerIsNullException style viewer is null
	 * @throws MapIsNullRutimeException online map is null
	 * @throws MapBoundsIsNullRuntimeException loading sector bounds is null
	 * @throws NullPointerException some parameters are null
	 */
	public void loadToMap(MapBounds pLoadingSectorBounds, StyleViewer pStyleViewer,
					OnlineMap pFillingMap) throws StyleViewerIsNullException, MapIsNullRutimeException, MapBoundsIsNullRuntimeException
	{
		if (pLoadingSectorBounds == null)
			throw new MapBoundsIsNullRuntimeException();
		if (pStyleViewer == null)
			throw new StyleViewerIsNullException();
		if (pFillingMap == null)
			throw new MapIsNullRutimeException();

		try
		{
			URL openStreetMapURL = new URL("http://api.openstreetmap.org/api/0.6/map?bbox=35.7155,53.9239,35.7811,53.971");
			URLConnection openStreetMapConnection = openStreetMapURL.openConnection();
			onlineParser.convert(openStreetMapConnection.getInputStream());
		}
		catch (Exception ex)
		{
			Logger.getLogger(OnlineMapLoader.class.getName()).log(Level.SEVERE, null, ex);
		}

		/*
		 * Проверить границы загружаемого сектора Проверить заполняемую карту
		 * Создать объект для чтения Обнулить парсер Передать в парсер объект для
		 * чтения Заполнить карту данными из парсера
		 */
	}
}
