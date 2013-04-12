package com.osmviewer.drawingStyles.forms;

import com.osmviewer.drawingStyles.StandartDrawSettingsContainer;
import com.osmviewer.drawingStyles.StandartMapObjectDrawSettings;
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

			if (drawSettings.isCanBePoint())
			{
				drawPriorityListItems.add(new DrawingPriorityListItem(drawSettings.getPointDrawPriority(),
								drawSettings.getName() + " (point)"));
			}

			if (drawSettings.isCanBeLine())
			{
				drawPriorityListItems.add(new DrawingPriorityListItem(drawSettings.getLineDrawPriority(),
								drawSettings.getName() + " (line)"));
			}

			if (drawSettings.isCanBePolygon())
			{
				drawPriorityListItems.add(new DrawingPriorityListItem(drawSettings.getPolygonDrawPriority(),
								drawSettings.getName() + " (polygon)"));
			}
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
	 * Find all draw settings that has drawPriorityToFind draw priority, and
	 * change it to newDrawPriority. If not found, not change
	 *
	 * @param drawPriorityToFind draw priority to find
	 * @param newDrawPriority draw priority that will be set to found draw
	 * settings
	 */
	private void findDrawSettingsAndChangeDrawPriority(int drawPriorityToFind, int newDrawPriority)
	{
		for (int i = 0; i < editingDrawSettings.countOfMapObjectDrawSettings(); i++)
		{
			StandartMapObjectDrawSettings drawSettings = editingDrawSettings.getMapObjectDrawSettings(i);
			if (drawSettings.getPointDrawPriority() == drawPriorityToFind)
			{
				drawSettings.setPointDrawPriority(newDrawPriority);
			}

			if (drawSettings.getLineDrawPriority() == drawPriorityToFind)
			{
				drawSettings.setLineDrawPriority(newDrawPriority);
			}

			if (drawSettings.getPolygonDrawPriority() == drawPriorityToFind)
			{
				drawSettings.setPolygonDrawPriority(newDrawPriority);
			}
		}
	}

	/**
	 * Increase draw priority for all draw settings if its draw priority more than
	 * drawPriorityToFind
	 *
	 * @param drawPriorityToFind draw priority of draw settings, more than, need
	 * to increase
	 */
	private void increaseDrawPriorityForAllObjectWithPriorityMoreThan(int drawPriorityToFind)
	{
		for (int i = 0; i < editingDrawSettings.countOfMapObjectDrawSettings(); i++)
		{
			StandartMapObjectDrawSettings drawSettings = editingDrawSettings.getMapObjectDrawSettings(i);
			if (drawSettings.getPointDrawPriority() > drawPriorityToFind)
			{
				drawSettings.setPointDrawPriority(drawSettings.getPointDrawPriority() + 1);
			}

			if (drawSettings.getLineDrawPriority() > drawPriorityToFind)
			{
				drawSettings.setLineDrawPriority(drawSettings.getLineDrawPriority() + 1);
			}

			if (drawSettings.getPolygonDrawPriority() > drawPriorityToFind)
			{
				drawSettings.setPolygonDrawPriority(drawSettings.getPolygonDrawPriority() + 1);
			}
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
    jScrollPane2 = new javax.swing.JScrollPane();
    jListObjectsByDrawPriority = new javax.swing.JList();
    jLabelDrawPriority1 = new javax.swing.JLabel();
    jLabelDrawPriority2 = new javax.swing.JLabel();
    jButtonIncreaseSelectedPointDrawPriority = new javax.swing.JButton();
    jButtonDecreaseSelectedPointDrawPriority = new javax.swing.JButton();
    jButtonIncreaseSelectedLineDrawPriority = new javax.swing.JButton();
    jButtonDecreaseSelectedLineDrawPriority = new javax.swing.JButton();
    jButtonIncreaseSelectedPolygonDrawPriority = new javax.swing.JButton();
    jButtonDecreaseSelectedPolygonDrawPriority = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

    jLabelMapBackgroundColor.setText("Цвет фона");

    jButtonRemoveMapObject.setText("Удалить");
    jButtonRemoveMapObject.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonRemoveMapObjectActionPerformed(evt);
      }
    });

    jButtonAddMapObject.setText("Добавить");
    jButtonAddMapObject.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonAddMapObjectActionPerformed(evt);
      }
    });

    jButtonOpen.setText("Открыть ...");
    jButtonOpen.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonOpenActionPerformed(evt);
      }
    });

    jButtonEditMapObject.setText("Редактировать");
    jButtonEditMapObject.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonEditMapObjectActionPerformed(evt);
      }
    });

    jButtonChooseBackgroundColor.setText("Выбрать ...");
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

    jButtonSaveAs.setText("Сохранить как ...");
    jButtonSaveAs.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSaveAsActionPerformed(evt);
      }
    });

    jButtonNew.setText("Новый");
    jButtonNew.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonNewActionPerformed(evt);
      }
    });

    jLabelDrawPriority.setText("Приоритет отрисовки (точка)");

    jListObjectsByDrawPriority.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
    jListObjectsByDrawPriority.setModel(drawPriorityListModel);
    jScrollPane2.setViewportView(jListObjectsByDrawPriority);

    jLabelDrawPriority1.setText("Приоритет отрисовки (линия)");

    jLabelDrawPriority2.setText("Приоритет отрисовки (многоугольник)");

    jButtonIncreaseSelectedPointDrawPriority.setText("+");
    jButtonIncreaseSelectedPointDrawPriority.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonIncreaseSelectedPointDrawPriorityActionPerformed(evt);
      }
    });

    jButtonDecreaseSelectedPointDrawPriority.setText("-");
    jButtonDecreaseSelectedPointDrawPriority.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonDecreaseSelectedPointDrawPriorityActionPerformed(evt);
      }
    });

    jButtonIncreaseSelectedLineDrawPriority.setText("+");
    jButtonIncreaseSelectedLineDrawPriority.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonIncreaseSelectedLineDrawPriorityActionPerformed(evt);
      }
    });

    jButtonDecreaseSelectedLineDrawPriority.setText("-");
    jButtonDecreaseSelectedLineDrawPriority.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonDecreaseSelectedLineDrawPriorityActionPerformed(evt);
      }
    });

    jButtonIncreaseSelectedPolygonDrawPriority.setText("+");
    jButtonIncreaseSelectedPolygonDrawPriority.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonIncreaseSelectedPolygonDrawPriorityActionPerformed(evt);
      }
    });

    jButtonDecreaseSelectedPolygonDrawPriority.setText("-");
    jButtonDecreaseSelectedPolygonDrawPriority.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonDecreaseSelectedPolygonDrawPriorityActionPerformed(evt);
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
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(layout.createSequentialGroup()
                .add(jLabelMapBackgroundColor)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanelMapBackgroudColorPreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonChooseBackgroundColor))
              .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 266, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(jScrollPane2)
              .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                  .add(jLabelDrawPriority)
                  .add(jLabelDrawPriority1)
                  .add(jLabelDrawPriority2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                  .add(layout.createSequentialGroup()
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                      .add(layout.createSequentialGroup()
                        .add(jButtonIncreaseSelectedLineDrawPriority, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jButtonDecreaseSelectedLineDrawPriority, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                      .add(layout.createSequentialGroup()
                        .add(jButtonIncreaseSelectedPointDrawPriority, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jButtonDecreaseSelectedPointDrawPriority, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(62, 62, 62))
                  .add(layout.createSequentialGroup()
                    .add(jButtonIncreaseSelectedPolygonDrawPriority, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 43, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jButtonDecreaseSelectedPolygonDrawPriority, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(0, 0, Short.MAX_VALUE))))))
          .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
              .add(layout.createSequentialGroup()
                .add(jButtonNew)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonOpen)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jButtonSaveAs))
              .add(layout.createSequentialGroup()
                .add(jButtonAddMapObject)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonEditMapObject)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonRemoveMapObject)))
            .add(0, 0, Short.MAX_VALUE)))
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
          .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
            .add(jButtonChooseBackgroundColor)
            .add(jLabelDrawPriority)
            .add(jButtonIncreaseSelectedPointDrawPriority)
            .add(jButtonDecreaseSelectedPointDrawPriority)))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
              .add(jLabelDrawPriority1)
              .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jButtonIncreaseSelectedLineDrawPriority)
                .add(jButtonDecreaseSelectedLineDrawPriority)))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
              .add(jLabelDrawPriority2)
              .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jButtonIncreaseSelectedPolygonDrawPriority)
                .add(jButtonDecreaseSelectedPolygonDrawPriority)))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 18, Short.MAX_VALUE)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 167, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 238, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jButtonAddMapObject)
          .add(jButtonEditMapObject)
          .add(jButtonRemoveMapObject))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 29, Short.MAX_VALUE)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jButtonOpen)
          .add(jButtonSaveAs)
          .add(jButtonNew))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

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

		final int newDrawSettingsDrawPriority = 0;
		newDrawSettings.setPointDrawPriority(newDrawSettingsDrawPriority);
		newDrawSettings.setLineDrawPriority(newDrawSettingsDrawPriority);
		newDrawSettings.setPolygonDrawPriority(newDrawSettingsDrawPriority);
		increaseDrawPriorityForAllObjectWithPriorityMoreThan(newDrawSettingsDrawPriority - 1);

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

  private void jButtonIncreaseSelectedPointDrawPriorityActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonIncreaseSelectedPointDrawPriorityActionPerformed
  {//GEN-HEADEREND:event_jButtonIncreaseSelectedPointDrawPriorityActionPerformed
		Integer selectedMapObjectIndex = new Integer(jListMapObjects.getSelectedIndex());
		if (selectedMapObjectIndex < 0 || selectedMapObjectIndex > editingDrawSettings.countOfMapObjectDrawSettings())
		{
			return;
		}
		StandartMapObjectDrawSettings selectedDrawSettings = (StandartMapObjectDrawSettings) mapObjectsListModel.get(selectedMapObjectIndex);
		int oldSelectedPointDrawPriority = selectedDrawSettings.getPointDrawPriority();
		int newSelectedPointDrawPriority = oldSelectedPointDrawPriority + 1;

		findDrawSettingsAndChangeDrawPriority(newSelectedPointDrawPriority, oldSelectedPointDrawPriority);

		selectedDrawSettings.setPointDrawPriority(newSelectedPointDrawPriority);

		updateDrawPriorityListByEditingStyles();
  }//GEN-LAST:event_jButtonIncreaseSelectedPointDrawPriorityActionPerformed

  private void jButtonDecreaseSelectedPointDrawPriorityActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonDecreaseSelectedPointDrawPriorityActionPerformed
  {//GEN-HEADEREND:event_jButtonDecreaseSelectedPointDrawPriorityActionPerformed
		Integer selectedMapObjectIndex = new Integer(jListMapObjects.getSelectedIndex());
		if (selectedMapObjectIndex < 0 || selectedMapObjectIndex > editingDrawSettings.countOfMapObjectDrawSettings())
		{
			return;
		}
		StandartMapObjectDrawSettings selectedDrawSettings = (StandartMapObjectDrawSettings) mapObjectsListModel.get(selectedMapObjectIndex);
		int oldSelectedPointDrawPriority = selectedDrawSettings.getPointDrawPriority();
		int newSelectedPointDrawPriority = oldSelectedPointDrawPriority - 1;

		findDrawSettingsAndChangeDrawPriority(newSelectedPointDrawPriority, oldSelectedPointDrawPriority);

		selectedDrawSettings.setPointDrawPriority(newSelectedPointDrawPriority);

		updateDrawPriorityListByEditingStyles();
  }//GEN-LAST:event_jButtonDecreaseSelectedPointDrawPriorityActionPerformed

  private void jButtonIncreaseSelectedLineDrawPriorityActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonIncreaseSelectedLineDrawPriorityActionPerformed
  {//GEN-HEADEREND:event_jButtonIncreaseSelectedLineDrawPriorityActionPerformed
		Integer selectedMapObjectIndex = new Integer(jListMapObjects.getSelectedIndex());
		if (selectedMapObjectIndex < 0 || selectedMapObjectIndex > editingDrawSettings.countOfMapObjectDrawSettings())
		{
			return;
		}
		StandartMapObjectDrawSettings selectedDrawSettings = (StandartMapObjectDrawSettings) mapObjectsListModel.get(selectedMapObjectIndex);
		int oldSelectedLineDrawPriority = selectedDrawSettings.getLineDrawPriority();
		int newSelectedLineDrawPriority = oldSelectedLineDrawPriority + 1;

		findDrawSettingsAndChangeDrawPriority(newSelectedLineDrawPriority, oldSelectedLineDrawPriority);

		selectedDrawSettings.setLineDrawPriority(newSelectedLineDrawPriority);

		updateDrawPriorityListByEditingStyles();
  }//GEN-LAST:event_jButtonIncreaseSelectedLineDrawPriorityActionPerformed

  private void jButtonDecreaseSelectedLineDrawPriorityActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonDecreaseSelectedLineDrawPriorityActionPerformed
  {//GEN-HEADEREND:event_jButtonDecreaseSelectedLineDrawPriorityActionPerformed
		Integer selectedMapObjectIndex = new Integer(jListMapObjects.getSelectedIndex());
		if (selectedMapObjectIndex < 0 || selectedMapObjectIndex > editingDrawSettings.countOfMapObjectDrawSettings())
		{
			return;
		}
		StandartMapObjectDrawSettings selectedDrawSettings = (StandartMapObjectDrawSettings) mapObjectsListModel.get(selectedMapObjectIndex);
		int oldSelectedLineDrawPriority = selectedDrawSettings.getLineDrawPriority();
		int newSelectedLineDrawPriority = oldSelectedLineDrawPriority - 1;

		findDrawSettingsAndChangeDrawPriority(newSelectedLineDrawPriority, oldSelectedLineDrawPriority);

		selectedDrawSettings.setLineDrawPriority(newSelectedLineDrawPriority);

		updateDrawPriorityListByEditingStyles();
  }//GEN-LAST:event_jButtonDecreaseSelectedLineDrawPriorityActionPerformed

  private void jButtonIncreaseSelectedPolygonDrawPriorityActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonIncreaseSelectedPolygonDrawPriorityActionPerformed
  {//GEN-HEADEREND:event_jButtonIncreaseSelectedPolygonDrawPriorityActionPerformed
		Integer selectedMapObjectIndex = new Integer(jListMapObjects.getSelectedIndex());
		if (selectedMapObjectIndex < 0 || selectedMapObjectIndex > editingDrawSettings.countOfMapObjectDrawSettings())
		{
			return;
		}
		StandartMapObjectDrawSettings selectedDrawSettings = (StandartMapObjectDrawSettings) mapObjectsListModel.get(selectedMapObjectIndex);
		int oldSelectedPolygonDrawPriority = selectedDrawSettings.getPolygonDrawPriority();
		int newSelectedPolygonDrawPriority = oldSelectedPolygonDrawPriority + 1;

		findDrawSettingsAndChangeDrawPriority(newSelectedPolygonDrawPriority, oldSelectedPolygonDrawPriority);

		selectedDrawSettings.setPolygonDrawPriority(newSelectedPolygonDrawPriority);

		updateDrawPriorityListByEditingStyles();
  }//GEN-LAST:event_jButtonIncreaseSelectedPolygonDrawPriorityActionPerformed

  private void jButtonDecreaseSelectedPolygonDrawPriorityActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonDecreaseSelectedPolygonDrawPriorityActionPerformed
  {//GEN-HEADEREND:event_jButtonDecreaseSelectedPolygonDrawPriorityActionPerformed
		Integer selectedMapObjectIndex = new Integer(jListMapObjects.getSelectedIndex());
		if (selectedMapObjectIndex < 0 || selectedMapObjectIndex > editingDrawSettings.countOfMapObjectDrawSettings())
		{
			return;
		}
		StandartMapObjectDrawSettings selectedDrawSettings = (StandartMapObjectDrawSettings) mapObjectsListModel.get(selectedMapObjectIndex);
		int oldSelectedPolygonDrawPriority = selectedDrawSettings.getPolygonDrawPriority();
		int newSelectedPolygonDrawPriority = oldSelectedPolygonDrawPriority - 1;

		findDrawSettingsAndChangeDrawPriority(newSelectedPolygonDrawPriority, oldSelectedPolygonDrawPriority);

		selectedDrawSettings.setPolygonDrawPriority(newSelectedPolygonDrawPriority);

		updateDrawPriorityListByEditingStyles();
  }//GEN-LAST:event_jButtonDecreaseSelectedPolygonDrawPriorityActionPerformed

  private void jButtonRemoveMapObjectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRemoveMapObjectActionPerformed
  {//GEN-HEADEREND:event_jButtonRemoveMapObjectActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_jButtonRemoveMapObjectActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonAddMapObject;
  private javax.swing.JButton jButtonChooseBackgroundColor;
  private javax.swing.JButton jButtonDecreaseSelectedLineDrawPriority;
  private javax.swing.JButton jButtonDecreaseSelectedPointDrawPriority;
  private javax.swing.JButton jButtonDecreaseSelectedPolygonDrawPriority;
  private javax.swing.JButton jButtonEditMapObject;
  private javax.swing.JButton jButtonIncreaseSelectedLineDrawPriority;
  private javax.swing.JButton jButtonIncreaseSelectedPointDrawPriority;
  private javax.swing.JButton jButtonIncreaseSelectedPolygonDrawPriority;
  private javax.swing.JButton jButtonNew;
  private javax.swing.JButton jButtonOpen;
  private javax.swing.JButton jButtonRemoveMapObject;
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
  // End of variables declaration//GEN-END:variables
}
