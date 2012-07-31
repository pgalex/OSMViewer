package forms;

import java.awt.Graphics;
import onlineMap.OnlineMapProcessor;

/**
 * Main form. Главное окно
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
	 * Creates new form JFrameMain
	 */
	public JFrameMain()
	{
		mapProcessor = new OnlineMapProcessor();

		initComponents();

		mapProcessor.setViewPosition(55.1990, 38.60329);
		mapProcessor.setScaleLevel(15);
		mapProcessor.setCanvasSize(jPanelCanvas.getWidth(), jPanelCanvas.getHeight());
		mapProcessor.testSetupStyleViewer();
		mapProcessor.testLoadMap();

		jSliderScaleLevel.setValue(mapProcessor.getScaleLevel());
	}

	@Override
	public void print(Graphics grphcs)
	{
		super.print(grphcs);
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

    jPanelCanvas = new JDrawingPanel(mapProcessor);
    jSliderScaleLevel = new javax.swing.JSlider();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("OpenStreetMap Viewer");

    jPanelCanvas.setBackground(new java.awt.Color(204, 204, 204));
    jPanelCanvas.setForeground(new java.awt.Color(255, 255, 255));

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

    javax.swing.GroupLayout jPanelCanvasLayout = new javax.swing.GroupLayout(jPanelCanvas);
    jPanelCanvas.setLayout(jPanelCanvasLayout);
    jPanelCanvasLayout.setHorizontalGroup(
      jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelCanvasLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jSliderScaleLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(596, Short.MAX_VALUE))
    );
    jPanelCanvasLayout.setVerticalGroup(
      jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelCanvasLayout.createSequentialGroup()
        .addComponent(jSliderScaleLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(0, 364, Short.MAX_VALUE))
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
  private javax.swing.JPanel jPanelCanvas;
  private javax.swing.JSlider jSliderScaleLevel;
  // End of variables declaration//GEN-END:variables
}
