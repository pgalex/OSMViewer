package drawingStyles.forms;

import drawingStyles.DefenitionTags;
import drawingStyles.DrawSettingsOnScale;
import drawingStyles.DrawSettingsOnScaleArray;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.MapTag;
import drawingStyles.TextTagsKeys;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 * Dialog for editing map object draw style
 *
 * @author pgalex
 */
public class JDialogEditMapObjectDrawSettings extends javax.swing.JDialog
{
	/**
	 * Headers of columns of defenition tags table
	 */
	private static final String[] DEFENITION_TAGS_TABLE_HEADERS = new String[]
	{
		"Key", "Value"
	};
	/**
	 * Headers of columns (one) of text tag keys table
	 */
	private static final String[] TEXT_TAG_KEYS_TABLE_HEADERS = new String[]
	{
		"Text tag keys"
	};
	/**
	 * Values of new row in text tags keys table
	 */
	private static final String[] TEXT_TAG_KEYS_TABLE_NEW_STRING = new String[]
	{
		""
	};
	/**
	 * map object draw setting, editing with dialog
	 */
	private MapObjectDrawSettings editingMapObjectDrawSettings;
	/**
	 * Table model of text tag keys table
	 */
	private DefaultTableModel textTagsKeysTableModel;
	/**
	 * Table model of defenition tags table
	 */
	private DefaultTableModel defenitionTagsTableModel;
	/**
	 * Model for scale level spinner
	 */
	private SpinnerNumberModel scaleLevelSpinnerModel;

	/**
	 * Creates dialog for editing map object draw style
	 *
	 * @param parent parent frame
	 * @param modal show as modal
	 * @param drawSettingsToEdit map object draw setting, that will be editing
	 * with dialog
	 * @throws IllegalArgumentException editing map object draw setting is null
	 */
	public JDialogEditMapObjectDrawSettings(java.awt.Frame parent, boolean modal,
					MapObjectDrawSettings drawSettingsToEdit) throws IllegalArgumentException
	{
		super(parent, modal);

		if (drawSettingsToEdit == null)
		{
			throw new IllegalArgumentException();
		}

		editingMapObjectDrawSettings = drawSettingsToEdit;

		initializeTextTagKeysTableMode();
		initializeDefenitionTagsTableModel();
		initializeScaleLevelSpinnerModel();
		initComponents();

		updateControlsByEditingSettings();
	}

	/**
	 * Initializing of scale level spinner model
	 */
	private void initializeScaleLevelSpinnerModel()
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getScaledStyles();
		scaleLevelSpinnerModel = new SpinnerNumberModel(editingSettingsOnScaleArray.getMaximumScaleLevel(),
						editingSettingsOnScaleArray.getMinimumScaleLevel(), editingSettingsOnScaleArray.getMaximumScaleLevel(), 1);

