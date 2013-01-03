package rendering;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineSegment;
import drawingStyles.TextDrawSettings;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Canvas using for text drawing
 *
 * @author pgalex
 */
public class TextCanvas
{
	/**
	 * Graphics where draw text
	 */
	private Graphics2D canvas;

	/**
	 * Create text canvas by graphics
	 *
	 * @param canvasForText graphics using to draw text
	 * @throws IllegalArgumentException canvasForText is null
	 */
	public TextCanvas(Graphics2D canvasForText) throws IllegalArgumentException
	{
		if (canvasForText == null)
		{
			throw new IllegalArgumentException();
		}

		canvas = canvasForText;
	}

	/**
	 * Draw text on canvas by text center x and top bound of text
	 *
	 * @param textToDraw text to draw on canvas. Will not be draw if empty
	 * @param drawSettings draw setting of text
	 * @param textCenterX text center x on canvas
	 * @param textTop text top bound on canvas
	 * @throws IllegalArgumentException textToDraw or drawSettings is null
	 */
	public void drawTextUnderPoint(String textToDraw, TextDrawSettings drawSettings,
					double textCenterX, double textTop) throws IllegalArgumentException
	{
		if (textToDraw == null)
		{
			throw new IllegalArgumentException();
		}
		if (drawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		if (textToDraw.isEmpty())
		{
			return;
		}

		canvas.setColor(drawSettings.getColor());
		canvas.setFont(drawSettings.getFont());

		FontMetrics textFontMetrics = canvas.getFontMetrics(drawSettings.getFont());
		int textWidth = textFontMetrics.stringWidth(textToDraw);
		int textHeight = textFontMetrics.getHeight();

		canvas.drawString(textToDraw, (int) textCenterX - textWidth / 2, (int) textTop + textHeight / 2);
	}

	/**
	 * Draw text on canvas by center point of text (text center will be in given
	 * point)
	 *
	 * @param textToDraw text to draw on canvas. Will not be draw if empty
	 * @param drawSettings draw setting of text
	 * @param textCenterX text center x on canvas
	 * @param textCenterY text center y on canvas
	 * @throws IllegalArgumentException textToDraw or drawSettings is null
	 */
	public void drawTextAtPoint(String textToDraw, TextDrawSettings drawSettings,
					double textCenterX, double textCenterY) throws IllegalArgumentException
	{
		if (textToDraw == null)
		{
			throw new IllegalArgumentException();
		}
		if (drawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		if (textToDraw.isEmpty())
		{
			return;
		}

		canvas.setColor(drawSettings.getColor());
		canvas.setFont(drawSettings.getFont());

		FontMetrics textFontMetrics = canvas.getFontMetrics(drawSettings.getFont());
		int textWidth = textFontMetrics.stringWidth(textToDraw);
		int textAscent = textFontMetrics.getAscent();

		canvas.drawString(textToDraw,
						(int) (textCenterX - textWidth / 2),
						(int) (textCenterY + textAscent / 2));
	}

	/**
	 * Compute text, drawen at point, bounds on text canvas
	 *
	 * @param text text, which bounds will be computed
	 * @param drawSettings draw settings of text
	 * @param textCenterX text center x on canvas
	 * @param textCenterY text center y on canvas
	 * @return text bounds on target text canvas
	 * @throws IllegalArgumentException textToDraw or drawSettings is null
	 */
	public Rectangle2D computeTextAtPointBounds(String text, TextDrawSettings drawSettings,
					double textCenterX, double textCenterY) throws IllegalArgumentException
	{
		if (text == null)
		{
			throw new IllegalArgumentException();
		}
		if (drawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		FontMetrics textFontMetrics = canvas.getFontMetrics(drawSettings.getFont());
		Rectangle2D textBoundsAtZeroPosition = textFontMetrics.getStringBounds(text, canvas);
		return new Rectangle2D.Double(textCenterX - textBoundsAtZeroPosition.getWidth() / 2,
						textCenterY - textBoundsAtZeroPosition.getHeight() / 2,
						textBoundsAtZeroPosition.getWidth(),
						textBoundsAtZeroPosition.getHeight());
	}

	/**
	 * Draw text on canvas by multiline defined by points array
	 *
	 * @param textToDraw text to draw on canvas
	 * @param drawSettings draw setting of text
	 * @param multilinePoints points of multiline
	 * @throws IllegalArgumentException textToDraw, drawSettings or
	 * multilinePoints is null, multilinePoints contains less than 2 points or
	 * contains null points
	 */
	public void drawTextOnMultiline(String textToDraw, TextDrawSettings drawSettings,
					Point2D[] multilinePoints) throws IllegalArgumentException
	{
		if (textToDraw == null)
		{
			throw new IllegalArgumentException();
		}
		if (drawSettings == null)
		{
			throw new IllegalArgumentException();
		}
		if (!isMultilinePointsCorrect(multilinePoints))
		{
			throw new IllegalArgumentException();
		}

		if (textToDraw.isEmpty())
		{
			return;
		}

		canvas.setColor(drawSettings.getColor());
		canvas.setFont(drawSettings.getFont());

		FontMetrics textFontMetrics = canvas.getFontMetrics(drawSettings.getFont());
		int textWidth = textFontMetrics.stringWidth(textToDraw);

		for (int i = 0; i < multilinePoints.length - 1; i++)
		{
			Point2D firstPoint = multilinePoints[i];
			Point2D secondPoint = multilinePoints[i + 1];

			if (firstPoint.distance(secondPoint) > textWidth)
			{
				drawTextOnLine(textToDraw, firstPoint, secondPoint);
			}
		}
	}

	/**
	 * Is points of multiline correct: not null, contains 2 or more points and not
	 * contains null
	 *
	 * @param multilinePoints points of multiline to test
	 * @return is points correct
	 */
	private boolean isMultilinePointsCorrect(Point2D[] multilinePoints)
	{
		if (multilinePoints == null)
		{
			return false;
		}

		if (multilinePoints.length < 2)
		{
			return false;
		}

		for (int i = 0; i < multilinePoints.length; i++)
		{
			if (multilinePoints[i] == null)
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Draw text on canvas by line: text starts at fisrt point directed to second.
	 * Using current font and color for drawing
	 *
	 * @param textToDraw text to draw on canvas
	 * @param linePoint1 first point of line
	 * @param linePoint2 second point of line
	 * @throws IllegalArgumentException textToDraw, linePoint1 or linePoint2 is
	 * null
	 */
	private void drawTextOnLine(String textToDraw, Point2D linePoint1,
					Point2D linePoint2) throws IllegalArgumentException
	{
		if (textToDraw == null)
		{
			throw new IllegalArgumentException();
		}
		if (linePoint1 == null)
		{
			throw new IllegalArgumentException();
		}
		if (linePoint2 == null)
		{
			throw new IllegalArgumentException();
		}

		LineSegment lineSegment = new LineSegment(new Coordinate(linePoint1.getX(), linePoint1.getY()),
						new Coordinate(linePoint2.getX(), linePoint2.getY()));
		double rotationAngle = lineSegment.angle();

		canvas.rotate(rotationAngle, linePoint1.getX(), linePoint1.getY());

		FontMetrics textFontMetrics = canvas.getFontMetrics(canvas.getFont());
		int textAscent = textFontMetrics.getAscent();
		canvas.drawString(textToDraw,
						(int) linePoint1.getX(),
						(int) (linePoint1.getY() + textAscent / 2));

		canvas.rotate(-rotationAngle, linePoint1.getX(), linePoint1.getY());
	}
}
