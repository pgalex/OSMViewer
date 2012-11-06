package drawingStyles.forms;

import ZoeloeSoft.projects.JFontChooser.JFontChooser;
import drawingStyles.DrawSettingsOnScale;
import drawingStyles.DrawSettingsOnScaleArray;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.LineDrawSettings;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.MapTag;
import drawingStyles.PointDrawSettings;
import drawingStyles.TextDrawSettings;
import drawingStyles.TextTagsKeys;
import java.awt.Color;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
	 * Values of new row in defenition tags table
	 */
	private static final String[] DEFENITION_TAGS_NEW_STRING = new String[]
	{
		"", ""
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
	 * Model for line draw settings - width spinner
	 */
	private SpinnerNumberModel lineWidthSpinnerModel;

	/**
	 * Creates dialog for editing map object draw style
	 *
	 * @param parent parent frame
	 * @param modal show as modal
	 * @param drawSettingsToEdit map object draw setting, that will be editing
	 * with dialog
	 * @throws IllegalArgumentException editing map object draw setting is null
	 */
	public JDialogEditMapObjectDrawSettings(Frame parent, boolean modal,
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
		initializeLineWidthSpinnerModel();
		initComponents();
		
		updateControlsByEditingSettings();
	}

	/**
	 * Initializing of line draw settings - width spinner
	 */
	private void initializeLineWidthSpinnerModel()
	{
		lineWidthSpinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
		lineWidthSpinnerModel.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e)
			{
				lineWidthSpinnerStateChanged(e);
			}
		});
	}

	/**
	 * Initializing of scale level spinner model
	 */
	private void initializeScaleLevelSpinnerModel()
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
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
		EditableDefenitionTags editingTags = editingMapObjectDrawSettings.getDefenitionTags();
		String tableData[][] = new String[editingTags.count()][2];
		for (int i = 0; i < editingTags.count(); i++)
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
		updateNameByEditingSettings();
		updateCanBeControlsByEditingSettings();
		updateNeedToDrawControlsBySettingsOnCurrentScale();
		updateLineColorPreviewBySettingsOnCurrentScale();
		updateLineWidthControlsBySettingsOnCurrentScale();
		updateTextPreviewBySettingsOnCurrentScale();
		updatePointIconPreviewBySettingsOnCurrentScale();
	}

	/**
	 * Update object description controls by editingMapObjectDrawSettings
	 */
	private void updateDescriptionByEditingSettings()
	{
		jTextFieldDescription.setText(editingMapObjectDrawSettings.getDescription());
	}

	/**
	 * Update object simple name controls by editingMapObjectDrawSettings
	 */
	private void updateNameByEditingSettings()
	{
		jTextFieldName.setText(editingMapObjectDrawSettings.getName());
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
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		jCheckBoxDrawPointOnScaleLevel.setSelected(settingOnCurrentScale.isDrawPoint());
		jCheckBoxDrawLineOnScaleLevel.setSelected(settingOnCurrentScale.isDrawLine());
		jCheckBoxDrawPolygonOnScaleLevel.setSelected(settingOnCurrentScale.isDrawPolygon());
	}

	/**
	 * Update line draw settings - color preview by draw settings on selected
	 * scale level
	 */
	private void updateLineColorPreviewBySettingsOnCurrentScale()
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		LineDrawSettings lineSettingsOnCurrentScale = settingOnCurrentScale.getLineDrawSettings();
		
		jPanelLinePreview.setBackground(lineSettingsOnCurrentScale.getColor());
	}

	/**
	 *
	 * Update line draw settings - width preview by draw settings on selected
	 * scale level
	 */
	private void updateLineWidthControlsBySettingsOnCurrentScale()
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		LineDrawSettings lineSettingsOnCurrentScale = settingOnCurrentScale.getLineDrawSettings();
		
		lineWidthSpinnerModel.setValue(lineSettingsOnCurrentScale.getWidth());
	}

	/**
	 * Update text preview by draw settings on selected scale level
	 */
	private void updateTextPreviewBySettingsOnCurrentScale()
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		TextDrawSettings textSettingsOnCurrentScale = settingOnCurrentScale.getTextDrawSettings();
		
		jLabelTextPreviewExample.setFont(textSettingsOnCurrentScale.getFont());
		jLabelTextPreviewExample.setForeground(textSettingsOnCurrentScale.getColor());
	}

	/**
	 * Update point draw settings icon preview by draw settings on selected scale
	 * level
	 */
	private void updatePointIconPreviewBySettingsOnCurrentScale()
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		PointDrawSettings pointSettingsOnCurrentScale = settingOnCurrentScale.getPointDrawSettings();
		
		if (pointSettingsOnCurrentScale.getIcon() != null)
		{
			jLabelPointIconPreview.setIcon(new ImageIcon(pointSettingsOnCurrentScale.getIcon()));
			jLabelPointIconPreview.setText("");
		}
		else
		{
			jLabelPointIconPreview.setIcon(null);
			jLabelPointIconPreview.setText("no icon");
		}
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
		EditableDefenitionTags editingTags = editingMapObjectDrawSettings.getDefenitionTags();
		editingTags.clear();
		for (int i = 0; i < defenitionTagsTableModel.getRowCount(); i++)
		{
			String tagKey = (String) defenitionTagsTableModel.getValueAt(i, 0);
			String tagValue = (String) defenitionTagsTableModel.getValueAt(i, 1);
			if (!tagKey.isEmpty())
			{
				editingTags.add(new MapTag(tagKey, tagValue));
			}
		}
	}

	/**
	 * Event on change in scale level spinner
	 *
	 * @param event descriptor of event
	 */
	private void scaleLevelSpinnerStateChanged(ChangeEvent event)
	{
		updateNeedToDrawControlsBySettingsOnCurrentScale();
		updateLineColorPreviewBySettingsOnCurrentScale();
		updateLineWidthControlsBySettingsOnCurrentScale();
		updateTextPreviewBySettingsOnCurrentScale();
		updatePointIconPreviewBySettingsOnCurrentScale();
	}

	/**
	 * Event on change in line width spinner
	 *
	 * @param event descriptor of event
	 */
	private void lineWidthSpinnerStateChanged(ChangeEvent event)
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		LineDrawSettings lineSettingsOnCurrentScale = settingOnCurrentScale.getLineDrawSettings();
		
		lineSettingsOnCurrentScale.setWidth(lineWidthSpinnerModel.getNumber().intValue());
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
    jLabelPointIcon = new javax.swing.JLabel();
    jButtonSelectPointIcon = new javax.swing.JButton();
    jButtonResetPointIcon = new javax.swing.JButton();
    jPanelLinePreview = new javax.swing.JPanel();
    jButtonSelectLineColor = new javax.swing.JButton();
    jSpinnerLineWidth = new javax.swing.JSpinner();
    jButtonSelectTextFont = new javax.swing.JButton();
    jButtonSelectTextColor = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jPanelPolygonPreview = new javax.swing.JPanel();
    jLabelTextPreviewExample = new javax.swing.JLabel();
    jLabelPointIconPreview = new javax.swing.JLabel();
    jButtonApplyDescription = new javax.swing.JButton();
    jButtonApplyName = new javax.swing.JButton();
    jTextFieldName = new javax.swing.JTextField();
    jLabelName = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    jLabelDescription.setText("Description");

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

    jLabelPointIcon.setText("Icon");

    jButtonSelectPointIcon.setText("Select ...");
    jButtonSelectPointIcon.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSelectPointIconActionPerformed(evt);
      }
    });

    jButtonResetPointIcon.setText("Reset");
    jButtonResetPointIcon.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonResetPointIconActionPerformed(evt);
      }
    });

    jPanelLinePreview.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

    org.jdesktop.layout.GroupLayout jPanelLinePreviewLayout = new org.jdesktop.layout.GroupLayout(jPanelLinePreview);
    jPanelLinePreview.setLayout(jPanelLinePreviewLayout);
    jPanelLinePreviewLayout.setHorizontalGroup(
      jPanelLinePreviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 42, Short.MAX_VALUE)
    );
    jPanelLinePreviewLayout.setVerticalGroup(
      jPanelLinePreviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 42, Short.MAX_VALUE)
    );

    jButtonSelectLineColor.setText("Color ...");
    jButtonSelectLineColor.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSelectLineColorActionPerformed(evt);
      }
    });

    jSpinnerLineWidth.setModel(lineWidthSpinnerModel);

    jButtonSelectTextFont.setText("Text font ...");
    jButtonSelectTextFont.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSelectTextFontActionPerformed(evt);
      }
    });

    jButtonSelectTextColor.setText("Text color ...");
    jButtonSelectTextColor.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSelectTextColorActionPerformed(evt);
      }
    });

    jLabel1.setText("Width");

    jPanelPolygonPreview.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

    org.jdesktop.layout.GroupLayout jPanelPolygonPreviewLayout = new org.jdesktop.layout.GroupLayout(jPanelPolygonPreview);
    jPanelPolygonPreview.setLayout(jPanelPolygonPreviewLayout);
    jPanelPolygonPreviewLayout.setHorizontalGroup(
      jPanelPolygonPreviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 42, Short.MAX_VALUE)
    );
    jPanelPolygonPreviewLayout.setVerticalGroup(
      jPanelPolygonPreviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(0, 42, Short.MAX_VALUE)
    );

    jLabelTextPreviewExample.setText("Sample text");

    jButtonApplyDescription.setText("Apply");
    jButtonApplyDescription.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonApplyDescriptionActionPerformed(evt);
      }
    });

    jButtonApplyName.setText("Apply");
    jButtonApplyName.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonApplyNameActionPerformed(evt);
      }
    });

    jTextFieldName.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyTyped(java.awt.event.KeyEvent evt)
      {
        jTextFieldNameKeyTyped(evt);
      }
    });

    jLabelName.setText("Name");

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
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                  .add(jCheckBoxDrawPointOnScaleLevel)
                  .add(jButtonSelectPointIcon)
                  .add(jButtonResetPointIcon)
                  .add(jLabelPointIconPreview))
                .add(62, 62, 62)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                  .add(jCheckBoxDrawLineOnScaleLevel)
                  .add(jButtonSelectLineColor)
                  .add(jPanelLinePreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                  .add(layout.createSequentialGroup()
                    .add(jLabel1)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jSpinnerLineWidth, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(65, 65, 65)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                  .add(layout.createSequentialGroup()
                    .add(jPanelPolygonPreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 82, Short.MAX_VALUE)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                      .add(jButtonSelectTextColor)
                      .add(jButtonSelectTextFont)
                      .add(jLabelTextPreviewExample)))
                  .add(jCheckBoxDrawPolygonOnScaleLevel)))
              .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                  .add(layout.createSequentialGroup()
                    .add(jLabelScaleLevel)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                    .add(jSpinnerScaleLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                  .add(jLabelPointIcon)
                  .add(layout.createSequentialGroup()
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                      .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(jLabelDescription)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldDescription, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 303, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                      .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(jCheckBoxCanBePoint)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jCheckBoxCanBeLine)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jCheckBoxCanBePolygon))
                      .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                          .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                          .add(layout.createSequentialGroup()
                            .add(jButtonAddTextTagKey)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jButtonRemoveTextTagKey)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                          .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                          .add(layout.createSequentialGroup()
                            .add(jButtonAddDefenitionTag)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jButtonRemoveDefenitionTag)))))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jButtonApplyDescription)))
                .add(0, 0, Short.MAX_VALUE)))
            .add(41, 41, 41))
          .add(layout.createSequentialGroup()
            .add(jLabelName)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jTextFieldName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonApplyName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 74, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jLabelName)
          .add(jTextFieldName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(jButtonApplyName))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
          .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(jLabelDescription)
              .add(jTextFieldDescription, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(jButtonApplyDescription))
            .add(42, 42, 42)
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
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
          .add(layout.createSequentialGroup()
            .add(jLabelPointIcon)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(jPanelLinePreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(jLabelPointIconPreview)))
          .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabelTextPreviewExample)
            .add(jPanelPolygonPreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jButtonSelectPointIcon)
          .add(jButtonSelectLineColor)
          .add(jButtonSelectTextFont))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jButtonResetPointIcon)
          .add(jSpinnerLineWidth, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
          .add(jButtonSelectTextColor)
          .add(jLabel1))
        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
		
  private void jButtonAddDefenitionTagActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonAddDefenitionTagActionPerformed
  {//GEN-HEADEREND:event_jButtonAddDefenitionTagActionPerformed
		defenitionTagsTableModel.addRow(DEFENITION_TAGS_NEW_STRING);
  }//GEN-LAST:event_jButtonAddDefenitionTagActionPerformed
	
  private void jButtonRemoveDefenitionTagActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonRemoveDefenitionTagActionPerformed
  {//GEN-HEADEREND:event_jButtonRemoveDefenitionTagActionPerformed
		int removingRowIndex = jTableDefenitionTags.getSelectedRow();
		
		if (removingRowIndex >= 0 && removingRowIndex < defenitionTagsTableModel.getRowCount())
		{
			defenitionTagsTableModel.removeRow(removingRowIndex);
		}
  }//GEN-LAST:event_jButtonRemoveDefenitionTagActionPerformed
	
  private void jCheckBoxDrawPointOnScaleLevelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxDrawPointOnScaleLevelActionPerformed
  {//GEN-HEADEREND:event_jCheckBoxDrawPointOnScaleLevelActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
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
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
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
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
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
	
  private void jButtonSelectLineColorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSelectLineColorActionPerformed
  {//GEN-HEADEREND:event_jButtonSelectLineColorActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		LineDrawSettings lineSettingsOnCurrentScale = settingOnCurrentScale.getLineDrawSettings();
		
		Color newLineColor = JColorChooser.showDialog(this, "Choosing line color", lineSettingsOnCurrentScale.getColor());
		if (newLineColor != null)
		{
			lineSettingsOnCurrentScale.setColor(newLineColor);
			updateLineColorPreviewBySettingsOnCurrentScale();
		}
  }//GEN-LAST:event_jButtonSelectLineColorActionPerformed
	
  private void jButtonSelectTextColorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSelectTextColorActionPerformed
  {//GEN-HEADEREND:event_jButtonSelectTextColorActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		TextDrawSettings textSettingsOnCurrentScale = settingOnCurrentScale.getTextDrawSettings();
		
		Color newTextColor = JColorChooser.showDialog(this, "Choosing text color", textSettingsOnCurrentScale.getColor());
		if (newTextColor != null)
		{
			textSettingsOnCurrentScale.setColor(newTextColor);
			updateTextPreviewBySettingsOnCurrentScale();
		}
  }//GEN-LAST:event_jButtonSelectTextColorActionPerformed
	
  private void jButtonResetPointIconActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonResetPointIconActionPerformed
  {//GEN-HEADEREND:event_jButtonResetPointIconActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		PointDrawSettings pointSettingsOnCurrentScale = settingOnCurrentScale.getPointDrawSettings();
		
		pointSettingsOnCurrentScale.setIcon(null);
		updatePointIconPreviewBySettingsOnCurrentScale();
  }//GEN-LAST:event_jButtonResetPointIconActionPerformed
	
  private void jButtonSelectPointIconActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSelectPointIconActionPerformed
  {//GEN-HEADEREND:event_jButtonSelectPointIconActionPerformed
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		int showDialogResult = fileChooser.showOpenDialog(this);
		if (showDialogResult == JFileChooser.APPROVE_OPTION)
		{
			BufferedImage imageFromSelectedFile;
			try
			{
				DataInputStream input = new DataInputStream(new FileInputStream(fileChooser.getSelectedFile()));
				imageFromSelectedFile = ImageIO.read(input);
				input.close();
			}
			catch (Exception e)
			{
				imageFromSelectedFile = null;
			}
			
			DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
			DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
			PointDrawSettings pointSettingsOnCurrentScale = settingOnCurrentScale.getPointDrawSettings();
			if (imageFromSelectedFile != null)
			{
				pointSettingsOnCurrentScale.setIcon(imageFromSelectedFile);
				updatePointIconPreviewBySettingsOnCurrentScale();
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Can not read image from selected file", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
  }//GEN-LAST:event_jButtonSelectPointIconActionPerformed
	
  private void jButtonSelectTextFontActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSelectTextFontActionPerformed
  {//GEN-HEADEREND:event_jButtonSelectTextFontActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		TextDrawSettings textSettingsOnCurrentScale = settingOnCurrentScale.getTextDrawSettings();
		
		JFontChooser fontChooser = new JFontChooser(null);
		int showDialogResult = fontChooser.showDialog(textSettingsOnCurrentScale.getFont());
		if (showDialogResult == JFontChooser.OK_OPTION)
		{
			textSettingsOnCurrentScale.setFont(fontChooser.getFont());
			updateTextPreviewBySettingsOnCurrentScale();
		}
  }//GEN-LAST:event_jButtonSelectTextFontActionPerformed
	
  private void jButtonApplyDescriptionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonApplyDescriptionActionPerformed
  {//GEN-HEADEREND:event_jButtonApplyDescriptionActionPerformed
		editingMapObjectDrawSettings.setDescription(jTextFieldDescription.getText());
  }//GEN-LAST:event_jButtonApplyDescriptionActionPerformed
	
  private void jButtonApplyNameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonApplyNameActionPerformed
  {//GEN-HEADEREND:event_jButtonApplyNameActionPerformed
		editingMapObjectDrawSettings.setName(jTextFieldName.getText());
  }//GEN-LAST:event_jButtonApplyNameActionPerformed
	
  private void jTextFieldNameKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldNameKeyTyped
  {//GEN-HEADEREND:event_jTextFieldNameKeyTyped
		// TODO add your handling code here:
  }//GEN-LAST:event_jTextFieldNameKeyTyped
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonAddDefenitionTag;
  private javax.swing.JButton jButtonAddTextTagKey;
  private javax.swing.JButton jButtonApplyDescription;
  private javax.swing.JButton jButtonApplyName;
  private javax.swing.JButton jButtonRemoveDefenitionTag;
  private javax.swing.JButton jButtonRemoveTextTagKey;
  private javax.swing.JButton jButtonResetPointIcon;
  private javax.swing.JButton jButtonSelectLineColor;
  private javax.swing.JButton jButtonSelectPointIcon;
  private javax.swing.JButton jButtonSelectTextColor;
  private javax.swing.JButton jButtonSelectTextFont;
  private javax.swing.JCheckBox jCheckBoxCanBeLine;
  private javax.swing.JCheckBox jCheckBoxCanBePoint;
  private javax.swing.JCheckBox jCheckBoxCanBePolygon;
  private javax.swing.JCheckBox jCheckBoxDrawLineOnScaleLevel;
  private javax.swing.JCheckBox jCheckBoxDrawPointOnScaleLevel;
  private javax.swing.JCheckBox jCheckBoxDrawPolygonOnScaleLevel;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabelDescription;
  private javax.swing.JLabel jLabelName;
  private javax.swing.JLabel jLabelPointIcon;
  private javax.swing.JLabel jLabelPointIconPreview;
  private javax.swing.JLabel jLabelScaleLevel;
  private javax.swing.JLabel jLabelTextPreviewExample;
  private javax.swing.JPanel jPanelLinePreview;
  private javax.swing.JPanel jPanelPolygonPreview;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JSpinner jSpinnerLineWidth;
  private javax.swing.JSpinner jSpinnerScaleLevel;
  private javax.swing.JTable jTableDefenitionTags;
  private javax.swing.JTable jTableTextTagKeys;
  private javax.swing.JTextField jTextFieldDescription;
  private javax.swing.JTextField jTextFieldName;
  // End of variables declaration//GEN-END:variables
}