		scaleLevelSpinnerModel.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				scaleLevelSpinnerStateChanged(e);
			}
		});
	}

	/**
	 * Initializing of defenition tags table model
	 */
	private void initializeDefenitionTagsTableModel()
	{
		DefenitionTags editingTags = editingMapObjectDrawSettings.getDefenitionTags();
		String tableData[][] = new String[editingTags.size()][2];
		for (int i = 0; i < editingTags.size(); i++)
		{
			MapTag tag = editingTags.get(i);
			tableData[i][0] = tag.getKey();
			tableData[i][1] = tag.getValue();
		}

		defenitionTagsTableModel = new DefaultTableModel(tableData, DEFENITION_TAGS_TABLE_HEADERS);

		defenitionTagsTableModel.addTableModelListener(new TableModelListener()
		{
			@Override
			public void tableChanged(TableModelEvent e)
			{
				defenitionTagsTableModelChanged(e);
			}
		});
	}

	/**
	 * Initializing of text tag keys table model
	 */
	private void initializeTextTagKeysTableMode()
	{
		TextTagsKeys editingTextTagsKeys = editingMapObjectDrawSettings.getTextTagKeys();
		String[][] tableData = new String[editingTextTagsKeys.getKeysCount()][1];
		for (int i = 0; i < editingTextTagsKeys.getKeysCount(); i++)
		{
			tableData[i][0] = editingTextTagsKeys.getKey(i);
		}

		textTagsKeysTableModel = new DefaultTableModel(tableData, TEXT_TAG_KEYS_TABLE_HEADERS);

		textTagsKeysTableModel.addTableModelListener(new TableModelListener()
		{
			@Override
			public void tableChanged(TableModelEvent e)
			{
				textTagsKeysTableModelChanged(e);
			}
		});
	}

	/**
	 * Update all controls of dialog by editingMapObjectDrawSettings
	 */
	private void updateControlsByEditingSettings()
	{
		updateDescriptionByEditingSettings();
		updateCanBeControlsByEditingSettings();
		updateNeedToDrawControlsBySettingsOnCurrentScale();
	}

	/**
	 * Update object description controls by editingMapObjectDrawSettings
	 */
	private void updateDescriptionByEditingSettings()
	{
		jTextFieldDescription.setText(editingMapObjectDrawSettings.getDescription());
	}

	/**
	 * Update can be point, can be line and can be polygon controls by
	 * editingMapObjectDrawSettings
	 */
	private void updateCanBeControlsByEditingSettings()
	{
		jCheckBoxCanBePoint.setSelected(editingMapObjectDrawSettings.isCanBePoint());
		jCheckBoxCanBeLine.setSelected(editingMapObjectDrawSettings.isCanBeLine());
		jCheckBoxCanBePolygon.setSelected(editingMapObjectDrawSettings.isCanBePolygon());
	}

	/**
	 * Update "need to draw" (point, line or polygon) controls by draw settings on
	 * selected scale level
	 */
	private void updateNeedToDrawControlsBySettingsOnCurrentScale()
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getScaledStyles();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		jCheckBoxDrawPointOnScaleLevel.setSelected(settingOnCurrentScale.isDrawPoint());
		jCheckBoxDrawLineOnScaleLevel.setSelected(settingOnCurrentScale.isDrawLine());
		jCheckBoxDrawPolygonOnScaleLevel.setSelected(settingOnCurrentScale.isDrawPolygon());
	}

	/**
	 * Event on change in text tags keys table
	 *
	 * @param event descriptor of event
	 */
	private void textTagsKeysTableModelChanged(TableModelEvent event)
	{
		TextTagsKeys editingTextTagsKeys = editingMapObjectDrawSettings.getTextTagKeys();
		editingTextTagsKeys.removeAllKeys();

		for (int i = 0; i < textTagsKeysTableModel.getRowCount(); i++)
		{
			String tagKey = (String) textTagsKeysTableModel.getValueAt(i, 0);
			if (!tagKey.isEmpty())
			{
				editingTextTagsKeys.addKey(tagKey);
			}
		}
	}

	/**
	 * Event on change in defenition tags table
	 *
	 * @param event descriptor of event
	 */
	private void defenitionTagsTableModelChanged(TableModelEvent event)
	{
		DefenitionTags editingTags = editingMapObjectDrawSettings.getDefenitionTags();
	}

	/**
	 * Event on change in scale level spinner
	 *
	 * @param event descriptor of event
	 */
	private void scaleLevelSpinnerStateChanged(ChangeEvent event)
	{
		updateNeedToDrawControlsBySettingsOnCurrentScale();
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

    jLabelDescription = new javax.swing.JLabel();
    jTextFieldDescription = new javax.swing.JTextField();
    jCheckBoxCanBePoint = new javax.swing.JCheckBox();
    jCheckBoxCanBeLine = new javax.swing.JCheckBox();
    jCheckBoxCanBePolygon = new javax.swing.JCheckBox();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTableTextTagKeys = new javax.swing.JTable();
    jButtonAddTextTagKey = new javax.swing.JButton();
    jButtonRemoveTextTagKey = new javax.swing.JButton();
    jScrollPane2 = new javax.swing.JScrollPane();
    jTableDefenitionTags = new javax.swing.JTable();
    jButtonAddDefenitionTag = new javax.swing.JButton();
    jButtonRemoveDefenitionTag = new javax.swing.JButton();
    jLabelScaleLevel = new javax.swing.JLabel();
    jSpinnerScaleLevel = new javax.swing.JSpinner();
    jCheckBoxDrawPointOnScaleLevel = new javax.swing.JCheckBox();
    jCheckBoxDrawLineOnScaleLevel = new javax.swing.JCheckBox();
    jCheckBoxDrawPolygonOnScaleLevel = new javax.swing.JCheckBox();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    jLabelDescription.setText("Description");

    jTextFieldDescription.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jTextFieldDescriptionActionPerformed(evt);
      }
    });
    jTextFieldDescription.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyTyped(java.awt.event.KeyEvent evt)
      {
        jTextFieldDescriptionKeyTyped(evt);
      }
    });

    jCheckBoxCanBePoint.setText("Can be point");
    jCheckBoxCanBePoint.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxCanBePointActionPerformed(evt);
      }
    });

    jCheckBoxCanBeLine.setText("Can be line");
    jCheckBoxCanBeLine.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxCanBeLineActionPerformed(evt);
      }
    });

    jCheckBoxCanBePolygon.setText("Can be polygon");
    jCheckBoxCanBePolygon.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxCanBePolygonActionPerformed(evt);
      }
    });

    jTableTextTagKeys.setModel(textTagsKeysTableModel);
    jTableTextTagKeys.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jScrollPane1.setViewportView(jTableTextTagKeys);

    jButtonAddTextTagKey.setText("+");
    jButtonAddTextTagKey.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonAddTextTagKeyActionPerformed(evt);
      }
    });

    jButtonRemoveTextTagKey.setText("-");
    jButtonRemoveTextTagKey.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonRemoveTextTagKeyActionPerformed(evt);
      }
    });

    jTableDefenitionTags.setModel(defenitionTagsTableModel);
    jTableDefenitionTags.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jScrollPane2.setViewportView(jTableDefenitionTags);

    jButtonAddDefenitionTag.setText("+");
    jButtonAddDefenitionTag.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonAddDefenitionTagActionPerformed(evt);
      }
    });

    jButtonRemoveDefenitionTag.setText("-");
    jButtonRemoveDefenitionTag.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonRemoveDefenitionTagActionPerformed(evt);
      }
    });

    jLabelScaleLevel.setText("Scale level");

    jSpinnerScaleLevel.setModel(scaleLevelSpinnerModel);

    jCheckBoxDrawPointOnScaleLevel.setText("Draw point");
    jCheckBoxDrawPointOnScaleLevel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxDrawPointOnScaleLevelActionPerformed(evt);
      }
    });

    jCheckBoxDrawLineOnScaleLevel.setText("Draw line");
    jCheckBoxDrawLineOnScaleLevel.setToolTipText("");
    jCheckBoxDrawLineOnScaleLevel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxDrawLineOnScaleLevelActionPerformed(evt);
      }
    });

    jCheckBoxDrawPolygonOnScaleLevel.setText("Draw polygon");
    jCheckBoxDrawPolygonOnScaleLevel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxDrawPolygonOnScaleLevelActionPerformed(evt);
      }
    });

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(layout.createSequentialGroup()
            .add(jCheckBoxDrawPointOnScaleLevel)
            .add(18, 18, 18)
            .add(jCheckBoxDrawLineOnScaleLevel)
            .add(18, 18, 18)
            .add(jCheckBoxDrawPolygonOnScaleLevel))
          .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
            .add(layout.createSequentialGroup()
              .add(jLabelDescription)
              .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
              .add(jTextFieldDescription, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 303, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(layout.createSequentialGroup()
              .add(jCheckBoxCanBePoint)
              .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
              .add(jCheckBoxCanBeLine)
              .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
              .add(jCheckBoxCanBePolygon))
            .add(layout.createSequentialGroup()
              .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(layout.createSequentialGroup()
                  .add(jButtonAddTextTagKey)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jButtonRemoveTextTagKey)))
              .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
              .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                  .add(jButtonAddDefenitionTag)
                  .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                  .add(jButtonRemoveDefenitionTag))
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
          .add(layout.createSequentialGroup()
            .add(jLabelScaleLevel)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jSpinnerScaleLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap(118, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .add(15, 15, 15)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
          .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(jLabelDescription)
              .add(jTextFieldDescription, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(jCheckBoxCanBePoint)
              .add(jCheckBoxCanBeLine)
              .add(jCheckBoxCanBePolygon))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
            .add(jButtonAddDefenitionTag)
            .add(jButtonRemoveDefenitionTag))
          .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
            .add(jButtonAddTextTagKey)
            .add(jButtonRemoveTextTagKey)))
        .add(27, 27, 27)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jLabelScaleLevel)
          .add(jSpinnerScaleLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        .add(18, 18, 18)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jCheckBoxDrawPointOnScaleLevel)
          .add(jCheckBoxDrawLineOnScaleLevel)
          .add(jCheckBoxDrawPolygonOnScaleLevel))
        .addContainerGap(249, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButtonAddTextTagKeyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddTextTagKeyActionPerformed
  {//GEN-HEADEREND:event_jButtonAddTextTagKeyActionPerformed
		textTagsKeysTableModel.addRow(TEXT_TAG_KEYS_TABLE_NEW_STRING);
  }//GEN-LAST:event_jButtonAddTextTagKeyActionPerformed

  private void jButtonRemoveTextTagKeyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRemoveTextTagKeyActionPerformed
  {//GEN-HEADEREND:event_jButtonRemoveTextTagKeyActionPerformed
		int removingRowIndex = jTableTextTagKeys.getSelectedRow();

		if (removingRowIndex >= 0 && removingRowIndex < textTagsKeysTableModel.getRowCount())
		{
			textTagsKeysTableModel.removeRow(removingRowIndex);
		}
  }//GEN-LAST:event_jButtonRemoveTextTagKeyActionPerformed

  private void jCheckBoxCanBePointActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxCanBePointActionPerformed
  {//GEN-HEADEREND:event_jCheckBoxCanBePointActionPerformed
		if (jCheckBoxCanBePoint.isSelected())
		{
			editingMapObjectDrawSettings.setCanBePoint();
		}
		else
		{
			editingMapObjectDrawSettings.setCanNotBePoint();
		}
  }//GEN-LAST:event_jCheckBoxCanBePointActionPerformed

  private void jCheckBoxCanBeLineActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxCanBeLineActionPerformed
  {//GEN-HEADEREND:event_jCheckBoxCanBeLineActionPerformed
		if (jCheckBoxCanBeLine.isSelected())
		{
			editingMapObjectDrawSettings.setCanBeLine();
		}
		else
		{
			editingMapObjectDrawSettings.setCanNotBeLine();
		}
  }//GEN-LAST:event_jCheckBoxCanBeLineActionPerformed

  private void jCheckBoxCanBePolygonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxCanBePolygonActionPerformed
  {//GEN-HEADEREND:event_jCheckBoxCanBePolygonActionPerformed
		if (jCheckBoxCanBePolygon.isSelected())
		{
			editingMapObjectDrawSettings.setCanBePolygon();
		}
		else
		{
			editingMapObjectDrawSettings.setCanNotBePolygon();
		}
  }//GEN-LAST:event_jCheckBoxCanBePolygonActionPerformed

  private void jTextFieldDescriptionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTextFieldDescriptionActionPerformed
  {//GEN-HEADEREND:event_jTextFieldDescriptionActionPerformed
		editingMapObjectDrawSettings.setDescription(jTextFieldDescription.getText());
  }//GEN-LAST:event_jTextFieldDescriptionActionPerformed

  private void jTextFieldDescriptionKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldDescriptionKeyTyped
  {//GEN-HEADEREND:event_jTextFieldDescriptionKeyTyped
		editingMapObjectDrawSettings.setDescription(jTextFieldDescription.getText());
  }//GEN-LAST:event_jTextFieldDescriptionKeyTyped

  private void jButtonAddDefenitionTagActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddDefenitionTagActionPerformed
  {//GEN-HEADEREND:event_jButtonAddDefenitionTagActionPerformed
		// TODO add your handling code here:
  }//GEN-LAST:event_jButtonAddDefenitionTagActionPerformed

  private void jButtonRemoveDefenitionTagActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRemoveDefenitionTagActionPerformed
  {//GEN-HEADEREND:event_jButtonRemoveDefenitionTagActionPerformed
		// TODO add your handling code here:
  }//GEN-LAST:event_jButtonRemoveDefenitionTagActionPerformed

  private void jCheckBoxDrawPointOnScaleLevelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxDrawPointOnScaleLevelActionPerformed
  {//GEN-HEADEREND:event_jCheckBoxDrawPointOnScaleLevelActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getScaledStyles();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		if (jCheckBoxDrawPointOnScaleLevel.isSelected())
		{
			settingOnCurrentScale.setDrawPoint();
		}
		else
		{
			settingOnCurrentScale.setNotDrawPoint();
		}
  }//GEN-LAST:event_jCheckBoxDrawPointOnScaleLevelActionPerformed

  private void jCheckBoxDrawLineOnScaleLevelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxDrawLineOnScaleLevelActionPerformed
  {//GEN-HEADEREND:event_jCheckBoxDrawLineOnScaleLevelActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getScaledStyles();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		if (jCheckBoxDrawLineOnScaleLevel.isSelected())
		{
			settingOnCurrentScale.setDrawLine();
		}
		else
		{
			settingOnCurrentScale.setNotDrawLine();
		}
  }//GEN-LAST:event_jCheckBoxDrawLineOnScaleLevelActionPerformed

  private void jCheckBoxDrawPolygonOnScaleLevelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxDrawPolygonOnScaleLevelActionPerformed
  {//GEN-HEADEREND:event_jCheckBoxDrawPolygonOnScaleLevelActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getScaledStyles();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		if (jCheckBoxDrawPolygonOnScaleLevel.isSelected())
		{
			settingOnCurrentScale.setDrawPolygon();
		}
		else
		{
			settingOnCurrentScale.setNotDrawPolygon();
		}
  }//GEN-LAST:event_jCheckBoxDrawPolygonOnScaleLevelActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonAddDefenitionTag;
  private javax.swing.JButton jButtonAddTextTagKey;
  private javax.swing.JButton jButtonRemoveDefenitionTag;
  private javax.swing.JButton jButtonRemoveTextTagKey;
  private javax.swing.JCheckBox jCheckBoxCanBeLine;
  private javax.swing.JCheckBox jCheckBoxCanBePoint;
  private javax.swing.JCheckBox jCheckBoxCanBePolygon;
  private javax.swing.JCheckBox jCheckBoxDrawLineOnScaleLevel;
  private javax.swing.JCheckBox jCheckBoxDrawPointOnScaleLevel;
  private javax.swing.JCheckBox jCheckBoxDrawPolygonOnScaleLevel;
  private javax.swing.JLabel jLabelDescription;
  private javax.swing.JLabel jLabelScaleLevel;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JSpinner jSpinnerScaleLevel;
  private javax.swing.JTable jTableDefenitionTags;
  private javax.swing.JTable jTableTextTagKeys;
  private javax.swing.JTextField jTextFieldDescription;
  // End of variables declaration//GEN-END:variables
}
