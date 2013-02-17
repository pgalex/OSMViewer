package drawingStyles.forms;

import drawingStyles.StandartDrawSettingsContainer;
import drawingStyles.StandartMapObjectDrawSettings;
import java.awt.Color;
import java.awt.Dialog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Frame for editing drawing styles
 *
 * @author pgalex
 */
public class EditDrawingStylesFrame extends javax.swing.JFrame
{
	/**
	 * Drawing styles editing with dialog
	 */
	private StandartDrawSettingsContainer editingDrawSettings;
	/**
	 * List model of map object jlist control
	 */
	private DefaultListModel mapObjectsListModel;
	/**
	 * Model of draw priority of selected spinner
	 */
	private SpinnerNumberModel pointDrawPriorityOfSelectedModel;
	/**
	 * Model of draw priority of selected spinner
	 */
	private SpinnerNumberModel lineDrawPriorityOfSelectedModel;
	/**
	 * Model of draw priority of selected spinner
	 */
	private SpinnerNumberModel polygonDrawPriorityOfSelectedModel;
	/**
	 * List model of draw priority list
	 */
	private DefaultListModel drawPriorityListModel;

	/**
	 * Creates new form EditDrawingStylesFrame
	 */
	public EditDrawingStylesFrame()
	{
		editingDrawSettings = new StandartDrawSettingsContainer();

		mapObjectsListModel = new DefaultListModel();

		pointDrawPriorityOfSelectedModel = new SpinnerNumberModel(0, 0, 100000, 1);
		pointDrawPriorityOfSelectedModel.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent ce)
			{
				pointDrawPriorityOfSelectedChanged(ce);
			}
		});

		lineDrawPriorityOfSelectedModel = new SpinnerNumberModel(0, 0, 100000, 1);
		lineDrawPriorityOfSelectedModel.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent ce)
			{
				lineDrawPriorityOfSelectedChanged(ce);
			}
		});
		
		polygonDrawPriorityOfSelectedModel = new SpinnerNumberModel(0, 0, 100000, 1);
		polygonDrawPriorityOfSelectedModel.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent ce)
			{
				polygonDrawPriorityOfSelectedChanged(ce);
			}
		});

		drawPriorityListModel = new DefaultListModel();

		initComponents();
		updateControlsByEditingStyles();
	}

	/**
	 * Update values and states of all dialog controls using editingDrawSettings
	 */
	private void updateControlsByEditingStyles()
	{
		updateBackgroundColorControlsByEditingStyles();
		updateMapObjectsListByEditingStyles();
		updateDrawPriorityListByEditingStyles();
	}

	/**
	 * Update background color controls by editing drawing styles
	 */
	private void updateBackgroundColorControlsByEditingStyles()
	{
		jPanelMapBackgroudColorPreview.setBackground(editingDrawSettings.getMapDrawSettings().getMapBackgroundColor());
	}

	/**
	 * Update list of map objects by editing drawing styles
	 */
	private void updateMapObjectsListByEditingStyles()
	{
		mapObjectsListModel.clear();

		for (int i = 0; i < editingDrawSettings.countOfMapObjectDrawSettings(); i++)
		{
			mapObjectsListModel.addElement(editingDrawSettings.getMapObjectDrawSettings(i));
		}
	}

	/**
	 * Update draw priority list by editing drawing styles
	 */
	private void updateDrawPriorityListByEditingStyles()
	{
		ArrayList<DrawingPriorityListItem> drawPriorityListItems = new ArrayList<DrawingPriorityListItem>();

		for (int i = 0; i < editingDrawSettings.countOfMapObjectDrawSettings(); i++)
		{
			StandartMapObjectDrawSettings drawSettings = editingDrawSettings.getMapObjectDrawSettings(i);

			drawPriorityListItems.add(new DrawingPriorityListItem(drawSettings.getPointDrawPriority(),
							drawSettings.getName() + " (point)"));

			drawPriorityListItems.add(new DrawingPriorityListItem(drawSettings.getLineDrawPriority(),
							drawSettings.getName() + " (line)"));

			drawPriorityListItems.add(new DrawingPriorityListItem(drawSettings.getPolygonDrawPriority(),
							drawSettings.getName() + " (polygon)"));
		}

		Collections.sort(drawPriorityListItems);

		drawPriorityListModel.clear();
		for (int i = 0; i < drawPriorityListItems.size(); i++)
		{
			drawPriorityListModel.addElement(drawPriorityListItems.get(i));
		}
	}

	/**
	 * Event on change in point draw priority of selected spinnner
	 *
	 * @param event description of event
	 */
	private void pointDrawPriorityOfSelectedChanged(ChangeEvent event)
	{
		Integer selectedMapObjectIndex = new Integer(jListMapObjects.getSelectedIndex());
		if (selectedMapObjectIndex < 0 || selectedMapObjectIndex > editingDrawSettings.countOfMapObjectDrawSettings())
		{
			return;
		}

		StandartMapObjectDrawSettings selectedDrawSettings = (StandartMapObjectDrawSettings) mapObjectsListModel.get(selectedMapObjectIndex);
		selectedDrawSettings.setPointDrawPriority(pointDrawPriorityOfSelectedModel.getNumber().intValue());
		updateDrawPriorityListByEditingStyles();
	}

	/**
	 * Event on change in line draw priority of selected spinnner
	 *
	 * @param event description of event
	 */
	private void lineDrawPriorityOfSelectedChanged(ChangeEvent event)
	{
		Integer selectedMapObjectIndex = new Integer(jListMapObjects.getSelectedIndex());
		if (selectedMapObjectIndex < 0 || selectedMapObjectIndex > editingDrawSettings.countOfMapObjectDrawSettings())
		{
			return;
		}

		StandartMapObjectDrawSettings selectedDrawSettings = (StandartMapObjectDrawSettings) mapObjectsListModel.get(selectedMapObjectIndex);
		selectedDrawSettings.setLineDrawPriority(lineDrawPriorityOfSelectedModel.getNumber().intValue());
		updateDrawPriorityListByEditingStyles();
	}
	
	/**
	 * Event on change in polygon draw priority of selected spinnner
	 *
	 * @param event description of event
	 */
	private void polygonDrawPriorityOfSelectedChanged(ChangeEvent event)
	{
		Integer selectedMapObjectIndex = new Integer(jListMapObjects.getSelectedIndex());
		if (selectedMapObjectIndex < 0 || selectedMapObjectIndex > editingDrawSettings.countOfMapObjectDrawSettings())
		{
			return;
		}

		StandartMapObjectDrawSettings selectedDrawSettings = (StandartMapObjectDrawSettings) mapObjectsListModel.get(selectedMapObjectIndex);
		selectedDrawSettings.setPolygonDrawPriority(polygonDrawPriorityOfSelectedModel.getNumber().intValue());
		updateDrawPriorityListByEditingStyles();
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

    jButtonSave = new javax.swing.JButton();
    jPanelMapBackgroudColorPreview = new javax.swing.JPanel();
    jLabelMapBackgroundColor = new javax.swing.JLabel();
    jButtonRemoveMapObject = new javax.swing.JButton();
    jButtonAddMapObject = new javax.swing.JButton();
    jButtonOpen = new javax.swing.JButton();
    jButtonEditMapObject = new javax.swing.JButton();
    jButtonChooseBackgroundColor = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    jListMapObjects = new javax.swing.JList();
    jButtonSaveAs = new javax.swing.JButton();
    jButtonNew = new javax.swing.JButton();
    jLabelDrawPriority = new javax.swing.JLabel();
    jSpinnerPointDrawPriorityOfSelected = new javax.swing.JSpinner();
    jScrollPane2 = new javax.swing.JScrollPane();
    jListObjectsByDrawPriority = new javax.swing.JList();
    jLabelDrawPriority1 = new javax.swing.JLabel();
    jSpinnerLineDrawPriorityOfSelected = new javax.swing.JSpinner();
    jLabelDrawPriority2 = new javax.swing.JLabel();
    jSpinnerPolygonDrawPriorityOfSelected = new javax.swing.JSpinner();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jButtonSave.setText("Save");
    jButtonSave.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSaveActionPerformed(evt);
      }
    });

    jPanelMapBackgroudColorPreview.setBorder(javax.swing.BorderFactory.createEtchedBorder());

    org.jdesktop.layout.GroupLayout jPanelMapBackgroudColorPreviewLayout = new org.jdesktop.layout.GroupLayout(jPanelMapBackgroudColorPreview);
    jPanelMapBackgroudColorPreview.setLayout(jPanelMapBackgroudColorPreviewLayout);
    jPanelMapBackgroudColorPreviewLayout.setHorizontalGroup(
      jPanelMapBackgroudColorPreviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 42, Short.MAX_VALUE)
    );
    jPanelMapBackgroudColorPreviewLayout.setVerticalGroup(
      jPanelMapBackgroudColorPreviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 35, Short.MAX_VALUE)
    );

    jLabelMapBackgroundColor.setText("Map background color");

    jButtonRemoveMapObject.setText("Remove");

    jButtonAddMapObject.setText("Add");
    jButtonAddMapObject.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonAddMapObjectActionPerformed(evt);
      }
    });

    jButtonOpen.setText("Open ...");
    jButtonOpen.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonOpenActionPerformed(evt);
      }
    });

    jButtonEditMapObject.setText("Edit");
    jButtonEditMapObject.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonEditMapObjectActionPerformed(evt);
      }
    });

    jButtonChooseBackgroundColor.setText("Choose ...");
    jButtonChooseBackgroundColor.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonChooseBackgroundColorActionPerformed(evt);
      }
    });

    jListMapObjects.setModel(mapObjectsListModel);
    jListMapObjects.addListSelectionListener(new javax.swing.event.ListSelectionListener()
    {
      public void valueChanged(javax.swing.event.ListSelectionEvent evt)
      {
        jListMapObjectsValueChanged(evt);
      }
    });
    jScrollPane1.setViewportView(jListMapObjects);

    jButtonSaveAs.setText("Save as ...");
    jButtonSaveAs.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSaveAsActionPerformed(evt);
      }
    });

    jButtonNew.setText("New");
    jButtonNew.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonNewActionPerformed(evt);
      }
    });

    jLabelDrawPriority.setText("Point draw prority");

    jSpinnerPointDrawPriorityOfSelected.setModel(pointDrawPriorityOfSelectedModel);

    jListObjectsByDrawPriority.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
    jListObjectsByDrawPriority.setModel(drawPriorityListModel);
    jScrollPane2.setViewportView(jListObjectsByDrawPriority);

    jLabelDrawPriority1.setText("Line draw prority");

    jSpinnerLineDrawPriorityOfSelected.setModel(lineDrawPriorityOfSelectedModel);

    jLabelDrawPriority2.setText("Polygon draw prority");

    jSpinnerPolygonDrawPriorityOfSelected.setModel(polygonDrawPriorityOfSelectedModel);

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
              .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1)
              .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                .add(jLabelMapBackgroundColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelMapBackgroudColorPreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonChooseBackgroundColor)))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(jScrollPane2)
              .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                  .add(layout.createSequentialGroup()
                    .add(jLabelDrawPriority)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jSpinnerPointDrawPriorityOfSelected, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                    .add(jLabelDrawPriority1)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jSpinnerLineDrawPriorityOfSelected, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                    .add(jLabelDrawPriority2)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                    .add(jSpinnerPolygonDrawPriorityOfSelected, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(0, 0, Short.MAX_VALUE))))
          .add(layout.createSequentialGroup()
            .add(jButtonNew)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonOpen)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonSave)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonSaveAs))
          .add(layout.createSequentialGroup()
            .add(jButtonAddMapObject)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonEditMapObject)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonRemoveMapObject)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
          .add(jPanelMapBackgroudColorPreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(layout.createSequentialGroup()
            .add(jLabelMapBackgroundColor)
            .add(13, 13, 13))
          .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSpinnerPointDrawPriorityOfSelected, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(jButtonChooseBackgroundColor)
              .add(jLabelDrawPriority))))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
          .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 238, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
              .add(jLabelDrawPriority1)
              .add(jSpinnerLineDrawPriorityOfSelected, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
              .add(jLabelDrawPriority2)
              .add(jSpinnerPolygonDrawPriorityOfSelected, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 18, Short.MAX_VALUE)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 167, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jButtonAddMapObject)
          .add(jButtonEditMapObject)
          .add(jButtonRemoveMapObject))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 31, Short.MAX_VALUE)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jButtonOpen)
          .add(jButtonSave)
          .add(jButtonSaveAs)
          .add(jButtonNew))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSaveActionPerformed
  {//GEN-HEADEREND:event_jButtonSaveActionPerformed
		//
  }//GEN-LAST:event_jButtonSaveActionPerformed

  private void jButtonOpenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonOpenActionPerformed
  {//GEN-HEADEREND:event_jButtonOpenActionPerformed
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int showDialogResult = fileChooser.showOpenDialog(this);
		if (showDialogResult == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				editingDrawSettings.readFromFile(fileChooser.getSelectedFile());
			}
			catch (IOException ex)
			{
				Logger.getLogger(EditDrawingStylesFrame.class.getName()).log(Level.SEVERE, null, ex);
			}

			updateControlsByEditingStyles();
		}
  }//GEN-LAST:event_jButtonOpenActionPerformed

  private void jButtonEditMapObjectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonEditMapObjectActionPerformed
  {//GEN-HEADEREND:event_jButtonEditMapObjectActionPerformed
		Integer selectedMapObjectIndex = new Integer(jListMapObjects.getSelectedIndex());
		if (selectedMapObjectIndex < 0 || selectedMapObjectIndex > editingDrawSettings.countOfMapObjectDrawSettings())
		{
			return;
		}

		StandartMapObjectDrawSettings selectedDrawSettings = (StandartMapObjectDrawSettings) mapObjectsListModel.get(selectedMapObjectIndex);

		EditMapObjectDrawSettingsDialog editDrawSettingsDialog = new EditMapObjectDrawSettingsDialog(this,
						Dialog.ModalityType.DOCUMENT_MODAL, selectedDrawSettings);
		editDrawSettingsDialog.setLocationRelativeTo(this);
		editDrawSettingsDialog.setVisible(true);
		mapObjectsListModel.setElementAt(selectedDrawSettings, selectedMapObjectIndex);
  }//GEN-LAST:event_jButtonEditMapObjectActionPerformed

  private void jButtonChooseBackgroundColorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonChooseBackgroundColorActionPerformed
  {//GEN-HEADEREND:event_jButtonChooseBackgroundColorActionPerformed
		Color newBackgroundColor = JColorChooser.showDialog(this, "Choosing map background color",
						editingDrawSettings.getMapDrawSettings().getMapBackgroundColor());

		if (newBackgroundColor != null)
		{
			editingDrawSettings.getMapDrawSettings().setMapBackgroundColor(newBackgroundColor);
			updateBackgroundColorControlsByEditingStyles();
		}
  }//GEN-LAST:event_jButtonChooseBackgroundColorActionPerformed

  private void jButtonSaveAsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSaveAsActionPerformed
  {//GEN-HEADEREND:event_jButtonSaveAsActionPerformed
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		/*FileNameExtensionFilter filter = new FileNameExtensionFilter(
		 "JPG & GIF Images", "jpg", "gif");
		 chooser.setFileFilter(filter);*/

		int showDialogResult = fileChooser.showSaveDialog(this);
		if (showDialogResult == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				editingDrawSettings.writeToFile(fileChooser.getSelectedFile());
			}
			catch (IOException ex)
			{
				Logger.getLogger(EditDrawingStylesFrame.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
  }//GEN-LAST:event_jButtonSaveAsActionPerformed

  private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonNewActionPerformed
  {//GEN-HEADEREND:event_jButtonNewActionPerformed
		editingDrawSettings = new StandartDrawSettingsContainer();
		updateControlsByEditingStyles();
  }//GEN-LAST:event_jButtonNewActionPerformed

  private void jButtonAddMapObjectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddMapObjectActionPerformed
  {//GEN-HEADEREND:event_jButtonAddMapObjectActionPerformed
		StandartMapObjectDrawSettings newDrawSettings = new StandartMapObjectDrawSettings();
		newDrawSettings.setName("new object");

		editingDrawSettings.addMapObjectDrawSettings(newDrawSettings);
		mapObjectsListModel.addElement(newDrawSettings);
		updateDrawPriorityListByEditingStyles();
  }//GEN-LAST:event_jButtonAddMapObjectActionPerformed

  private void jListMapObjectsValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListMapObjectsValueChanged
  {//GEN-HEADEREND:event_jListMapObjectsValueChanged
		Integer selectedMapObjectIndex = new Integer(jListMapObjects.getSelectedIndex());
		if (selectedMapObjectIndex < 0 || selectedMapObjectIndex > editingDrawSettings.countOfMapObjectDrawSettings())
		{
			return;
		}

		StandartMapObjectDrawSettings selectedDrawSettings = (StandartMapObjectDrawSettings) mapObjectsListModel.get(selectedMapObjectIndex);
		pointDrawPriorityOfSelectedModel.setValue(selectedDrawSettings.getPointDrawPriority());
		lineDrawPriorityOfSelectedModel.setValue(selectedDrawSettings.getLineDrawPriority());
		polygonDrawPriorityOfSelectedModel.setValue(selectedDrawSettings.getPolygonDrawPriority());
  }//GEN-LAST:event_jListMapObjectsValueChanged
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonAddMapObject;
  private javax.swing.JButton jButtonChooseBackgroundColor;
  private javax.swing.JButton jButtonEditMapObject;
  private javax.swing.JButton jButtonNew;
  private javax.swing.JButton jButtonOpen;
  private javax.swing.JButton jButtonRemoveMapObject;
  private javax.swing.JButton jButtonSave;
  private javax.swing.JButton jButtonSaveAs;
  private javax.swing.JLabel jLabelDrawPriority;
  private javax.swing.JLabel jLabelDrawPriority1;
  private javax.swing.JLabel jLabelDrawPriority2;
  private javax.swing.JLabel jLabelMapBackgroundColor;
  private javax.swing.JList jListMapObjects;
  private javax.swing.JList jListObjectsByDrawPriority;
  private javax.swing.JPanel jPanelMapBackgroudColorPreview;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JSpinner jSpinnerLineDrawPriorityOfSelected;
  private javax.swing.JSpinner jSpinnerPointDrawPriorityOfSelected;
  private javax.swing.JSpinner jSpinnerPolygonDrawPriorityOfSelected;
  // End of variables declaration//GEN-END:variables
}
