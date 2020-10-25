package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultEditorKit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import controller.LatexEditorController;
import view.dialogs.CommandDialog;
import view.dialogs.MetadataDialog;

public class MainFrame extends JFrame {

	private String workingDirectory = System.getProperty("user.dir");
	private String dataDirectory = workingDirectory +
			File.separator + "resources" + File.separator + "database" +
			File.separator;
	private String iconsDirectory = workingDirectory +
			File.separator + "resources" + File.separator + "icons" +
			File.separator;
	private String userDirectory = System.getProperty("user.home");
	private final String PROGRAM_NAME = "TexEd";
	private String programTitle = PROGRAM_NAME;
	private ArrayList<String> documentTypes = null;
	private String currentDocumentType = null;
	private String latestFilePath = "";
	private String selectedStrategy = null;
	private boolean saved = true;
	private LatexEditorController latexEditorController = null;
	private CommandDialog commandDialog = null;
	private MetadataDialog metadataDialog = null;
	private JButton newButton = null;
	private JButton manualNewButton = null;
	private JButton openButton = null;
	private JButton saveButton = null;
	private JButton commandButton = null;
	private JButton submitButton = null;
	private JButton versionTrackingButton = null;
	private JButton rollbackButton = null;
	private JButton propertiesButton = null;
	private JButton pdfButton = null;
	private JScrollPane scrollPane = null;
	private JTextArea textArea = null;
	private ArrayList <JMenuItem> strategiesMenu = null;
	private HashMap<String,Action> textActions = null;
	
	public MainFrame() {
		super();
		initFrontEnd();
		initBackEnd();
		initDialogs();
	}

	private void initFrontEnd(){
		loadViewData();
		defineFrameProperties();
		initKeyStrokes();
		initPanel();
		setEditMode(false);
		setSubmitMode(false);
		setRollbackMode(false);
		setPDFMode(false);
	}	
	
	private void loadViewData() {
		documentTypes = new ArrayList<String>();
		loadDocumentDataJSON();
	}
	
	private void loadDocumentDataJSON() {
		BufferedReader aBuffReader = null;
		String curJsonLine = null;
		String documentType = null;
		JSONObject curJsonObj = null;
		JSONParser parser = new JSONParser();
		try {
			aBuffReader = new BufferedReader(new FileReader(dataDirectory +
														"documents.json"));
			System.out.println("JSON File reading from Class: MainFrame");
			System.out.println("Start the ArrayList load from file " +
											 			"'documents.json'");
			while ((curJsonLine = aBuffReader.readLine()) != null) {
				curJsonObj = (JSONObject)parser.parse(curJsonLine);
				documentType = (String)curJsonObj.get("document_type");
				documentTypes.add(documentType);				
			}	
			System.out.println("ArrayList --> OK");
		}
		catch (IOException e) {
            System.out.println("Can not open the input file .");
            System.out.println("Program exits !");
            System.exit(0);
        } 
		catch (ParseException e) {
			System.out.println("Can not parse the given file with "
													+ "JSON protocol.");
            System.out.println("Program exits !");
            System.exit(0);
		}
	}
	
