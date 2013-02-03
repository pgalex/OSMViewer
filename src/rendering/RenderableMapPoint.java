package rendering;

import mapDefenitionUtilities.MapPosition;

/**
 * Point-like map object that can be render
 *
 * @author pgalex
 */
public interface RenderableMapPoint extends RenderableMapObject
{
	/**
	 * Get position on a map
	 *
	 * @return position on a map
	 */
	public MapPosition getPosition();
}
