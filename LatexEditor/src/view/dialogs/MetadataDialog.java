package view.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JPanel;
import controller.LatexEditorController;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.apache.commons.io.FilenameUtils;

public class MetadataDialog extends JDialog {
	
	private LatexEditorController latexEditorController = null;
	private JLabel typeLabel = null;
	private JLabel authorLabel = null;
	private JLabel dateLabel = null;
	private JLabel pathLabel = null;
	private JTextField typeField = null;
	private JTextField authorField = null;
	private JTextField dateField = null;
	private JTextField pathField = null;
	
	public MetadataDialog(LatexEditorController latexEditorController) {
		super();
		initFrontEnd();
		initBackEnd(latexEditorController);
	}
	
	private void initFrontEnd() {
		defineDialogProperties();
		initPanel();
	}
	
	private void defineDialogProperties() {	
		setTitle("Document Properties");
		int dialogWidth = 650;
		int dialogHeight = 460;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		MetadataDialog.this.setBounds((dim.width-dialogWidth)/2,
				(dim.height-dialogHeight)/2, dialogWidth, dialogHeight);
		MetadataDialog.this.setResizable(false);
		MetadataDialog.this.setModal(true);
	}
	
	private void initPanel() {
		JPanel propertiesPanel = createPropertiesPanel();
		getContentPane().add(propertiesPanel);
	}
	
	private JPanel createPropertiesPanel() {
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setBackground(Color.DARK_GRAY);
		propertiesPanel.setBounds(0, 0, 650, 430);
		propertiesPanel.setLayout(null);
		initPropertiesJLabels();
		assignPropertiesJLabels(propertiesPanel);
		initPropertiesJTextFields();
		assignPropertiesJTextFields(propertiesPanel);
		
		return propertiesPanel;
	}
	
	private void initPropertiesJLabels() {
		typeLabel = createTypeLabel();
		authorLabel = createAuthorLabel();
		dateLabel = createDateLabel();
		pathLabel = createPathLabel();
	}
	
	private JLabel createTypeLabel() {
		JLabel typeLabel = new JLabel("Document Type:");
		typeLabel.setForeground(Color.LIGHT_GRAY);
		typeLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		typeLabel.setBounds(84, 88, 135, 33);
		return typeLabel;
	}
	
	private JLabel createAuthorLabel() {
		JLabel authorLabel = new JLabel("Author:");
		authorLabel.setForeground(Color.LIGHT_GRAY);
		authorLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		authorLabel.setBounds(84, 133, 135, 33);
		return authorLabel;
	}

	private JLabel createDateLabel() {
		JLabel dateLabel = new JLabel("Creation Date:");
		dateLabel.setForeground(Color.LIGHT_GRAY);
		dateLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		dateLabel.setBounds(84, 178, 135, 33);
		return dateLabel;
	}
	
	private JLabel createPathLabel() {
		JLabel pathLabel = new JLabel("Parent Directory:");
		pathLabel.setForeground(Color.LIGHT_GRAY);
		pathLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		pathLabel.setBounds(84, 223, 135, 33);
		return pathLabel;
	}
	
	private void assignPropertiesJLabels(JPanel preopertiesPanel) {
		preopertiesPanel.add(typeLabel);
		preopertiesPanel.add(authorLabel);
		preopertiesPanel.add(dateLabel);
		preopertiesPanel.add(pathLabel);
	}
	
	private void initPropertiesJTextFields() {
		typeField = createTypeField();
		authorField = createAuthorField();
		dateField = createDateField();
		pathField = createPathField();
	}
	
	private JTextField createTypeField() {
		JTextField typeField = new JTextField();
		typeField.setHorizontalAlignment(SwingConstants.CENTER);
		typeField.setEditable(false);
		typeField.setBounds(237, 95, 200, 19);
		typeField.setColumns(10);
		return typeField;
	}
	private JTextField createAuthorField() {
		JTextField authorField = new JTextField();
		authorField.setHorizontalAlignment(SwingConstants.CENTER);
		authorField.setEditable(false);
		authorField.setColumns(10);
		authorField.setBounds(237, 140, 200, 19);
		return authorField;
	}
	private JTextField createDateField() {
		JTextField dateField = new JTextField();
		dateField.setHorizontalAlignment(SwingConstants.CENTER);
		dateField.setEditable(false);
		dateField.setColumns(10);
		dateField.setBounds(237, 185, 200, 19);
		return dateField;
	}
	private JTextField createPathField() {
		JTextField pathField = new JTextField();
		pathField.setHorizontalAlignment(SwingConstants.CENTER);
		pathField.setEditable(false);
		pathField.setColumns(10);
		pathField.setBounds(237, 230, 200, 19);
		return pathField;
	}
	private void assignPropertiesJTextFields(JPanel preopertiesPanel) {
		preopertiesPanel.add(typeField);
		preopertiesPanel.add(authorField);
		preopertiesPanel.add(dateField);
		preopertiesPanel.add(pathField);
	}
	
	private void initBackEnd(LatexEditorController latexEditorController) {
		this.latexEditorController = latexEditorController;
	}
	
	public void updateFields() {
		typeField.setText(latexEditorController.getCurrentDocumentType());
		authorField.setText(latexEditorController.getCurrentDocumentAuthor());
		dateField.setText(latexEditorController.getCurrentDocumentDate());
		pathField.setText(FilenameUtils
				.getFullPath(latexEditorController.getCurrentDocumentPath()));
	}
}
