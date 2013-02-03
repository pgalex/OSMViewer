package formsTests;

import mapDefenitionUtilities.DefenitionTags;
import forms.DefenitionTagsInformationTableModel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of DefenitionTagsInformationTableModel class
 *
 * @author pgalex
 */
public class DefenitionTagsInformationTableModelTest
{
	/**
	 * Test filling table model with null tags
	 */
	@Test
	public void fillingWithNullDefenitionTags()
	{
		try
		{
			DefenitionTagsInformationTableModel tableModel = new DefenitionTagsInformationTableModel();
			tableModel.fillWithDefenitionTags(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test filling with empty tags
	 */
	@Test
	public void fillingWithEmptyDefenitionTags()
	{
		DefenitionTagsInformationTableModel tableModel = new DefenitionTagsInformationTableModel();
		tableModel.fillWithDefenitionTags(new DefenitionTags());
		assertEquals(0, tableModel.getRowCount());
	}
}
