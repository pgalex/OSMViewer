package forms;

import com.osmviewer.drawingStyles.StandartMapObjectDrawSettings;
import com.osmviewer.drawingStyles.forms.EditDrawingStylesFrame;
import java.awt.Dialog.ModalityType;
import java.awt.Point;
import javax.swing.JFrame;
import com.osmviewer.map.onlineMap.OnlineMapController;
import com.osmviewer.mapDefenitionUtilities.MapPosition;
import com.osmviewer.rendering.RenderableMapObject;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;

/**
 * Main form
 *
 * @author preobrazhentsev
 */
public class MainFrame extends javax.swing.JFrame
{
	/**
	 * Dialog using to show information about objects on map
	 */
	private MapObjectInformationDialog mapObjectInformationDialog;
	/**
	 * Current map processor
	 */
	private OnlineMapController mapController;
	/**
	 * Previous mouse position while dragging
	 */
	private Point mouseDragPreviousPosition;

	/**
	 * Creates new main form
	 */
	public MainFrame()
	{
		initComponents();

		mapController = new OnlineMapController(new MapPosition(55.0905, 38.7788), 16,
						jPanelCanvas.getWidth(), jPanelCanvas.getHeight());

		DrawingPanel drawingPanel = (DrawingPanel) jPanelCanvas;
		drawingPanel.setPainter(mapController);

		mapObjectInformationDialog = new MapObjectInformationDialog(this, ModalityType.MODELESS);
		mapObjectInformationDialog.setAlwaysOnTop(true);
		mapObjectInformationDialog.setLocationRelativeTo(this);
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

    jPanelCanvas = new forms.DrawingPanel(null);
    jButtonReloadMap = new javax.swing.JButton();
    jButtonEditDrawingStyles = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("OpenStreetMap Viewer");

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

    jButtonReloadMap.setText("Reload map");
    jButtonReloadMap.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonReloadMapActionPerformed(evt);
      }
    });

    jButtonEditDrawingStyles.setText("How to draw map...");
    jButtonEditDrawingStyles.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonEditDrawingStylesActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanelCanvasLayout = new javax.swing.GroupLayout(jPanelCanvas);
    jPanelCanvas.setLayout(jPanelCanvasLayout);
    jPanelCanvasLayout.setHorizontalGroup(
      jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelCanvasLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jButtonReloadMap)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButtonEditDrawingStyles)
        .addContainerGap(336, Short.MAX_VALUE))
    );
    jPanelCanvasLayout.setVerticalGroup(
      jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelCanvasLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButtonReloadMap)
          .addComponent(jButtonEditDrawingStyles))
        .addContainerGap(445, Short.MAX_VALUE))
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
		jPanelCanvas.repaint();
  }//GEN-LAST:event_jPanelCanvasComponentResized

  private void jButtonReloadMapActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonReloadMapActionPerformed
  {//GEN-HEADEREND:event_jButtonReloadMapActionPerformed
		mapController.testLoadMap();
		jPanelCanvas.repaint();
  }//GEN-LAST:event_jButtonReloadMapActionPerformed

  private void jButtonEditDrawingStylesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonEditDrawingStylesActionPerformed
  {//GEN-HEADEREND:event_jButtonEditDrawingStylesActionPerformed
		EditDrawingStylesFrame editDrawingStylesDialog = new EditDrawingStylesFrame();
		editDrawingStylesDialog.setLocationRelativeTo(this);
		editDrawingStylesDialog.setVisible(true);
		editDrawingStylesDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }//GEN-LAST:event_jButtonEditDrawingStylesActionPerformed

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

		if (scaleByWheel < OnlineMapController.GetMinimumScaleLevel())
		{
			scaleByWheel = OnlineMapController.GetMinimumScaleLevel();
		}
		if (scaleByWheel > OnlineMapController.GetMaximumScaleLevel())
		{
			scaleByWheel = OnlineMapController.GetMaximumScaleLevel();
		}

		mapController.setScaleLevel(scaleByWheel);
		jPanelCanvas.repaint();
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
  private javax.swing.JButton jButtonEditDrawingStyles;
  private javax.swing.JButton jButtonReloadMap;
  private javax.swing.JPanel jPanelCanvas;
  // End of variables declaration//GEN-END:variables
}
