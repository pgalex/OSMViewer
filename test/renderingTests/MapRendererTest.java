package renderingTests;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.MapDrawSettings;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import mapDefenitionUtilities.MapBounds;
import mapDefenitionUtilities.MapPosition;
import static org.junit.Assert.*;
import org.junit.Test;
import rendering.MapRenderer;
import rendering.RenderableMap;
import rendering.RenderableMapObject;

/**
 * MapRenderer class tests
 *
 * @author pgalex
 */
public class MapRendererTest
{
	/**
	 * Test find object under point with null point
	 */
	@Test
	public void findObjectAtNullPointTest()
	{
		try
		{
			MapRenderer renderer = new MapRenderer(5, 10, 5);
			renderer.findObjectsAtPoint(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test find object under point if no objects was rendered
	 */
	@Test
	public void findObjectInEmptyRendererTest()
	{
		MapRenderer renderer = new MapRenderer(5, 10, 5);
		RenderableMapObject[] foundObjects = renderer.findObjectsAtPoint(new Point2D.Double(0, 0));
		assertNotNull(foundObjects);
		assertEquals(0, foundObjects.length);
	}

	/**
	 * Test creating with minimum scale level more than maximum
	 */
	@Test
	public void creatingWithMinimumScaleLevelMoreThanMaximumTest()
	{
		try
		{
			MapRenderer renderer = new MapRenderer(11, 10, 10);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with scale level less than minimum
	 */
	@Test
	public void creatingWithScaleLevelLessThanMinimumTest()
	{
		try
		{
			MapRenderer renderer = new MapRenderer(5, 10, 4);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test creating with scale level more than maximum
	 */
	@Test
	public void creatingWithScaleLevelMoreThanMaximumTest()
	{
		try
		{
			MapRenderer renderer = new MapRenderer(5, 10, 11);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test converting null point georaphics to canvas
	 */
	@Test
	public void convertingNullPointOnMapTest()
	{
		try
		{
			MapRenderer renderer = new MapRenderer(5, 10, 5);
			renderer.goegraphicsToCanvas(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null object to draw as highlighted
	 */
	@Test
	public void setNullObjectToHighlightTest()
	{
		try
		{
			MapRenderer renderer = new MapRenderer(5, 10, 5);
			renderer.setObjectToDrawAsHighlighted(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null object to draw as selected
	 */
	@Test
	public void setNullObjectToSelectTest()
	{
		try
		{
			MapRenderer renderer = new MapRenderer(5, 10, 5);
			renderer.setObjectToDrawAsSelected(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test converting null point canvas to georaphics
	 */
	@Test
	public void convertingNullPointOnCanvasTest()
	{
		try
		{
			MapRenderer renderer = new MapRenderer(5, 10, 5);
			renderer.canvasToGeographics(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test rendering null map
	 */
	@Test
	public void renderingNullMapTest()
	{
		try
		{
			MapRenderer renderer = new MapRenderer(5, 10, 5);

			BufferedImage someImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
			renderer.renderMap(null, someImage.createGraphics(), new MapDrawSettings());

			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test rendering with null target canvas
	 */
	@Test
	public void renderingOnNullCanvasTest()
	{
		try
		{
			MapRenderer renderer = new MapRenderer(5, 10, 5);

			RenderableMap map = new RenderableMapFake();
			renderer.renderMap(map, null, new MapDrawSettings());

			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test rendering with null style viewer
	 */
	@Test
	public void renderingWithStyleViewerTest()
	{
		try
		{
			MapRenderer renderer = new MapRenderer(5, 10, 5);

			BufferedImage someImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
			RenderableMap map = new RenderableMapFake();
			renderer.renderMap(map, someImage.createGraphics(), null);

			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null view position
	 */
	@Test
	public void setNullViewPositionTest()
	{
		try
		{
			MapRenderer testRenderer = new MapRenderer(0, 10, 10);
			testRenderer.setViewPosition(null);
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test scale level less than bounds
	 */
	@Test
	public void setScaleLevelLessThanBoundsTest()
	{
		try
		{
			MapRenderer testRenderer = new MapRenderer(2, 10, 10);

			testRenderer.setScaleLevel(1);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test scale level more than bounds
	 */
	@Test
	public void setScaleLevelMoreThanBoundsTest()
	{
		try
		{
			MapRenderer testRenderer = new MapRenderer(2, 10, 10);
			testRenderer.setScaleLevel(11);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing initializing drawing area with default value in default constructor
	 */
	@Test
	public void initializingDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer(0, 10, 10);
		assertNotNull(testRenderer.getTargetCanvasDrawingArea());
	}

	/**
	 * Test setting new value of drawing area
	 */
	@Test
	public void setNewDrawingAreaTest()
	{
		MapRenderer testRenderer = new MapRenderer(0, 10, 10);
		testRenderer.setTargetCanvasDrawingArea(new Rectangle(10, 10, 20, 20));
		assertEquals(new Rectangle(10, 10, 20, 20), testRenderer.getTargetCanvasDrawingArea());
	}

	/**
	 * Test setting null value of drawing area
	 */
	@Test
	public void setNullDrawingAreaTest()
	{
		try
		{
			MapRenderer testRenderer = new MapRenderer(0, 10, 10);
			testRenderer.setTargetCanvasDrawingArea(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test that view area changes with view position in assumed way
	 */
	@Test
	public void viewAreaDependesOnViewPosition()
	{
		MapRenderer renderer = new MapRenderer(10, 18, 16);

		renderer.setViewPosition(new MapPosition(0, 0));
		MapBounds viewAreaOnZero = renderer.getViewArea();

		renderer.setViewPosition(new MapPosition(45, 45));
		MapBounds viewAreaMoreZero = renderer.getViewArea();

		renderer.setViewPosition(new MapPosition(-45, -45));
		MapBounds viewAreaLessZero = renderer.getViewArea();

		assertTrue(viewAreaMoreZero.getLatitudeMinimum() > viewAreaOnZero.getLatitudeMinimum());
		assertTrue(viewAreaMoreZero.getLatitudeMaximum() > viewAreaOnZero.getLatitudeMaximum());
		assertTrue(viewAreaMoreZero.getLongitudeMinimum() > viewAreaOnZero.getLongitudeMinimum());
		assertTrue(viewAreaMoreZero.getLongitudeMaximum() > viewAreaOnZero.getLongitudeMaximum());

		assertTrue(viewAreaLessZero.getLatitudeMinimum() < viewAreaOnZero.getLatitudeMinimum());
		assertTrue(viewAreaLessZero.getLatitudeMaximum() < viewAreaOnZero.getLatitudeMaximum());
		assertTrue(viewAreaLessZero.getLongitudeMinimum() < viewAreaOnZero.getLongitudeMinimum());
		assertTrue(viewAreaLessZero.getLongitudeMaximum() < viewAreaOnZero.getLongitudeMaximum());
	}

	/**
	 * Test that view area changes with drawing area in assumed way
	 */
	@Test
	public void viewAreaDependesOnDrawingArea()
	{
		MapRenderer renderer = new MapRenderer(10, 18, 16);
		renderer.setViewPosition(new MapPosition(0, 0));

		renderer.setTargetCanvasDrawingArea(new Rectangle(0, 0, 100, 100));
		MapBounds areaOnBeforeChanging = renderer.getViewArea();

		renderer.setTargetCanvasDrawingArea(new Rectangle(0, 0, 150, 150));
		MapBounds areaOnMoreDrawingArea = renderer.getViewArea();

		renderer.setTargetCanvasDrawingArea(new Rectangle(0, 0, 50, 50));
		MapBounds areaOnLessDrawingArea = renderer.getViewArea();

		assertTrue(areaOnMoreDrawingArea.getLatitudeMinimum() < areaOnBeforeChanging.getLatitudeMinimum());
		assertTrue(areaOnMoreDrawingArea.getLatitudeMaximum() > areaOnBeforeChanging.getLatitudeMaximum());
		assertTrue(areaOnMoreDrawingArea.getLongitudeMinimum() < areaOnBeforeChanging.getLongitudeMinimum());
		assertTrue(areaOnMoreDrawingArea.getLongitudeMaximum() > areaOnBeforeChanging.getLongitudeMaximum());

		assertTrue(areaOnLessDrawingArea.getLatitudeMinimum() > areaOnBeforeChanging.getLatitudeMinimum());
		assertTrue(areaOnLessDrawingArea.getLatitudeMaximum() < areaOnBeforeChanging.getLatitudeMaximum());
		assertTrue(areaOnLessDrawingArea.getLongitudeMinimum() > areaOnBeforeChanging.getLongitudeMinimum());
		assertTrue(areaOnLessDrawingArea.getLongitudeMaximum() < areaOnBeforeChanging.getLongitudeMaximum());
	}

	/**
	 * Test converting coordinates by converting view position
	 *
	 * @param pViewPosition view position using for test
	 */
	private void testConvertingViewPosition(MapPosition pViewPosition)
	{
		MapRenderer renderer = new MapRenderer(10, 18, 14);

		renderer.setTargetCanvasDrawingArea(new Rectangle(50, 40, 100, 90));
		renderer.setViewPosition(pViewPosition);

		Point2D viewPositionOnCanvas = renderer.goegraphicsToCanvas(renderer.getViewPosition());

		assertEquals(viewPositionOnCanvas.getX(), renderer.getTargetCanvasDrawingArea().getCenterX(), 0.0001);
		assertEquals(viewPositionOnCanvas.getY(), renderer.getTargetCanvasDrawingArea().getCenterY(), 0.0001);
	}

	/**
	 * Test converting coordinates by converting and view area
	 *
	 * @param pViewPosition view position using for test
	 */
	private void testConvertingViewArea(MapPosition pViewPosition)
	{
		MapRenderer renderer = new MapRenderer(10, 18, 12);

		renderer.setTargetCanvasDrawingArea(new Rectangle(50, 40, 100, 90));
		renderer.setViewPosition(pViewPosition);

		MapPosition viewAreaLeftTop = new MapPosition(renderer.getViewArea().getLatitudeMinimum(),
						renderer.getViewArea().getLongitudeMinimum());
		Point2D viewAreaLeftTopOnCanvas = renderer.goegraphicsToCanvas(viewAreaLeftTop);

		MapPosition viewAreaRightBottom = new MapPosition(renderer.getViewArea().getLatitudeMaximum(),
						renderer.getViewArea().getLongitudeMaximum());
		Point2D viewAreaRightBottomOnCanvas = renderer.goegraphicsToCanvas(viewAreaRightBottom);

		// Y coordinate is inverted
		assertEquals(renderer.getTargetCanvasDrawingArea().getMinX(), viewAreaLeftTopOnCanvas.getX(), 0.1);
		assertEquals(renderer.getTargetCanvasDrawingArea().getMaxY(), viewAreaLeftTopOnCanvas.getY(), 0.1);

		assertEquals(renderer.getTargetCanvasDrawingArea().getMaxX(), viewAreaRightBottomOnCanvas.getX(), 0.1);
		assertEquals(renderer.getTargetCanvasDrawingArea().getMinY(), viewAreaRightBottomOnCanvas.getY(), 0.1);

	}

	/**
	 * Test converting coordinates by converting view position (coordinates more
	 * zero)
	 */
	@Test
	public void convertingViewPositionMoreZeroToCanvas()
	{
		testConvertingViewPosition(new MapPosition(45, 45));
		testConvertingViewArea(new MapPosition(45, 45));
	}

	/**
	 * Test converting coordinates by converting view position (coordinates are
	 * zero)
	 */
	@Test
	public void convertingViewPositionZeroToCanvas()
	{
		testConvertingViewPosition(new MapPosition(0, 0));
		testConvertingViewArea(new MapPosition(0, 0));
	}

	/**
	 * Test converting coordinates by converting view position (coordinates less
	 * zero)
	 */
	@Test
	public void convertingViewPositionLessZeroToCanvas()
	{
		testConvertingViewPosition(new MapPosition(-45, -45));
		testConvertingViewArea(new MapPosition(-45, -45));
	}

	/**
	 * Test converting coordinates by converting from geographics to canvas and
	 * from canvas to geographics
	 *
	 * @param pPositionOnMap position of point on map
	 */
	private void testByReconverting(MapPosition pPositionOnMap)
	{
		MapRenderer renderer = new MapRenderer(10, 18, 10);

		Point2D pointOnCanvas = renderer.goegraphicsToCanvas(pPositionOnMap);
		MapPosition reconvertedPointOnMap = renderer.canvasToGeographics(pointOnCanvas);

		assertEquals(pPositionOnMap.getLatitude(), reconvertedPointOnMap.getLatitude(), 0.000001);
		assertEquals(pPositionOnMap.getLongitude(), reconvertedPointOnMap.getLongitude(), 0.000001);
	}

	/**
	 * Test converting coordinates by point on map (coordinates more zero)
	 */
	@Test
	public void convertingPointOnMapMoreZeroToCanvas()
	{
		testByReconverting(new MapPosition(45, 45));
	}

	/**
	 * Test converting coordinates by point on map (coordinates are zero)
	 */
	@Test
	public void convertingPointOnMapZeroToCanvas()
	{
		testByReconverting(new MapPosition(0, 0));
	}

	/**
	 * Test converting coordinates by point on map (coordinates less zero)
	 */
	@Test
	public void convertingPointOnMapLessZeroToCanvas()
	{
		testByReconverting(new MapPosition(-45, -45));
	}
}
