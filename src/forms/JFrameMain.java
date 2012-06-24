/*
 * JFrameMain.java
 *
 * Created on 28.11.2011, 8:12:47
 */
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

		mapProcessor.setCanvasSize(jPanelCanvas.getWidth(), jPanelCanvas.getHeight());
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
    private void initComponents() {

        jPanelCanvas = new JDrawingPanel(mapProcessor);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OpenStreetMap Viewer");

        jPanelCanvas.setBackground(new java.awt.Color(204, 204, 204));
        jPanelCanvas.setForeground(new java.awt.Color(255, 255, 255));
        jPanelCanvas.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanelCanvasComponentResized(evt);
            }
        });

        javax.swing.GroupLayout jPanelCanvasLayout = new javax.swing.GroupLayout(jPanelCanvas);
        jPanelCanvas.setLayout(jPanelCanvasLayout);
        jPanelCanvasLayout.setHorizontalGroup(
            jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );
        jPanelCanvasLayout.setVerticalGroup(
            jPanelCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
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
		mapProcessor.setCanvasSize(jPanelCanvas.getWidth(), jPanelCanvas.getHeight());
	}//GEN-LAST:event_jPanelCanvasComponentResized

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
    // End of variables declaration//GEN-END:variables
}
