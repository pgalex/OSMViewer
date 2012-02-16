
import drawingStyle.IOColor;
import drawingStyle.ScaledObjectStyle;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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

	@Test
	public void FileTest()
	{
		ScaledObjectStyle writingStyle = new ScaledObjectStyle();
		writingStyle.drawLine = true;
		writingStyle.drawPoint = false;
		writingStyle.drawPolygon = true;
		writingStyle.pointStyle.icon.imageFileName = "icon1.png";
		writingStyle.lineStyle.color = new IOColor(Color.LIGHT_GRAY);
		writingStyle.lineStyle.width = 11;
		writingStyle.polygonStyle.borderDrawStyle.color = new IOColor(Color.GRAY);
		writingStyle.polygonStyle.borderDrawStyle.width = 12;
		writingStyle.polygonStyle.fillColor = new IOColor(Color.WHITE);
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingStyle.WriteToStream(output);
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
			readingStyle.ReadFromStream(input);
			input.close();
			assertEquals(writingStyle.drawLine, readingStyle.drawLine);
			assertEquals(writingStyle.drawPoint, readingStyle.drawPoint);
			assertEquals(writingStyle.drawPolygon, readingStyle.drawPolygon);
			assertEquals(writingStyle.pointStyle.icon.imageFileName, readingStyle.pointStyle.icon.imageFileName);
			assertEquals(writingStyle.lineStyle.color, readingStyle.lineStyle.color);
			assertEquals(writingStyle.lineStyle.width, readingStyle.lineStyle.width);
			assertEquals(writingStyle.polygonStyle.borderDrawStyle.color, readingStyle.polygonStyle.borderDrawStyle.color);
			assertEquals(writingStyle.polygonStyle.borderDrawStyle.width, readingStyle.polygonStyle.borderDrawStyle.width);
			assertEquals(writingStyle.polygonStyle.fillColor, readingStyle.polygonStyle.fillColor);
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
