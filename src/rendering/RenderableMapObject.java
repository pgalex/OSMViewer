package rendering;

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
	 * Get drawing style index
	 *
	 * @return style index. null if not foun
	 */
	public Integer getStyleIndex();

	/**
	 * Accept rendering visitor
	 *
	 * @param renderingVisitor renderable map objects visitor to accept
	 * @throws IllegalArgumentException renderingVisitor is null
	 */
	public void acceptRenderingVisitor(RenderableMapObjectsVisitor renderingVisitor) throws IllegalArgumentException;
}
