package com.osmviewer.forms;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Panel for drawing
 *
 * @author pgalex
 */
public class DrawingPanel extends JPanel
{
	/**
	 * Object that drawes on panel
	 */
	private DrawableOnPanel painter;

	/**
	 * Default constructor
	 *
	 */
	public DrawingPanel()
	{
		super();

		painter = null;
	}

	/**
	 * Constructor
	 *
	 * @param pPainter object that will draw on panel
	 */
	public DrawingPanel(DrawableOnPanel pPainter)
	{
		super();

		painter = pPainter;
	}

	/**
	 * Set object that will draw on panel
	 *
	 * @param pPainter object that will draw on panel
	 */
	public void setPainter(DrawableOnPanel pPainter)
	{
		painter = pPainter;
	}

	/**
	 * Panel redrawing
	 *
	 * @param pGraphics graphics
	 */
	@Override
	protected void paintComponent(Graphics pGraphics)
	{
		super.paintComponent(pGraphics);

		if (painter != null)
		{
			painter.drawOnPanel((Graphics2D) pGraphics);
		}
	}
}