	private void defineFrameProperties() {
		MainFrame.this.setTitle(PROGRAM_NAME);
		MainFrame.this.setMinimumSize(new Dimension(1080, 700));
		MainFrame.this.setBounds(100, 50, 1080, 700);
		MainFrame.this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent windowEvent) {
		    	if (!isSaved()) {
					showSaveOption();
				}
		    	System.out.println("Program terminates succesfully!");
		    	System.exit(0);
		    }
		});
	}
	
	private void initKeyStrokes() {
		JPanel contentPane = (JPanel) MainFrame.this.getContentPane();
		InputMap iMap = contentPane.getInputMap(JComponent
												.WHEN_IN_FOCUSED_WINDOW);
		ActionMap aMap = contentPane.getActionMap();
		iMap.put(KeyStroke.getKeyStroke("control S"), "save");
		iMap.put(KeyStroke.getKeyStroke("control Z"), "rollback");
		iMap.put(KeyStroke.getKeyStroke("control W"), "submit");
		aMap.put("save", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveButton.doClick();
			}
		});
		aMap.put("rollback", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rollbackButton.doClick();
			}
		});
		aMap.put("submit", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitButton.doClick();
			}
		});
	}
	
	private void initPanel() {	
		JPanel mainPanel = createMainPanel();
		setFrameLayout(mainPanel);
		JPanel toolPanel = createToolPanel();
		initEditComponent();
		setPanelLayout(mainPanel, toolPanel);
	}
	
	private JPanel createMainPanel() {	
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.DARK_GRAY);
		return mainPanel;
	}
	
	private void setFrameLayout(JPanel aJPanel) {	
		GroupLayout mainFrameLayout = new GroupLayout(MainFrame
												.this.getContentPane());
		mainFrameLayout.setHorizontalGroup(
				mainFrameLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(aJPanel, GroupLayout.PREFERRED_SIZE,
													1080, Short.MAX_VALUE));
		mainFrameLayout.setVerticalGroup(
				mainFrameLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(aJPanel, Alignment.TRAILING,
							GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE));
	}
	
	private JPanel createToolPanel() {
		JPanel toolPanel = new JPanel();
		toolPanel.setBackground(new Color(220, 220, 220));
		toolPanel.setLayout(null);
		initToolButtons();
		assignToolButtons(toolPanel);
		return toolPanel;
	}
	
	private void initToolButtons(){	
		newButton = createNewButton();
		manualNewButton = createManualNewButton();
		openButton = createOpenButton();
		saveButton = createSaveButton();
		commandButton = createLatexCommandButton();
		submitButton = createSubmitChangesButton();
		versionTrackingButton = createVersionTrackingButton();
		rollbackButton = createRollbackButton();
		propertiesButton = createPropertiesButton();
		pdfButton = createExportToPDFButton();
	}
	
	private JButton createNewButton() {	
		JButton newButton = new JButton();
		ImageIcon createButtonEnter = new ImageIcon(iconsDirectory +
												"newSelected.jpg");
		ImageIcon createButtonExit = new ImageIcon(iconsDirectory + 
														"new.jpg");
		newButton.setBorderPainted(false);
		newButton.setContentAreaFilled(false);
		newButton.setIcon(createButtonExit);
		newButton.setBounds(0, 2, 35, 35);
		newButton.setToolTipText("New");
		newButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				newButton.setIcon(createButtonEnter);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				newButton.setIcon(createButtonExit);
			}
		});
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isSaved()) {
					showSaveOption();
				}
				/* Set New button to empty document creation */
				setCurrentDocumentType(documentTypes.get(0));
				latexEditorController.enact("CreateCommand");
			}
		});
		return newButton;
	}
	
	private JButton createManualNewButton() {	
		JButton manualNewButton = new JButton();
		ImageIcon manualNewButtonEnter = new ImageIcon(iconsDirectory +
												"newPropSelected.jpg");
		ImageIcon manualNewButtonExit = new ImageIcon(iconsDirectory +
														"newProp.jpg");
		manualNewButton.setBorderPainted(false);
		manualNewButton.setContentAreaFilled(false);
		manualNewButton.setIcon(manualNewButtonExit);
		manualNewButton.setBounds(35, 12, 16, 16);
		manualNewButton.setToolTipText("New...");
		initManualNewPopupMenu(manualNewButton);
		manualNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				manualNewButton.setIcon(manualNewButtonEnter);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				manualNewButton.setIcon(manualNewButtonExit);
			}
		});
		return manualNewButton;	
	}
	
	private void initManualNewPopupMenu(JButton manualNewButton) {	
		JPopupMenu createOptionsPopupMenu = new JPopupMenu();
		createOptionsPopupMenu.setFont(new Font("Chandas", Font.BOLD, 12));
		createOptionsPopupMenu.setBorder(new MatteBorder(2, 2, 2, 2,
											(Color) new Color(0, 0, 0)));	
		for (String documentType: documentTypes) {
			JMenuItem menuEntry = new JMenuItem(documentType);
			menuEntry.setFont(new Font("Dialog", Font.BOLD, 13));
			menuEntry.setBackground(new Color(220, 220, 220));
			menuEntry.setBorder(null);
			menuEntry.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (!isSaved()) {
						showSaveOption();
					}
					setCurrentDocumentType(documentType);
					latexEditorController.enact("CreateCommand");
				}
			});
			createOptionsPopupMenu.add(menuEntry);
		}
		addManualNewPopup(manualNewButton, createOptionsPopupMenu);
	}
	
	private void addManualNewPopup(Component component,
											final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showMenu(e);
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(),
						component.getX() - 70, component.getY() + 14);
			}
		});
	}

	private JButton createOpenButton() {
		JButton openButton = new JButton();
		ImageIcon openButtonEnter = new ImageIcon(iconsDirectory +	
												"openSelected.jpg");
		ImageIcon openButtonExit = new ImageIcon(iconsDirectory +
												"open.jpg");
		openButton.setBorderPainted(false);
		openButton.setContentAreaFilled(false);
		openButton.setIcon(openButtonExit);
		openButton.setBounds(58, 2, 35, 35);
		openButton.setToolTipText("Open");
		openButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				openButton.setIcon(openButtonEnter);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				openButton.setIcon(openButtonExit);
			}
		});
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isSaved()) {
					showSaveOption();
				}
				boolean doOpen = true;
				JFileChooser openFileChooser = createOpenFileChooser();
				int openChoice = openFileChooser.showOpenDialog(null);
				while (openChoice == JFileChooser.APPROVE_OPTION
				        && !isValidOpenning(openFileChooser)) {
						JOptionPane.showMessageDialog(null,
				    		  "Compatible only with existing LaTex Files!",
				    		  "Open Error", JOptionPane.ERROR_MESSAGE);
						openChoice = openFileChooser.showOpenDialog(null);
				}
				if (openChoice ==  JFileChooser.APPROVE_OPTION ) {
					//FilePath with .tex extension is guaranteed here
					String openPath = getTrimmedFilePath(openFileChooser);
					if (!isOpened(openPath)) {
						setLatestFilePath(openPath);
					}else{
						doOpen = false;
					}
				}else {
					doOpen = false;
				}
				if (doOpen) {
					latexEditorController.enact("OpenCommand");
				}
			}
		});
		return openButton;
	}
	
	private JFileChooser createOpenFileChooser() {
		JFileChooser openFileChooser = 
				new JFileChooser(new File(userDirectory));
		openFileChooser.setDialogTitle("Open Latex Document");
		openFileChooser.setAcceptAllFileFilterUsed(false);
		openFileChooser.setFileFilter(new FileTypeFilter(".tex",
													"LaTex File"));
		return openFileChooser;
	}
	
	private JButton createSaveButton() {
		JButton saveButton = new JButton();
		ImageIcon saveButtonEnter = new ImageIcon(iconsDirectory +
										"saveSelected.jpg");
		ImageIcon saveButtonExit = new ImageIcon(iconsDirectory +
												"save.jpg");
		saveButton.setBorderPainted(false);
		saveButton.setContentAreaFilled(false);
		saveButton.setIcon(saveButtonExit);
		saveButton.setBounds(100, 2, 35, 35); // +7 for gap
		saveButton.setToolTipText("Save");
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				saveButton.setIcon(saveButtonEnter);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				saveButton.setIcon(saveButtonExit);
			}
		});
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isSaved()) {
					boolean doSave = true;
					if (!latexEditorController.hasCurrentDocumentPath()) {
						JFileChooser saveFileChooser = createSaveFileChooser();
						int saveChoice = saveFileChooser.showSaveDialog(null);
						while (saveChoice == JFileChooser.APPROVE_OPTION
					        && !isValidSaving(saveFileChooser)) {
							JOptionPane.showMessageDialog(null,
					    		  "File names can't be blank!",
					    		  "Save Error", JOptionPane.ERROR_MESSAGE);
					      saveChoice = saveFileChooser.showSaveDialog(null);
					    }
						if (saveChoice ==  JFileChooser.APPROVE_OPTION) {
							String savePath = 
									getTrimmedFilePath(saveFileChooser);
							setLatestFilePath(getConfiguredFilePath(savePath));
						}else {
							doSave = false;
						}
					}
					if (doSave) {
						latexEditorController.enact("SaveCommand");
					}
				}
			}
		});
		return saveButton;
	}
	
	private JFileChooser createSaveFileChooser() {		
		JFileChooser saveFileChooser = 
				new JFileChooser(new File(userDirectory));
		saveFileChooser.setDialogTitle("Save Latex Document");
		saveFileChooser.setFileFilter(new FileTypeFilter(".tex",
													"LaTex File"));
		return saveFileChooser;
	}

	private JButton createLatexCommandButton() {	
		JButton comandButton = new JButton();
		ImageIcon  comandButtonEnter = new ImageIcon(iconsDirectory +
									"commandSelected.jpg");
		ImageIcon  comandButtonExit = new ImageIcon(iconsDirectory +
											"command.jpg");
		comandButton.setBorderPainted(false);
		comandButton.setContentAreaFilled(false);
		comandButton.setIcon(comandButtonExit);
		comandButton.setBounds(142, 2, 35, 35);
		comandButton.setToolTipText("Add Latex Command");
		comandButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				comandButton.setIcon(comandButtonEnter);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				comandButton.setIcon(comandButtonExit);
			}
		});
		comandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				commandDialog.updateChoice();
				commandDialog.setVisible(true);
			}
		});
		return comandButton;
	}
	
	private JButton createSubmitChangesButton() {
		JButton submitButton = new JButton();
		ImageIcon  submitButtonEnter = new ImageIcon(iconsDirectory +
												"/submitSelected.jpg");
		ImageIcon  submitButtonExit = new ImageIcon(iconsDirectory +
														"submit.jpg");
		submitButton.setBorderPainted(false);
		submitButton.setContentAreaFilled(false);
		submitButton.setIcon(submitButtonExit);
		submitButton.setBounds(182, 2, 35, 35);
		submitButton.setToolTipText("Submit Changes");
		submitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				submitButton.setIcon(submitButtonEnter);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				submitButton.setIcon(submitButtonExit);
			}
		});
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				latexEditorController.enact("SubmitCommand");
			}
		});
		return submitButton;
	}
	
	private JButton createVersionTrackingButton() {	
		JButton versionTrackingButton = new JButton();
		ImageIcon versionTrackingButtonEnter = new ImageIcon(iconsDirectory +
													"newPropSelected.jpg");
		ImageIcon versionTrackingButtonExit = new ImageIcon(iconsDirectory +
															"newProp.jpg");
		versionTrackingButton.setBorderPainted(false);
		versionTrackingButton.setContentAreaFilled(false);
		versionTrackingButton.setIcon(versionTrackingButtonExit);
		versionTrackingButton.setBounds(217, 12, 16, 16);
		versionTrackingButton.setToolTipText("Enable Version Tracking");
		initStrategiesPopupMenu(versionTrackingButton);
		versionTrackingButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				versionTrackingButton.setIcon(versionTrackingButtonEnter);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				versionTrackingButton.setIcon(versionTrackingButtonExit);
			}
		});
		return versionTrackingButton;	
	}
	
	private void initStrategiesPopupMenu(JButton changeStrategyButton) {	
		JPopupMenu createOptionsPopupMenu = new JPopupMenu();
		createOptionsPopupMenu.setFont(new Font("Chandas", Font.BOLD, 12));
		createOptionsPopupMenu.setBorder(new MatteBorder(2, 2, 2, 2,
												(Color) new Color(0, 0, 0)));
		strategiesMenu = new ArrayList <JMenuItem>();
		strategiesMenu.add(new JMenuItem("Volatile"));
		strategiesMenu.add(new JMenuItem("Stable"));
		strategiesMenu.add(new JMenuItem("Disable"));
		setSelectedStrategy("Disable");
		selectStrategy();
		for (JMenuItem strategy: strategiesMenu) {
			strategy.setFont(new Font("Dialog", Font.BOLD, 13));
			if(strategy.getText().equals("Disable")) {
				strategy.setBackground(new Color(220, 0, 0));
			}else {
				strategy.setBackground(new Color(220, 220, 220));
			}
			strategy.setBorder(null);
			strategy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String strategyType = strategy.getText();
					setSelectedStrategy(strategyType);
					if (strategyType.equals("Disable")) {
						latexEditorController.enact("DisableStrategyCommand");
					}else {
						latexEditorController.enact("ChangeStrategyCommand");
					}
				}
			});
			createOptionsPopupMenu.add(strategy);
		}
		addChangeStrategyPopup(changeStrategyButton, createOptionsPopupMenu);
	}
	
	private void addChangeStrategyPopup(Component component,
											final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(versionTrackingButton.isEnabled()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(),
						component.getX() - 218, component.getY() + 14);
			}
		});
	}

	private JButton createRollbackButton() {	
		JButton rollbackButton = new JButton();
		ImageIcon  rollbackButtonEnter = new ImageIcon(iconsDirectory +
												"rollbackSelected.jpg");
		ImageIcon  rollbackButtonExit = new ImageIcon(iconsDirectory +
														"rollback.jpg");
		rollbackButton.setBorderPainted(false);
		rollbackButton.setContentAreaFilled(false);
		rollbackButton.setIcon(rollbackButtonExit);
		rollbackButton.setBounds(240, 2, 35, 35);
		rollbackButton.setToolTipText("Rollback");
		rollbackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rollbackButton.setIcon(rollbackButtonEnter);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				rollbackButton.setIcon(rollbackButtonExit);
			}
		});
		rollbackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				latexEditorController.enact("RollbackToPreviousVersionCommand");
			}
		});
		return rollbackButton;
	}
	
	private JButton createExportToPDFButton() {
		JButton pdfButton = new JButton();
		ImageIcon pdfButtonEnter = new ImageIcon(iconsDirectory +
												"pdfSelected.jpg");
		ImageIcon pdfButtonExit = new ImageIcon(iconsDirectory +
														"pdf.jpg");
		pdfButton.setBorderPainted(false);
		pdfButton.setContentAreaFilled(false);
		pdfButton.setIcon(pdfButtonExit);
		pdfButton.setBounds(282, 2, 35, 35);
		pdfButton.setToolTipText("Export to PDF");
		pdfButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				pdfButton.setIcon(pdfButtonEnter);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				pdfButton.setIcon(pdfButtonExit);
			}
		});
		pdfButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isSaved()) {
					showSaveOption();
				}
				/* Set New button to empty document creation */
				/*Success message needed*/
				latexEditorController.enact("ExportToPDFCommand");
			}
		});
		return pdfButton;
	}
	
	private JButton createPropertiesButton() {	
		JButton propertiesButton = new JButton();
		ImageIcon  propertiesButtonEnter = new ImageIcon(iconsDirectory +
												"propertiesSelected.jpg");
		ImageIcon  propertiesButtonExit = new ImageIcon(iconsDirectory +
														"properties.jpg");
		propertiesButton.setBorderPainted(false);
		propertiesButton.setContentAreaFilled(false);
		propertiesButton.setIcon(propertiesButtonExit);
		propertiesButton.setBounds(324, 2, 35, 35);
		propertiesButton.setToolTipText("Document Properties");
		propertiesButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				propertiesButton.setIcon(propertiesButtonEnter);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				propertiesButton.setIcon(propertiesButtonExit);
			}
		});
		propertiesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metadataDialog.updateFields();
				metadataDialog.setVisible(true);
			}
		});
		return propertiesButton;
	}
	
	private void assignToolButtons(JPanel toolPanel){	
		toolPanel.add(newButton);
		toolPanel.add(manualNewButton);
		toolPanel.add(openButton);
		toolPanel.add(saveButton);
		toolPanel.add(commandButton);
		toolPanel.add(submitButton);
		toolPanel.add(versionTrackingButton);
		toolPanel.add(rollbackButton);
		toolPanel.add(propertiesButton);
		toolPanel.add(pdfButton);
	}
	
	private void initEditComponent(){
		textArea = createJTextArea();
		initTextActions();
		initTextAreaPopupMenu(textArea);
		scrollPane = createJScrollPanel();
		scrollPane.setViewportView(textArea);
	}
	
	private JTextArea createJTextArea(){	
		JTextArea textArea = new JTextArea();
		textArea.setSelectionColor(Color.LIGHT_GRAY);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setMargin(new Insets(10, 10, 10, 3));
		textArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		textArea.setFont(new Font("Courier 10 Pitch", Font.PLAIN, 16));
		textArea.setForeground(Color.DARK_GRAY);
		textArea.setCaretColor(Color.BLACK);
		textArea.setBackground(Color.WHITE);
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				changeToUnsaved();
		    }
		    public void removeUpdate(DocumentEvent e) {
		    	changeToUnsaved();
		    }
		    public void changedUpdate(DocumentEvent e) {
		    	changeToUnsaved();
		    }
		});
		return textArea;
	}
	
	private void initTextActions() {
		textActions = new HashMap<String,Action>();
		textActions.put("Cut", new DefaultEditorKit.CutAction());
		textActions.put("Copy", new DefaultEditorKit.CopyAction());
		textActions.put("Paste", new DefaultEditorKit.PasteAction());
	}
	
	private void initTextAreaPopupMenu(JTextArea textArea) {	
		JPopupMenu textAreapopupMenu = new JPopupMenu();
		textAreapopupMenu.setFont(new Font("Chandas", Font.BOLD, 12));
		textAreapopupMenu.setBorder(new MatteBorder(2, 2, 2, 2,
											(Color) new Color(0, 0, 0)));
		JMenuItem menuEntry = null;
		for (String textActionName : textActions.keySet()) {
			menuEntry = new JMenuItem(textActions.get(textActionName));
			menuEntry.setText(textActionName);
			menuEntry.setFont(new Font("Dialog", Font.BOLD, 13));
			menuEntry.setBackground(new Color(220, 220, 220));
			menuEntry.setBorder(null);
			textAreapopupMenu.add(menuEntry);
		}
		menuEntry = new JMenuItem("Add Latex Command");
		menuEntry.setFont(new Font("Dialog", Font.BOLD, 13));
		menuEntry.setBackground(new Color(220, 220, 220));
		menuEntry.setBorder(null);
		menuEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				commandButton.doClick();
			}
		});
		textAreapopupMenu.add(menuEntry);
		addTextAreaPopup(textArea, textAreapopupMenu);
	}
		
	private void addTextAreaPopup(Component component,
									final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	private JScrollPane createJScrollPanel() {	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants
												.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants
												.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar()
									.setBackground(new Color(220, 220, 220));
		scrollPane.getVerticalScrollBar().setBorder(null);
		return scrollPane;
	}
	
	private void setPanelLayout(JPanel mainJPanel, JPanel toolJPanel) {	
		GroupLayout mainPanelLayout = new GroupLayout(mainJPanel);
		mainPanelLayout.setHorizontalGroup(
				mainPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addGap(270)
					.addComponent(scrollPane)
					.addGap(270))
				.addComponent(toolJPanel, GroupLayout.DEFAULT_SIZE,
													1080, Short.MAX_VALUE)
		);
		mainPanelLayout.setVerticalGroup(
				mainPanelLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(mainPanelLayout.createSequentialGroup()
					.addComponent(toolJPanel, GroupLayout.PREFERRED_SIZE, 38,
													GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGap(20)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
													593, Short.MAX_VALUE)
					.addGap(33))
		);
		mainJPanel.setLayout(mainPanelLayout);
	}
	
	private void initBackEnd(){
		createController();
	}
	
	private void createController() {
		latexEditorController = new LatexEditorController(this);
		initControllerCommandsMap();
		initControllerPostsMap();
	}
	
	private void initControllerCommandsMap() {
		latexEditorController.addCommand("CreateCommand");
		latexEditorController.addCommand("OpenCommand");
		latexEditorController.addCommand("SaveCommand");
		latexEditorController.addCommand("AddLatexCommand");
		latexEditorController.addCommand("SubmitCommand");
		latexEditorController.addCommand("ChangeStrategyCommand");
		latexEditorController.addCommand("DisableStrategyCommand");
		latexEditorController.addCommand("RollbackToPreviousVersionCommand");
		latexEditorController.addCommand("ExportToPDFCommand");
	}
	
	private void initControllerPostsMap() {
		latexEditorController.addPost("CreateCommand");
		latexEditorController.addPost("OpenCommand");
		latexEditorController.addPost("SaveCommand");
		latexEditorController.addPost("AddLatexCommand");
		latexEditorController.addPost("SubmitCommand");
		latexEditorController.addPost("ChangeStrategyCommand");
		latexEditorController.addPost("DisableStrategyCommand");
		latexEditorController.addPost("RollbackToPreviousVersionCommand");
		latexEditorController.addPost("ExportToPDFCommand");
	}
	
	private void initDialogs() {
		createCommandDialog();
		createMetadataDialog();
	}
	
	private void createCommandDialog() {
		commandDialog = new CommandDialog(latexEditorController);
	}
	
	private void createMetadataDialog() {
		metadataDialog = new MetadataDialog(latexEditorController);
	}
	
	/* Getters */
	
	public String getProgramName() {
		return PROGRAM_NAME;
	}
	
	public String getProgramTitle() {
		return programTitle;
	}
	
	public String getCurrentDocumentType() {
		return currentDocumentType;
	}
	
	public String getLatestFilePath() {
		return latestFilePath;
	}
	
	private String getTrimmedFilePath(JFileChooser fileChooser) {
		return fileChooser.getSelectedFile().getAbsolutePath().trim();
	}
	
	private String getConfiguredFilePath(String filePath) {
		return latexEditorController.addLatexExtension(filePath);
	}

	public String getTypedText() {
		return textArea.getText().toString();
	}
	
	public int getCarretPosition() {
		return textArea.getCaretPosition();
	}
	
	public String getSelectedStrategy() {
		return selectedStrategy;
	}
	
	public CommandDialog getCommandDialog() { /* For testing */
		return commandDialog;
	}
	
	public LatexEditorController getLatexEditorController() { /* For testing */
		return latexEditorController;
	}
	
	public String getCommandName() {
		return commandDialog.getCurrentCommandName();
	}
	
	public ArrayList <JMenuItem>  getStrategiesButton(){
		return strategiesMenu;
	}
	
	public JButton getSubmitButton() {
		return submitButton;
	}
	
	public JButton getRollbackButton() {
		return rollbackButton;
	}
	
	public JButton getExportToPDFButton() {
		return pdfButton;
	}

	/* Setters */
	
	public void setProgramTitle(String title) {
		programTitle = title;
	}
	
	public void setCurrentDocumentType(String documentType) {
		currentDocumentType = documentType;
	}
	
	public void setLatestFilePath(String path) {
		latestFilePath = path;
	}
	
	public void setInputText(String text) {
		textArea.setText(text);
	}
	
	public void setSelectedStrategy(String selectedStrategy) {
		this.selectedStrategy = selectedStrategy;
	}
	
	/* Boolean Checkers */
	
	public boolean isSubmitButtonEnabled() {
		return submitButton.isEnabled();
	}
	
	public boolean isSaved() {
		return saved;
	}
	
	public boolean isEmptyInputText() {
		return getTypedText().isEmpty();
	}
	
	private boolean isValidOpenning(JFileChooser openFileChooser) {
		String trimmedFilePath = getTrimmedFilePath(openFileChooser);
		return latexEditorController.isValidOpenning(trimmedFilePath);
	}
	
	private boolean isValidSaving(JFileChooser saveFileChooser) {
		String trimmedFilePath = getTrimmedFilePath(saveFileChooser);
		return latexEditorController.isValidSaving(trimmedFilePath);
	}
	
	private boolean isOpened(String filePath) {
		return latexEditorController.isDocumentOpened(filePath);
	}
		
	/* View Environment Changers */
	
	private void showSaveOption() {
		int option = JOptionPane.showConfirmDialog(null,
				"Save changes to Document ?","Unsaved Document",
				JOptionPane.YES_NO_OPTION);
		if (option == 0) {
			saveButton.doClick();
		}
	}
	
	public void showVersionTrackingWarning() {
		JOptionPane.showMessageDialog(null,
				"Stable option needs a file with existing path.\n" +
				"Please save the file and try again!");
		saveButton.doClick();
	}

	public void showPDFCreationSuccess() {
		JOptionPane.showMessageDialog(null,
				"Document successfully exported to PDF!");
	}
	
	public void showPDFCreationFailed() {
		JOptionPane.showMessageDialog(null,
				"Export Failed!\n"+
				"Not a valid Latex Document!");
	}
	
	public void showPDFCreationMissingLib() {
			JOptionPane.showMessageDialog(null,
					"Latex library should be installed!\n" +
					"In linux use '$sudo apt install texlive-latex-extra' to install it!\n" +
					"In windows ...");
	}
	
	public void showAutoDisableNotification() {
		JOptionPane.showMessageDialog(null,
				"Version tracking mechanism disabled automatic,\n" +
				"after creation of a new file!");
	}
	
	public void changeToSaved() {
		this.setTitle(programTitle);
		saved = true;
	}
	
	public void changeToUnsaved() {
		this.setTitle(programTitle + "*");
		saved = false;
	}
	
	public void setEditMode(boolean mode) {
		scrollPane.setVisible(mode);
		saveButton.setEnabled(mode);
		commandButton.setEnabled(mode);
		versionTrackingButton.setEnabled(mode);
		propertiesButton.setEnabled(mode);
	}
	
	public void setSubmitMode(boolean mode){
		submitButton.setEnabled(mode);
	}
	
	public void setRollbackMode(boolean mode) {
		rollbackButton.setEnabled(mode);
	}
	
	public void selectStrategy() {
		if (selectedStrategy.equals("Disable")) {
			strategiesMenu.get(0).setEnabled(true);
			strategiesMenu.get(1).setEnabled(true);
			strategiesMenu.get(2).setEnabled(false);
		}else if (selectedStrategy.equals("Volatile")){
			strategiesMenu.get(0).setEnabled(false);
			strategiesMenu.get(1).setEnabled(true);
			strategiesMenu.get(2).setEnabled(true);
		}else {
			strategiesMenu.get(0).setEnabled(true);
			strategiesMenu.get(1).setEnabled(false);
			strategiesMenu.get(2).setEnabled(true);
		}
	}

	public void setPDFMode(boolean mode) {
		pdfButton.setEnabled(mode);
		
	}

	public void updateRendering() {
		getContentPane().validate();
		getContentPane().repaint();
	}
}
