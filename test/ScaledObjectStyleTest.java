
import drawingStyle.LineDrawStyle;
import drawingStyle.PointDrawStyle;
import drawingStyle.PolygonDrawStyle;
import drawingStyle.ScaledObjectStyle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author abc
 */
public class ScaledObjectStyleTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public ScaledObjectStyleTest()
	{
	}

	/**
	 * тест создания с нулевыми аргументами
	 */
	@Test
	public void constructorTest()
	{
		ScaledObjectStyle testStyle = new ScaledObjectStyle(true, true, true, null, null, null);
		assertNotNull(testStyle.getPointStyle());
		assertNotNull(testStyle.getLineStyle());
		assertNotNull(testStyle.getPolygonStyle());
	}
	
	/**
	 * чтение запись
	 */
	@Test
	public void fileTest()
	{
		ScaledObjectStyle writingStyle = new ScaledObjectStyle(true, false, true, new PointDrawStyle(), new LineDrawStyle(),
						new PolygonDrawStyle());
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingStyle.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		//чтение
		ScaledObjectStyle readingStyle = new ScaledObjectStyle();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingStyle.readFromStream(input);
			input.close();
			assertEquals(writingStyle.isDrawLine(), readingStyle.isDrawLine());
			assertEquals(writingStyle.isDrawPoint(), readingStyle.isDrawPoint());
			assertEquals(writingStyle.isDrawPolygon(), readingStyle.isDrawPolygon());
			assertEquals(writingStyle.getPointStyle().getIcon().getImageFileName(), readingStyle.getPointStyle().getIcon().getImageFileName());
			assertEquals(writingStyle.getLineStyle().getColor(), readingStyle.getLineStyle().getColor());
			assertEquals(writingStyle.getLineStyle().getWidth(), readingStyle.getLineStyle().getWidth());
			assertEquals(writingStyle.getPolygonStyle().getBorderDrawStyle().getColor(), readingStyle.getPolygonStyle().getBorderDrawStyle().getColor());
			assertEquals(writingStyle.getPolygonStyle().getBorderDrawStyle().getWidth(), readingStyle.getPolygonStyle().getBorderDrawStyle().getWidth());
			assertEquals(writingStyle.getPolygonStyle().getFillColor(), readingStyle.getPolygonStyle().getFillColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	@BeforeClass
	public static void setUpClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}
}
