package rendering;

import drawingStyles.MapObjectDrawSettings;
import mapDefenitionUtilities.DefenitionTags;

/**
 * Map object that can be rendered
 *
 * @author pgalex
 */
public interface RenderableMapObject
{
	/**
	 * Get defenition tags
	 *
	 * @return defenition tags
	 */
	public DefenitionTags getDefenitionTags();

	/**
	 * Get draw settings
	 *
	 * @return draw settings of map object. Null if not defined
	 */
	public MapObjectDrawSettings getDrawSettings();

	/**
	 * Accept rendering visitor
	 *
	 * @param renderingVisitor renderable map objects visitor to accept
	 * @throws IllegalArgumentException renderingVisitor is null
	 */
	public void acceptRenderingVisitor(RenderableMapObjectsVisitor renderingVisitor) throws IllegalArgumentException;
}
