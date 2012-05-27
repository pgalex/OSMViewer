package forms;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Panel for drawing
 *
 * @author pgalex
 */
public class JDrawingPanel extends JPanel
{
	/**
	 * Object that drawes on panel
	 */
	private DrawableOnPanel painter;

	/**
	 * Default constructor
	 */
	public JDrawingPanel()
	{
		super();

		painter = null;
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
			painter.drawOnPanel((Graphics2D) pGraphics);
	}
}
