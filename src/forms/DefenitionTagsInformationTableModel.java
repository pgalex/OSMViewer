package forms;

import drawingStyles.DefenitionTags;
import drawingStyles.Tag;
import javax.swing.table.DefaultTableModel;

/**
 * Table model for showing defenition tags like information
 *
 * @author pgalex
 */
public class DefenitionTagsInformationTableModel extends DefaultTableModel
{
	/**
	 * Headers of columns
	 */
	private static final String[] COLUMNS_HEADERS = new String[]
	{
		"Key", "Value"
	};

	/**
	 * Create empty table model
	 */
	public DefenitionTagsInformationTableModel()
	{
		super(new String[0][0], COLUMNS_HEADERS);
	}

	/**
	 * Fill model with defenition tags data
	 *
	 * @param tags tags, using to fill model. Must be not null
	 * @throws IllegalArgumentException tags is null
	 */
	public void fillWithDefenitionTags(DefenitionTags tags) throws IllegalArgumentException
	{
		if (tags == null)
		{
			throw new IllegalArgumentException();
		}

		setRowCount(tags.count());
		for (int i = 0; i < tags.count(); i++)
		{
			Tag tag = tags.get(i);
			setValueAt(tag.getKey(), i, 0);
			setValueAt(tag.getValue(), i, 1);
		}
	}

	@Override
	public boolean isCellEditable(int i, int i1)
	{
		return false;
	}
}
