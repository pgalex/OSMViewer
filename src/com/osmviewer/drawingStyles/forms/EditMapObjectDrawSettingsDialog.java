package com.osmviewer.drawingStyles.forms;

import ZoeloeSoft.projects.JFontChooser.JFontChooser;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.drawingStyles.DrawSettingsOnScale;
import com.osmviewer.drawingStyles.DrawSettingsOnScaleArray;
import com.osmviewer.drawingStyles.StandartLineDrawSettings;
import com.osmviewer.drawingStyles.StandartMapObjectDrawSettings;
import com.osmviewer.drawingStyles.StandartPointDrawSettings;
import com.osmviewer.drawingStyles.StandartPolygonDrawSettings;
import com.osmviewer.drawingStyles.PolygonFillersFactory;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.drawingStyles.StandartTextDrawSettings;
import com.osmviewer.drawingStyles.TextTagsKeys;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Window;
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
public class EditMapObjectDrawSettingsDialog extends javax.swing.JDialog
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
	private StandartMapObjectDrawSettings editingMapObjectDrawSettings;
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
	 * @param parentWindow parent window
	 * @param modalityType dialog modality type
	 * @param drawSettingsToEdit map object draw setting, that will be editing
	 * with dialog
	 * @throws IllegalArgumentException editing map object draw setting is null
	 */
	public EditMapObjectDrawSettingsDialog(Window parentWindow, ModalityType modalityType,
					StandartMapObjectDrawSettings drawSettingsToEdit) throws IllegalArgumentException
	{
		super(parentWindow, modalityType);
		
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
		DefenitionTags editingTags = editingMapObjectDrawSettings.getDefenitionTags();
		String tableData[][] = new String[editingTags.count()][2];
		for (int i = 0; i < editingTags.count(); i++)
		{
			Tag tag = editingTags.get(i);
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
		updatePolygonDrawInnerAndBorderBySettingsOnCurrentScale();
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
	 * Update "need to draw" (point, line, polygon, text) controls by draw
	 * settings on selected scale level
	 */
	private void updateNeedToDrawControlsBySettingsOnCurrentScale()
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		jCheckBoxDrawPointOnScaleLevel.setSelected(settingOnCurrentScale.isDrawPoint());
		jCheckBoxDrawLineOnScaleLevel.setSelected(settingOnCurrentScale.isDrawLine());
		jCheckBoxDrawPolygonOnScaleLevel.setSelected(settingOnCurrentScale.isDrawPolygon());
		jCheckBoxDrawTextOnScaleLevel.setSelected(settingOnCurrentScale.isDrawText());
	}

	/**
	 * Update line draw settings - color preview by draw settings on selected
	 * scale level
	 */
	private void updateLineColorPreviewBySettingsOnCurrentScale()
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		StandartLineDrawSettings lineSettingsOnCurrentScale = settingOnCurrentScale.getLineDrawSettings();
		
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
		StandartLineDrawSettings lineSettingsOnCurrentScale = settingOnCurrentScale.getLineDrawSettings();
		
		lineWidthSpinnerModel.setValue(lineSettingsOnCurrentScale.getWidth());
	}

	/**
	 * Update text preview by draw settings on selected scale level
	 */
	private void updateTextPreviewBySettingsOnCurrentScale()
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		StandartTextDrawSettings textSettingsOnCurrentScale = settingOnCurrentScale.getTextDrawSettings();
		
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
		StandartPointDrawSettings pointSettingsOnCurrentScale = settingOnCurrentScale.getPointDrawSettings();
		
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
	 * Update draw polygon inner part and border controls by draw settings on
	 * selected scale level
	 */
	private void updatePolygonDrawInnerAndBorderBySettingsOnCurrentScale()
	{
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		StandartPolygonDrawSettings polygonSettingOnCurrentScale = settingOnCurrentScale.getPolygonDrawSettings();
		
		jCheckBoxDrawPolygonInnerPart.setSelected(polygonSettingOnCurrentScale.isDrawInnerPart());
		jCheckBoxDrawPolygonBorder.setSelected(polygonSettingOnCurrentScale.isDrawBorder());
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
		editingTags.clear();
		for (int i = 0; i < defenitionTagsTableModel.getRowCount(); i++)
		{
			String tagKey = (String) defenitionTagsTableModel.getValueAt(i, 0);
			String tagValue = (String) defenitionTagsTableModel.getValueAt(i, 1);
			if (!tagKey.isEmpty())
			{
				editingTags.add(new Tag(tagKey, tagValue));
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
		updatePolygonDrawInnerAndBorderBySettingsOnCurrentScale();
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
		StandartLineDrawSettings lineSettingsOnCurrentScale = settingOnCurrentScale.getLineDrawSettings();
		
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
    jButtonChoosePolygonFillColor = new javax.swing.JButton();
    jButtonChoosePolygonFillTexture = new javax.swing.JButton();
    jCheckBoxDrawPolygonInnerPart = new javax.swing.JCheckBox();
    jCheckBoxDrawPolygonBorder = new javax.swing.JCheckBox();
    jCheckBoxDrawTextOnScaleLevel = new javax.swing.JCheckBox();
    jButtonEditLinePattern = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    jLabelDescription.setText("Описание");

    jCheckBoxCanBePoint.setText("Может быть точкой");
    jCheckBoxCanBePoint.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxCanBePointActionPerformed(evt);
      }
    });

    jCheckBoxCanBeLine.setText("Может быть линией");
    jCheckBoxCanBeLine.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxCanBeLineActionPerformed(evt);
      }
    });

    jCheckBoxCanBePolygon.setText("Может быть многоугольником");
    jCheckBoxCanBePolygon.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxCanBePolygonActionPerformed(evt);
      }
    });

    jTableTextTagKeys.setModel(textTagsKeysTableModel);
    jTableTextTagKeys.setToolTipText("Теги, значения которых отображаются как текст");
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
    jTableDefenitionTags.setToolTipText("Теги");
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

    jLabelScaleLevel.setText("Масштаб");

    jSpinnerScaleLevel.setModel(scaleLevelSpinnerModel);

    jCheckBoxDrawPointOnScaleLevel.setText("Рисовать точку");
    jCheckBoxDrawPointOnScaleLevel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxDrawPointOnScaleLevelActionPerformed(evt);
      }
    });

    jCheckBoxDrawLineOnScaleLevel.setText("Рисовать линию");
    jCheckBoxDrawLineOnScaleLevel.setToolTipText("");
    jCheckBoxDrawLineOnScaleLevel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxDrawLineOnScaleLevelActionPerformed(evt);
      }
    });

    jCheckBoxDrawPolygonOnScaleLevel.setText("Рисовать многоугольник");
    jCheckBoxDrawPolygonOnScaleLevel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxDrawPolygonOnScaleLevelActionPerformed(evt);
      }
    });

    jLabelPointIcon.setText("Значок");

    jButtonSelectPointIcon.setText("Выбрать ...");
    jButtonSelectPointIcon.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSelectPointIconActionPerformed(evt);
      }
    });

    jButtonResetPointIcon.setText("Сбросить");
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

    jButtonSelectLineColor.setText("Цвет...");
    jButtonSelectLineColor.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSelectLineColorActionPerformed(evt);
      }
    });

    jSpinnerLineWidth.setModel(lineWidthSpinnerModel);

    jButtonSelectTextFont.setText("Шрифт ...");
    jButtonSelectTextFont.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSelectTextFontActionPerformed(evt);
      }
    });

    jButtonSelectTextColor.setText("Цвет ...");
    jButtonSelectTextColor.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSelectTextColorActionPerformed(evt);
      }
    });

    jLabel1.setText("Толщина");

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

    jLabelTextPreviewExample.setText("<<< Текст >>>");

    jButtonApplyDescription.setText("Применить");
    jButtonApplyDescription.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonApplyDescriptionActionPerformed(evt);
      }
    });

    jButtonApplyName.setText("Применить");
    jButtonApplyName.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonApplyNameActionPerformed(evt);
      }
    });

    jLabelName.setText("Название");

    jButtonChoosePolygonFillColor.setText("Заливка цветом...");
    jButtonChoosePolygonFillColor.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonChoosePolygonFillColorActionPerformed(evt);
      }
    });

    jButtonChoosePolygonFillTexture.setText("Заливка текстурой ...");
    jButtonChoosePolygonFillTexture.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonChoosePolygonFillTextureActionPerformed(evt);
      }
    });

    jCheckBoxDrawPolygonInnerPart.setText("Использовать заливку");
    jCheckBoxDrawPolygonInnerPart.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxDrawPolygonInnerPartActionPerformed(evt);
      }
    });

    jCheckBoxDrawPolygonBorder.setText("Рисовать границу");
    jCheckBoxDrawPolygonBorder.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxDrawPolygonBorderActionPerformed(evt);
      }
    });

    jCheckBoxDrawTextOnScaleLevel.setText("Рисовать текст");
    jCheckBoxDrawTextOnScaleLevel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jCheckBoxDrawTextOnScaleLevelActionPerformed(evt);
      }
    });

    jButtonEditLinePattern.setText("Шаблон ...");
    jButtonEditLinePattern.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonEditLinePatternActionPerformed(evt);
      }
    });

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(jCheckBoxDrawPointOnScaleLevel)
              .add(jButtonSelectPointIcon)
              .add(jButtonResetPointIcon)
              .add(jLabelPointIconPreview))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(jButtonSelectLineColor)
              .add(layout.createSequentialGroup()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSpinnerLineWidth, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
              .add(jPanelLinePreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(jCheckBoxDrawLineOnScaleLevel)
              .add(jButtonEditLinePattern))
            .add(18, 18, 18)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(jCheckBoxDrawPolygonOnScaleLevel)
              .add(jButtonChoosePolygonFillTexture)
              .add(jButtonChoosePolygonFillColor)
              .add(jPanelPolygonPreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(jCheckBoxDrawPolygonBorder))
            .add(30, 30, 30)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(jLabelTextPreviewExample)
              .add(jCheckBoxDrawTextOnScaleLevel)
              .add(jButtonSelectTextFont)
              .add(jButtonSelectTextColor)))
          .add(layout.createSequentialGroup()
            .add(jLabelScaleLevel)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jSpinnerScaleLevel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
          .add(layout.createSequentialGroup()
            .add(jLabelPointIcon)
            .add(258, 258, 258)
            .add(jCheckBoxDrawPolygonInnerPart))
          .add(layout.createSequentialGroup()
            .add(jLabelName)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jTextFieldName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonApplyName))
          .add(layout.createSequentialGroup()
            .add(jLabelDescription)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jTextFieldDescription, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 303, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonApplyDescription))
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
              .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
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
          .add(jCheckBoxDrawPolygonOnScaleLevel)
          .add(jCheckBoxDrawTextOnScaleLevel))
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
          .add(jLabelPointIcon)
          .add(jCheckBoxDrawPolygonInnerPart))
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(layout.createSequentialGroup()
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
              .add(jPanelPolygonPreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(jPanelLinePreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(jLabelPointIconPreview))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(jButtonSelectPointIcon)
              .add(jButtonSelectLineColor)
              .add(jButtonChoosePolygonFillColor, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(jButtonResetPointIcon)
              .add(jSpinnerLineWidth, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(jLabel1)
              .add(jButtonChoosePolygonFillTexture))
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(jCheckBoxDrawPolygonBorder)
              .add(jButtonEditLinePattern)))
          .add(layout.createSequentialGroup()
            .add(jLabelTextPreviewExample)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
            .add(jButtonSelectTextFont)
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(jButtonSelectTextColor)))
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
		StandartLineDrawSettings lineSettingsOnCurrentScale = settingOnCurrentScale.getLineDrawSettings();
		
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
		StandartTextDrawSettings textSettingsOnCurrentScale = settingOnCurrentScale.getTextDrawSettings();
		
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
		StandartPointDrawSettings pointSettingsOnCurrentScale = settingOnCurrentScale.getPointDrawSettings();
		
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
			StandartPointDrawSettings pointSettingsOnCurrentScale = settingOnCurrentScale.getPointDrawSettings();
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
		StandartTextDrawSettings textSettingsOnCurrentScale = settingOnCurrentScale.getTextDrawSettings();
		
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
	
  private void jButtonChoosePolygonFillColorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonChoosePolygonFillColorActionPerformed
  {//GEN-HEADEREND:event_jButtonChoosePolygonFillColorActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		StandartPolygonDrawSettings polygonSettingOnCurrentScale = settingOnCurrentScale.getPolygonDrawSettings();
		
		Color newFillColor = JColorChooser.showDialog(this, "Choosing polygon fill color", Color.BLACK);
		if (newFillColor != null)
		{
			polygonSettingOnCurrentScale.setFiller(PolygonFillersFactory.createColorFiller(newFillColor));
			// update preview
		}
  }//GEN-LAST:event_jButtonChoosePolygonFillColorActionPerformed
	
  private void jButtonChoosePolygonFillTextureActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonChoosePolygonFillTextureActionPerformed
  {//GEN-HEADEREND:event_jButtonChoosePolygonFillTextureActionPerformed
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
			StandartPolygonDrawSettings polygonSettingOnCurrentScale = settingOnCurrentScale.getPolygonDrawSettings();
			
			if (imageFromSelectedFile != null)
			{
				polygonSettingOnCurrentScale.setFiller(PolygonFillersFactory.createTextureFiller(imageFromSelectedFile));
				// update preview
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Can not read image from selected file", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
  }//GEN-LAST:event_jButtonChoosePolygonFillTextureActionPerformed
	
  private void jCheckBoxDrawPolygonInnerPartActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxDrawPolygonInnerPartActionPerformed
  {//GEN-HEADEREND:event_jCheckBoxDrawPolygonInnerPartActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		StandartPolygonDrawSettings polygonSettingOnCurrentScale = settingOnCurrentScale.getPolygonDrawSettings();
		
		if (jCheckBoxDrawPolygonInnerPart.isSelected())
		{
			polygonSettingOnCurrentScale.setDrawInnerPart();
		}
		else
		{
			polygonSettingOnCurrentScale.setNotDrawInnerPart();
		}
  }//GEN-LAST:event_jCheckBoxDrawPolygonInnerPartActionPerformed
	
  private void jCheckBoxDrawPolygonBorderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxDrawPolygonBorderActionPerformed
  {//GEN-HEADEREND:event_jCheckBoxDrawPolygonBorderActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		StandartPolygonDrawSettings polygonSettingOnCurrentScale = settingOnCurrentScale.getPolygonDrawSettings();
		
		if (jCheckBoxDrawPolygonBorder.isSelected())
		{
			polygonSettingOnCurrentScale.setDrawBorder();
		}
		else
		{
			polygonSettingOnCurrentScale.setNotDrawBorder();
		}
  }//GEN-LAST:event_jCheckBoxDrawPolygonBorderActionPerformed
	
  private void jCheckBoxDrawTextOnScaleLevelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxDrawTextOnScaleLevelActionPerformed
  {//GEN-HEADEREND:event_jCheckBoxDrawTextOnScaleLevelActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		if (jCheckBoxDrawTextOnScaleLevel.isSelected())
		{
			settingOnCurrentScale.setDrawText();
		}
		else
		{
			settingOnCurrentScale.setNotDrawText();
		}
  }//GEN-LAST:event_jCheckBoxDrawTextOnScaleLevelActionPerformed
	
  private void jButtonEditLinePatternActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonEditLinePatternActionPerformed
  {//GEN-HEADEREND:event_jButtonEditLinePatternActionPerformed
		DrawSettingsOnScaleArray editingSettingsOnScaleArray = editingMapObjectDrawSettings.getDrawSettingsOnScales();
		DrawSettingsOnScale settingOnCurrentScale = editingSettingsOnScaleArray.getDrawSettingsOnScale(scaleLevelSpinnerModel.getNumber().intValue());
		StandartLineDrawSettings lineSettingsOnCurrentScale = settingOnCurrentScale.getLineDrawSettings();
		
		EditLinePatternDialog editLinePatternDialog = new EditLinePatternDialog(this,
						Dialog.ModalityType.DOCUMENT_MODAL);
		editLinePatternDialog.setPattern(lineSettingsOnCurrentScale.getPattern());
		editLinePatternDialog.setLocationRelativeTo(this);
		
		editLinePatternDialog.setVisible(true);
		
		float[] newPattern = editLinePatternDialog.getPattern();
		if (newPattern.length > 0)
		{
			lineSettingsOnCurrentScale.setPattern(newPattern);
		}
  }//GEN-LAST:event_jButtonEditLinePatternActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonAddDefenitionTag;
  private javax.swing.JButton jButtonAddTextTagKey;
  private javax.swing.JButton jButtonApplyDescription;
  private javax.swing.JButton jButtonApplyName;
  private javax.swing.JButton jButtonChoosePolygonFillColor;
  private javax.swing.JButton jButtonChoosePolygonFillTexture;
  private javax.swing.JButton jButtonEditLinePattern;
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
  private javax.swing.JCheckBox jCheckBoxDrawPolygonBorder;
  private javax.swing.JCheckBox jCheckBoxDrawPolygonInnerPart;
  private javax.swing.JCheckBox jCheckBoxDrawPolygonOnScaleLevel;
  private javax.swing.JCheckBox jCheckBoxDrawTextOnScaleLevel;
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
