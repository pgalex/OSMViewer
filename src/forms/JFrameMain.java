package forms;

import drawingStyles.forms.JDialogEditDrawingStyles;
import map.MapPosition;
import onlineMap.OnlineMapProcessor;

/**
 * Main form
 *
 * @author preobrazhentsev
 */
public class JFrameMain extends javax.swing.JFrame
{
	/**
	 * Current map processor
	 */
	private OnlineMapProcessor mapProcessor;

	/**
	 * Creates new main form
	 */
	public JFrameMain()
	{
		initComponents();
		
		mapProcessor = new OnlineMapProcessor(new MapPosition(55.19907, 38.60329), 16,
						jPanelCanvas.getWidth(), jPanelCanvas.getHeight());

		JDrawingPanel drawingPanel = (JDrawingPanel) jPanelCanvas;
		drawingPanel.setPainter(mapProcessor);
		jSliderScaleLevel.setValue(mapProcessor.getScaleLevel());
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

    jPanelCanvas = new JDrawingPanel(null);
    jSliderScaleLevel = new javax.swing.JSlider();
    jButtonMoveLeft = new javax.swing.JButton();
    jButtonMoveRight = new javax.swing.JButton();
    jButtonMoveUp = new javax.swing.JButton();
    jButtonMoveDown = new javax.swing.JButton();
    jButtonReloadMap = new javax.swing.JButton();
    jButtonEditDrawingStyles = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("OpenStreetMap Viewer");

    jPanelCanvas.setBackground(new java.awt.Color(204, 204, 204));
    jPanelCanvas.setForeground(new java.awt.Color(255, 255, 255));
    jPanelCanvas.addComponentListener(new java.awt.event.ComponentAdapter()
    {
      public void componentResized(java.awt.event.ComponentEvent evt)
      {
        jPanelCanvasComponentResized(evt);
      }
    });

    jSliderScaleLevel.setMaximum(OnlineMapProcessor.GetMaximumScaleLevel());
    jSliderScaleLevel.setMinimum(OnlineMapProcessor.GetMinimumScaleLevel());
    jSliderScaleLevel.setOrientation(javax.swing.JSlider.VERTICAL);
    jSliderScaleLevel.addChangeListener(new javax.swing.event.ChangeListener()
    {
      public void stateChanged(javax.swing.event.ChangeEvent evt)
      {
        jSliderScaleLevelStateChanged(evt);
      }
    });

    jButtonMoveLeft.setText("Left");
    jButtonMoveLeft.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonMoveLeftActionPerformed(evt);
      }
    });

    jButtonMoveRight.setText("Right");
    jButtonMoveRight.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonMoveRightActionPerformed(evt);
      }
    });

    jButtonMoveUp.setText("Up");
    jButtonMoveUp.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonMoveUpActionPerformed(evt);
      }
    });

    jButtonMoveDown.setText("Down");
    jButtonMoveDown.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonMoveDownActionPerformed(evt);
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
        .addComponent(jSliderScaleLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 0, Short.MAX_VALUE))
      .addGroup(jPanelCanvasLayout.createSequentialGroup()
        .addGroup(jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanelCanvasLayout.createSequentialGroup()
            .addComponent(jButtonMoveLeft)
            .addGap(40, 40, 40)
            .addComponent(jButtonMoveRight))
          .addGroup(jPanelCanvasLayout.createSequentialGroup()
            .addGap(56, 56, 56)
            .addComponent(jButtonMoveUp)
            .addGap(71, 71, 71)
            .addComponent(jButtonReloadMap)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButtonEditDrawingStyles))
          .addGroup(jPanelCanvasLayout.createSequentialGroup()
            .addGap(55, 55, 55)
            .addComponent(jButtonMoveDown)))
        .addContainerGap(140, Short.MAX_VALUE))
    );
    jPanelCanvasLayout.setVerticalGroup(
      jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelCanvasLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButtonMoveUp)
          .addComponent(jButtonReloadMap)
          .addComponent(jButtonEditDrawingStyles))
        .addGap(2, 2, 2)
        .addGroup(jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButtonMoveLeft)
          .addComponent(jButtonMoveRight))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButtonMoveDown)
        .addGap(22, 22, 22)
        .addComponent(jSliderScaleLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(241, Short.MAX_VALUE))
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

  private void jSliderScaleLevelStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jSliderScaleLevelStateChanged
  {//GEN-HEADEREND:event_jSliderScaleLevelStateChanged
		mapProcessor.setScaleLevel(jSliderScaleLevel.getValue());
		jPanelCanvas.repaint();
  }//GEN-LAST:event_jSliderScaleLevelStateChanged

  private void jButtonMoveLeftActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonMoveLeftActionPerformed
  {//GEN-HEADEREND:event_jButtonMoveLeftActionPerformed
    mapProcessor.moveViewPositionByPixels(-50, 0);
		jPanelCanvas.repaint();
  }//GEN-LAST:event_jButtonMoveLeftActionPerformed

  private void jButtonMoveRightActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonMoveRightActionPerformed
  {//GEN-HEADEREND:event_jButtonMoveRightActionPerformed
    mapProcessor.moveViewPositionByPixels(50, 0);
		jPanelCanvas.repaint();
  }//GEN-LAST:event_jButtonMoveRightActionPerformed

  private void jButtonMoveUpActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonMoveUpActionPerformed
  {//GEN-HEADEREND:event_jButtonMoveUpActionPerformed
    mapProcessor.moveViewPositionByPixels(0, -50);
		jPanelCanvas.repaint();
  }//GEN-LAST:event_jButtonMoveUpActionPerformed

  private void jButtonMoveDownActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonMoveDownActionPerformed
  {//GEN-HEADEREND:event_jButtonMoveDownActionPerformed
    mapProcessor.moveViewPositionByPixels(0, 50);
		jPanelCanvas.repaint();
  }//GEN-LAST:event_jButtonMoveDownActionPerformed

  private void jPanelCanvasComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_jPanelCanvasComponentResized
  {//GEN-HEADEREND:event_jPanelCanvasComponentResized
    mapProcessor.setCanvasSize(jPanelCanvas.getWidth(), jPanelCanvas.getHeight());
		jPanelCanvas.repaint();
  }//GEN-LAST:event_jPanelCanvasComponentResized

  private void jButtonReloadMapActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonReloadMapActionPerformed
  {//GEN-HEADEREND:event_jButtonReloadMapActionPerformed
    mapProcessor.testLoadMap();
		jPanelCanvas.repaint();
  }//GEN-LAST:event_jButtonReloadMapActionPerformed

  private void jButtonEditDrawingStylesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonEditDrawingStylesActionPerformed
  {//GEN-HEADEREND:event_jButtonEditDrawingStylesActionPerformed
    JDialogEditDrawingStyles editDrawingStylesDialog = new JDialogEditDrawingStyles(this, false);
		editDrawingStylesDialog.setLocationRelativeTo(this);
		editDrawingStylesDialog.setVisible(true);
  }//GEN-LAST:event_jButtonEditDrawingStylesActionPerformed

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
			java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
				JFrameMain mainFrame = new JFrameMain();
				mainFrame.setLocationRelativeTo(null);
				mainFrame.setVisible(true);
			}
		});
	}
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonEditDrawingStyles;
  private javax.swing.JButton jButtonMoveDown;
  private javax.swing.JButton jButtonMoveLeft;
  private javax.swing.JButton jButtonMoveRight;
  private javax.swing.JButton jButtonMoveUp;
  private javax.swing.JButton jButtonReloadMap;
  private javax.swing.JPanel jPanelCanvas;
  private javax.swing.JSlider jSliderScaleLevel;
  // End of variables declaration//GEN-END:variables
}
