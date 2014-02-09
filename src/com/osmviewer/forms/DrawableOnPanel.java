package com.osmviewer.forms;

import java.awt.Graphics2D;

/**
 * Interface of object that will be draw on JDrawingPanel
 *
 * @author pgalex
 */
public interface DrawableOnPanel
{
	/**
	 * Drawing on panel
	 *
	 * @param pPanelGraphics graphics of panel
	 */
	public abstract void drawOnPanel(Graphics2D pPanelGraphics);
}
