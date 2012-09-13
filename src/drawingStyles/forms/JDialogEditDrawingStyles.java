package drawingStyles.forms;

import drawingStyles.DrawingStylesFactory;
import drawingStyles.StyleEditor;
import drawingStyles.exceptions.IncorrectParameterException;
import java.awt.Color;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;

/**
 * Dialog for editing drawing styles
 *
 * @author pgalex
 */
public class JDialogEditDrawingStyles extends javax.swing.JDialog
{
	/**
	 * Drawing styles editing with dialog
	 */
	private StyleEditor editingDrawingStyles;

	/**
	 * Creates new form with empty drawing styles
	 *
	 * @param parent dialog parent
	 * @param modal show as modal
	 */
	public JDialogEditDrawingStyles(java.awt.Frame parent, boolean modal)
	{
		super(parent, modal);
		initComponents();

		editingDrawingStyles = DrawingStylesFactory.createStyleEditor();
		updateControlsByEditingDrawingStyles();
	}

	/**
	 * Create for edit drawing styles by stylesEditor
	 *
	 * @param parent dialog parent
	 * @param modal show as modal
	 * @param drawingStylesToEdit drawing styles for editing. Must be not null
	 * @throws IncorrectParameterException drawingStylesToEdit is null
	 */
	public JDialogEditDrawingStyles(java.awt.Frame parent, boolean modal, StyleEditor drawingStylesToEdit) throws IncorrectParameterException
	{
		super(parent, modal);

		if (drawingStylesToEdit == null)
		{
			throw new IncorrectParameterException();
		}

		editingDrawingStyles = drawingStylesToEdit;
		updateControlsByEditingDrawingStyles();
	}

	/**
	 * Update values and states of all dialog controls using editingDrawingStyles
	 */
	private void updateControlsByEditingDrawingStyles()
	{
		jPanelMapBackgroudColorPreview.setBackground(editingDrawingStyles.getMapDrawSettings().getMapBackgroundColor());
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

    jLabelMapBackgroundColor = new javax.swing.JLabel();
    jPanelMapBackgroudColorPreview = new javax.swing.JPanel();
    jButtonChooseBackgroundColor = new javax.swing.JButton();
    jButtonOpen = new javax.swing.JButton();
    jButtonSave = new javax.swing.JButton();
    jButtonSaveAs = new javax.swing.JButton();
    jButtonNew = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setResizable(false);

    jLabelMapBackgroundColor.setText("Map background color");

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

    jButtonChooseBackgroundColor.setText("Choose ...");
    jButtonChooseBackgroundColor.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonChooseBackgroundColorActionPerformed(evt);
      }
    });

    jButtonOpen.setText("Open ...");

    jButtonSave.setText("Save");
    jButtonSave.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSaveActionPerformed(evt);
      }
    });

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

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(layout.createSequentialGroup()
            .add(jLabelMapBackgroundColor)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jPanelMapBackgroudColorPreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonChooseBackgroundColor))
          .add(layout.createSequentialGroup()
            .add(jButtonNew)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonOpen)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonSave)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonSaveAs)))
        .addContainerGap(111, Short.MAX_VALUE))
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
          .add(jButtonChooseBackgroundColor))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 345, Short.MAX_VALUE)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jButtonOpen)
          .add(jButtonSave)
          .add(jButtonSaveAs)
          .add(jButtonNew))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButtonChooseBackgroundColorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonChooseBackgroundColorActionPerformed
  {//GEN-HEADEREND:event_jButtonChooseBackgroundColorActionPerformed
		Color newBackgroundColor = JColorChooser.showDialog(this, "Choosing map background color",
						editingDrawingStyles.getMapDrawSettings().getMapBackgroundColor());

		if (newBackgroundColor != null)
		{
			editingDrawingStyles.getMapDrawSettings().setMapBackgroundColor(newBackgroundColor);
			jPanelMapBackgroudColorPreview.setBackground(newBackgroundColor);
		}
  }//GEN-LAST:event_jButtonChooseBackgroundColorActionPerformed

  private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonNewActionPerformed
  {//GEN-HEADEREND:event_jButtonNewActionPerformed
		editingDrawingStyles = DrawingStylesFactory.createStyleEditor();
		updateControlsByEditingDrawingStyles();
  }//GEN-LAST:event_jButtonNewActionPerformed

  private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSaveActionPerformed
  {//GEN-HEADEREND:event_jButtonSaveActionPerformed
		//
  }//GEN-LAST:event_jButtonSaveActionPerformed

  private void jButtonSaveAsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSaveAsActionPerformed
  {//GEN-HEADEREND:event_jButtonSaveAsActionPerformed
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int showDialogResult = fileChooser.showSaveDialog(this);
		if (showDialogResult == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				editingDrawingStyles.writeToFile(fileChooser.getSelectedFile());
			}
			catch (IOException ex)
			{
				Logger.getLogger(JDialogEditDrawingStyles.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
  }//GEN-LAST:event_jButtonSaveAsActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
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
			java.util.logging.Logger.getLogger(JDialogEditDrawingStyles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(JDialogEditDrawingStyles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(JDialogEditDrawingStyles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(JDialogEditDrawingStyles.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JDialogEditDrawingStyles dialog = new JDialogEditDrawingStyles(new javax.swing.JFrame(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter()
				{
					@Override
					public void windowClosing(java.awt.event.WindowEvent e)
					{
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonChooseBackgroundColor;
  private javax.swing.JButton jButtonNew;
  private javax.swing.JButton jButtonOpen;
  private javax.swing.JButton jButtonSave;
  private javax.swing.JButton jButtonSaveAs;
  private javax.swing.JLabel jLabelMapBackgroundColor;
  private javax.swing.JPanel jPanelMapBackgroudColorPreview;
  // End of variables declaration//GEN-END:variables
}