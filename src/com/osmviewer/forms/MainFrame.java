package com.osmviewer.forms;

import com.osmviewer.drawingStyles.forms.EditDrawingStylesFrame;
import com.osmviewer.map.MapController;
import com.osmviewer.map.MapLoadingHandler;
import com.osmviewer.map.exceptions.FetchingErrorException;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.rendering.RenderableMapObject;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import java.awt.Dialog.ModalityType;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Main form
 *
 * @author preobrazhentsev
 */
public class MainFrame extends javax.swing.JFrame implements MapLoadingHandler
{
	/**
	 * Dialog using to show information about objects on map
	 */
	private MapObjectInformationDialog mapObjectInformationDialog;
	/**
	 * Current map processor
	 */
	private MapController mapController;
	/**
	 * Previous mouse position while dragging
	 */
	private Point mouseDragPreviousPosition;
	/**
	 * Osm conversion dialog
	 */
	private ConvertOsmXmlToSQLiteDialog convertOsmDialog;

	/**
	 * Creates new main form
	 */
	public MainFrame()
	{
		initComponents();
		fillCommandsPopupMenu();

		ImageIcon mapLoadingIcon = new ImageIcon("resources/ajax-loader-2.gif");
		jLabelLoadingIcon.setIcon(mapLoadingIcon);
		jLabelLoadingIcon.setVisible(false);

		mapController = new MapController(UserSettings.getInstance().getViewPosition(), 16,
						jPanelCanvas.getWidth(), jPanelCanvas.getHeight(), this);

		convertOsmDialog = new ConvertOsmXmlToSQLiteDialog(this, ModalityType.MODELESS);
		convertOsmDialog.setLocationRelativeTo(null);

		mapObjectInformationDialog = new MapObjectInformationDialog(this, ModalityType.MODELESS);
		mapObjectInformationDialog.setAlwaysOnTop(true);

		DrawingPanel drawingPanel = (DrawingPanel) jPanelCanvas;
		drawingPanel.setPainter(mapController);

		jSliderScaleLevel.setMinimum(mapController.getMinimumScaleLevel());
		jSliderScaleLevel.setMaximum(mapController.getMaximumScaleLevel());
		jSliderScaleLevel.setValue(mapController.getScaleLevel());
	}

	/**
	 * Fill commands popup menu with items
	 */
	private void fillCommandsPopupMenu()
	{
		jPopupMenuCommands.add(createOpenMapItem());
		jPopupMenuCommands.add(createConvertOsmItem());
		jPopupMenuCommands.add(createDrawStylesEditorItem());
		jPopupMenuCommands.add(createGoToCoordinatesItem());
	}

