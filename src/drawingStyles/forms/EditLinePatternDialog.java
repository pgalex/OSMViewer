package drawingStyles.forms;

import java.awt.Window;
import javax.swing.table.DefaultTableModel;

/**
 * Dialog for editing line patter
 *
 * @author pgalex
 */
public class EditLinePatternDialog extends javax.swing.JDialog
{
	/**
	 * Headers of columns of defenition tags table
	 */
	private static final String[] PATTERN_COLUMNS_NAMES = new String[]
	{
		"Value"
	};
	/**
	 * Default table in dialog
	 */
	private static final float[] DEFAULT_LINE_PATTERN = new float[]
	{
		1.0f
	};
	/**
	 * Table model of pattern editing table
	 */
	private DefaultTableModel patternTableModel;

	/**
	 * Create new dialog
	 *
	 * @param parentWindow parent window
	 * @param modalityType modality type of dialog
	 */
	public EditLinePatternDialog(Window parentWindow, ModalityType modalityType)
	{
		super(parentWindow, modalityType);

		patternTableModel = new DefaultTableModel(PATTERN_COLUMNS_NAMES, 0);
		initComponents();

		jSliderPatternLength.setValue(DEFAULT_LINE_PATTERN.length);
		fillPatternTableByLinePattern(DEFAULT_LINE_PATTERN);
	}

	/**
	 * Set line pattern to edit with dialog
	 *
	 * @param linePatternToEdit line pattern for editing
	 * @throws IllegalArgumentException linePatternToEdit is null
	 */
	public void setPattern(float[] linePatternToEdit) throws IllegalArgumentException
	{
		if (linePatternToEdit == null)
		{
			throw new IllegalArgumentException();
		}

		jSliderPatternLength.setValue(linePatternToEdit.length);
		fillPatternTableByLinePattern(linePatternToEdit);
	}

	/**
	 * Get pattern from dialog
	 *
	 * @return pattern from dialog. Empty cell in dialog will be 0 in result
	 * pattern
	 */
	public float[] getPattern()
	{
		float[] linePatternByTable = new float[patternTableModel.getRowCount()];

		for (int i = 0; i < patternTableModel.getRowCount(); i++)
		{
			try
			{
				Float tableValue = Float.parseFloat((String)patternTableModel.getValueAt(i, 0));
				linePatternByTable[i] = tableValue;
			}
			catch(Exception ex) // can not cast to String or can not parse
			{
				linePatternByTable[i] = 0;
			}
		}

		return linePatternByTable;
	}

	/**
	 * Fill pattern table by line pattern
	 *
	 * @param linePattern line pattern using for filling
	 * @throws IllegalArgumentException linePattern is null
	 */
	private void fillPatternTableByLinePattern(float[] linePattern) throws IllegalArgumentException
	{
		if (linePattern == null)
		{
			throw new IllegalArgumentException();
		}

		patternTableModel.setRowCount(linePattern.length);
		for (int i = 0; i < linePattern.length; i++)
		{
			patternTableModel.setValueAt(Float.toString(linePattern[i]), i, 0);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    jSliderPatternLength = new javax.swing.JSlider();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTablePattern = new javax.swing.JTable();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    jSliderPatternLength.setMajorTickSpacing(1);
    jSliderPatternLength.setMaximum(10);
    jSliderPatternLength.setMinorTickSpacing(1);
    jSliderPatternLength.setPaintTicks(true);
    jSliderPatternLength.setValue(1);
    jSliderPatternLength.addChangeListener(new javax.swing.event.ChangeListener()
    {
      public void stateChanged(javax.swing.event.ChangeEvent evt)
      {
        jSliderPatternLengthStateChanged(evt);
      }
    });

    jTablePattern.setModel(patternTableModel);
    jScrollPane1.setViewportView(jTablePattern);

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(layout.createSequentialGroup()
            .add(6, 6, 6)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(jSliderPatternLength, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .add(jSliderPatternLength, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 178, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jSliderPatternLengthStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jSliderPatternLengthStateChanged
  {//GEN-HEADEREND:event_jSliderPatternLengthStateChanged
		patternTableModel.setRowCount(jSliderPatternLength.getValue());
  }//GEN-LAST:event_jSliderPatternLengthStateChanged
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JSlider jSliderPatternLength;
  private javax.swing.JTable jTablePattern;
  // End of variables declaration//GEN-END:variables
}