	/**
	 * Create "Map style editor" commands menu item
	 *
	 * @return "Map style editor" commands menu item
	 */
	private JMenuItem createDrawStylesEditorItem()
	{
		JMenuItem drawStylesEditItem = new JMenuItem("Редактор стилей ...");
		drawStylesEditItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				drawStylesEditorItemPressed();
			}
		});

		return drawStylesEditItem;
	}

	/**
	 * Create "Convert .osm" commands menu item
	 *
	 * @return "Convert .osm" commands menu item
	 */
	private JMenuItem createConvertOsmItem()
	{
		JMenuItem convertOsmItem = new JMenuItem("Конвертировать .osm ...");
		convertOsmItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				convertOsmItemPressed();
			}
		});

		return convertOsmItem;
	}

	/**
	 * Create "Open Map" commands menu item
	 *
	 * @return "Open Map" commands menu item
	 */
	private JMenuItem createOpenMapItem()
	{
		JMenuItem openMapItem = new JMenuItem("Открыть карту ...");
		openMapItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				openMapMenuItemPressed();
			}
		});

		return openMapItem;
	}

	/**
	 * Create "Go to coordinates" commands menu item
	 *
	 * @return "Go to coordinates" commands menu item
	 */
	private JMenuItem createGoToCoordinatesItem()
	{
		JMenuItem goToItem = new JMenuItem("Перейти к координатам ...");
		goToItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae)
			{
				goToCoordinatesItemPressed();
			}
		});

		return goToItem;
	}

	/**
	 * Actions on pressing "Go to coordinates" commands menu item
	 */
	private void goToCoordinatesItemPressed()
	{
		GoToCoordinatesDialog goToCoordinatesDialog = new GoToCoordinatesDialog(this, ModalityType.DOCUMENT_MODAL,
						mapController.getViewPosition());
		goToCoordinatesDialog.setVisible(true);


		if (goToCoordinatesDialog.isGoToButtonPressed())
		{
			Location newViewPosition = goToCoordinatesDialog.getGoToLocation();
			mapController.setViewPosition(newViewPosition);
			startMapLoading();
			jPanelCanvas.repaint();
		}
	}

	/**
	 * Actions on pressing "Map style editor" commands menu item
	 */
	private void drawStylesEditorItemPressed()
	{
		EditDrawingStylesFrame editDrawingStylesDialog = new EditDrawingStylesFrame();
		editDrawingStylesDialog.setLocationRelativeTo(this);
		editDrawingStylesDialog.setVisible(true);
		editDrawingStylesDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Actions on pressing "Convert osm." commands menu item
	 */
	private void convertOsmItemPressed()
	{
		convertOsmDialog.setVisible(true);
	}

	/**
	 * Actions on pressing "Open Map" commands menu item
	 */
	private void openMapMenuItemPressed()
	{
		try
		{
			JFileChooser databaseFileChooser = new JFileChooser();
			int databaseFileShowDialogResult = databaseFileChooser.showOpenDialog(this);
			if (databaseFileShowDialogResult == JFileChooser.APPROVE_OPTION)
			{
				File databaseFile = databaseFileChooser.getSelectedFile();

				mapController.setMapDataSourceByDatabase(databaseFile.getPath());
				startMapLoading();
			}
		}
		catch (DatabaseErrorExcetion ex)
		{
			JOptionPane.showMessageDialog(this, "Невозможно открыть карту", "Ошибка", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * All parts of map finished loading
	 *
	 */
	@Override
	public void wholeMapFinishedLoading()
	{
		jLabelLoadingIcon.setVisible(false);
		jPanelCanvas.repaint();
	}

	/**
	 * Part of map finished loading
	 */
	@Override
	public void partOfMapFinisheLoading()
	{
		jPanelCanvas.repaint();
	}

	/**
	 * Part of map start loading
	 */
	@Override
	public void partOfMapStartsLoading()
	{
		jLabelLoadingIcon.setVisible(true);
	}

	/**
	 * Start loading map by current view position
	 */
	private void startMapLoading()
	{
		try
		{
			mapController.loadMapByCurrentViewPosition();
			jPanelCanvas.repaint();
		}
		catch (FetchingErrorException ex)
		{
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
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

    jPopupMenuCommands = new javax.swing.JPopupMenu();
    jPanelCanvas = new com.osmviewer.forms.DrawingPanel(null);
    jSliderScaleLevel = new javax.swing.JSlider();
    jLabelLoadingIcon = new javax.swing.JLabel();
    jToggleButtonMenu = new javax.swing.JToggleButton();

    jPopupMenuCommands.addPopupMenuListener(new javax.swing.event.PopupMenuListener()
    {
      public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt)
      {
        jPopupMenuCommandsPopupMenuWillBecomeVisible(evt);
      }
      public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)
      {
        jPopupMenuCommandsPopupMenuWillBecomeInvisible(evt);
      }
      public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt)
      {
      }
    });

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("OpenStreetMap Viewer");
    addWindowListener(new java.awt.event.WindowAdapter()
    {
      public void windowClosing(java.awt.event.WindowEvent evt)
      {
        formWindowClosing(evt);
      }
    });

    jPanelCanvas.setBackground(new java.awt.Color(204, 204, 204));
    jPanelCanvas.setForeground(new java.awt.Color(255, 255, 255));
    jPanelCanvas.addMouseWheelListener(new java.awt.event.MouseWheelListener()
    {
      public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt)
      {
        jPanelCanvasMouseWheelMoved(evt);
      }
    });
    jPanelCanvas.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mousePressed(java.awt.event.MouseEvent evt)
      {
        jPanelCanvasMousePressed(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt)
      {
        jPanelCanvasMouseReleased(evt);
      }
      public void mouseClicked(java.awt.event.MouseEvent evt)
      {
        jPanelCanvasMouseClicked(evt);
      }
    });
    jPanelCanvas.addComponentListener(new java.awt.event.ComponentAdapter()
    {
      public void componentResized(java.awt.event.ComponentEvent evt)
      {
        jPanelCanvasComponentResized(evt);
      }
    });
    jPanelCanvas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
    {
      public void mouseMoved(java.awt.event.MouseEvent evt)
      {
        jPanelCanvasMouseMoved(evt);
      }
      public void mouseDragged(java.awt.event.MouseEvent evt)
      {
        jPanelCanvasMouseDragged(evt);
      }
    });

    jSliderScaleLevel.setOrientation(javax.swing.JSlider.VERTICAL);
    jSliderScaleLevel.setSnapToTicks(true);
    jSliderScaleLevel.addChangeListener(new javax.swing.event.ChangeListener()
    {
      public void stateChanged(javax.swing.event.ChangeEvent evt)
      {
        jSliderScaleLevelStateChanged(evt);
      }
    });

    jToggleButtonMenu.setText("Меню");
    jToggleButtonMenu.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jToggleButtonMenuActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanelCanvasLayout = new javax.swing.GroupLayout(jPanelCanvas);
    jPanelCanvas.setLayout(jPanelCanvasLayout);
    jPanelCanvasLayout.setHorizontalGroup(
      jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelCanvasLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jSliderScaleLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabelLoadingIcon)
          .addComponent(jToggleButtonMenu))
        .addContainerGap(538, Short.MAX_VALUE))
    );
    jPanelCanvasLayout.setVerticalGroup(
      jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelCanvasLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jToggleButtonMenu)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jSliderScaleLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 312, Short.MAX_VALUE)
        .addComponent(jLabelLoadingIcon)
        .addContainerGap())
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jPanelCanvas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jPanelCanvas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jPanelCanvasComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_jPanelCanvasComponentResized
  {//GEN-HEADEREND:event_jPanelCanvasComponentResized
		mapController.setCanvasSize(jPanelCanvas.getWidth(), jPanelCanvas.getHeight());
		startMapLoading();
  }//GEN-LAST:event_jPanelCanvasComponentResized

  private void jPanelCanvasMouseDragged(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanelCanvasMouseDragged
  {//GEN-HEADEREND:event_jPanelCanvasMouseDragged
		mapController.moveViewPositionByPixels((int) mouseDragPreviousPosition.getX() - evt.getX(),
						(int) mouseDragPreviousPosition.getY() - evt.getY());

		jPanelCanvas.repaint();

		mouseDragPreviousPosition = evt.getPoint();
  }//GEN-LAST:event_jPanelCanvasMouseDragged

  private void jPanelCanvasMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanelCanvasMousePressed
  {//GEN-HEADEREND:event_jPanelCanvasMousePressed
		mouseDragPreviousPosition = evt.getPoint();
  }//GEN-LAST:event_jPanelCanvasMousePressed

  private void jPanelCanvasMouseWheelMoved(java.awt.event.MouseWheelEvent evt)//GEN-FIRST:event_jPanelCanvasMouseWheelMoved
  {//GEN-HEADEREND:event_jPanelCanvasMouseWheelMoved
		int scaleByWheel = mapController.getScaleLevel() - evt.getWheelRotation();

		if (scaleByWheel < mapController.getMinimumScaleLevel())
		{
			scaleByWheel = mapController.getMinimumScaleLevel();
		}
		if (scaleByWheel > mapController.getMaximumScaleLevel())
		{
			scaleByWheel = mapController.getMaximumScaleLevel();
		}

		mapController.setScaleLevel(scaleByWheel);
		jSliderScaleLevel.setValue(scaleByWheel);
		startMapLoading();
  }//GEN-LAST:event_jPanelCanvasMouseWheelMoved

  private void jPanelCanvasMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanelCanvasMouseMoved
  {//GEN-HEADEREND:event_jPanelCanvasMouseMoved
		mapController.highlightTopObjectUnderPoint(evt.getPoint());
		jPanelCanvas.repaint();
  }//GEN-LAST:event_jPanelCanvasMouseMoved

  private void jPanelCanvasMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanelCanvasMouseClicked
  {//GEN-HEADEREND:event_jPanelCanvasMouseClicked
		RenderableMapObject[] objectsAtMousePosition = mapController.findObjectsAtPoint(evt.getPoint());

		if (objectsAtMousePosition.length > 0)
		{
			RenderableMapObject topDrawenObject = objectsAtMousePosition[0];
			RenderableMapObjectDrawSettings topDrawenObjectDrawSettings = topDrawenObject.getDrawSettings();
			if (topDrawenObjectDrawSettings != null)
			{
				mapController.setObjectToDrawAsSelected(topDrawenObject);
				mapObjectInformationDialog.showMapObjectInformation(topDrawenObject, topDrawenObjectDrawSettings);
			}
			else
			{
				mapObjectInformationDialog.clearInformation();
			}

			mapObjectInformationDialog.setVisible(true);
		}
		else
		{
			mapController.resetSelectedObject();
			mapObjectInformationDialog.clearInformation();
			mapObjectInformationDialog.setVisible(false);
		}

		jPanelCanvas.repaint();
  }//GEN-LAST:event_jPanelCanvasMouseClicked

  private void jSliderScaleLevelStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jSliderScaleLevelStateChanged
  {//GEN-HEADEREND:event_jSliderScaleLevelStateChanged
		if (jSliderScaleLevel.getValue() >= mapController.getMinimumScaleLevel() && jSliderScaleLevel.getValue() <= mapController.getMaximumScaleLevel())
		{
			mapController.setScaleLevel(jSliderScaleLevel.getValue());
			startMapLoading();
		}
  }//GEN-LAST:event_jSliderScaleLevelStateChanged

  private void jPanelCanvasMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanelCanvasMouseReleased
  {//GEN-HEADEREND:event_jPanelCanvasMouseReleased
		startMapLoading();
  }//GEN-LAST:event_jPanelCanvasMouseReleased

  private void jPopupMenuCommandsPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_jPopupMenuCommandsPopupMenuWillBecomeInvisible
  {//GEN-HEADEREND:event_jPopupMenuCommandsPopupMenuWillBecomeInvisible
		jToggleButtonMenu.setSelected(false);
  }//GEN-LAST:event_jPopupMenuCommandsPopupMenuWillBecomeInvisible

  private void jPopupMenuCommandsPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_jPopupMenuCommandsPopupMenuWillBecomeVisible
  {//GEN-HEADEREND:event_jPopupMenuCommandsPopupMenuWillBecomeVisible
		jToggleButtonMenu.setSelected(true);
  }//GEN-LAST:event_jPopupMenuCommandsPopupMenuWillBecomeVisible

  private void jToggleButtonMenuActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jToggleButtonMenuActionPerformed
  {//GEN-HEADEREND:event_jToggleButtonMenuActionPerformed
		jPopupMenuCommands.show(jToggleButtonMenu, jToggleButtonMenu.getLocation().x,
						jToggleButtonMenu.getLocation().y + jToggleButtonMenu.getHeight());
  }//GEN-LAST:event_jToggleButtonMenuActionPerformed

  private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
  {//GEN-HEADEREND:event_formWindowClosing
    try
		{
			UserSettings.getInstance().setViewPosition(mapController.getViewPosition());
			UserSettings.getInstance().saveToSettingsFile();
		}
		catch (IOException ex)
		{
			Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
  }//GEN-LAST:event_formWindowClosing

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{
		/*
		 * Set the Nimbus look and feel
		 */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/*
		 * Create and display the form
		 */
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				MainFrame mainFrame = new MainFrame();
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setVisible(true);
			}
		});
	}
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabelLoadingIcon;
  private javax.swing.JPanel jPanelCanvas;
  private javax.swing.JPopupMenu jPopupMenuCommands;
  private javax.swing.JSlider jSliderScaleLevel;
  private javax.swing.JToggleButton jToggleButtonMenu;
  // End of variables declaration//GEN-END:variables
}
