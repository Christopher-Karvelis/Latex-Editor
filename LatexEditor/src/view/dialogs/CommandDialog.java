package view.dialogs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import controller.LatexEditorController;

public class CommandDialog extends JDialog {

	private String workingDirectory = System.getProperty("user.dir");
	private String dataDirectory = workingDirectory +
			File.separator + "resources" + File.separator + "database" +
			File.separator;
	private String currentCommandName = null;
	private LinkedHashMap<String,String> commandPreviews = null;
	private LatexEditorController latexEditorController = null;
	private JButton insertButton = null;
	private JButton cancelButton = null;
	private JLabel commandLabel = null;
	private JLabel previewLabel = null;
	private JLabel warningLabel = null;
	private JList<String> commandList = null;
	private JScrollPane scrollPane = null;
	private JTextArea previewArea = null;
	
	public CommandDialog(LatexEditorController latexEditorController) {
		super();
		initFrontEnd();
		initBackEnd(latexEditorController);
	}
	
	private void initFrontEnd() {
		loadViewData();
		defineDialogProperties();
		initPanel();
	}
	
	private void loadViewData() {
		commandPreviews = new LinkedHashMap<String,String>();
		loadCommandDataJSON();
	}
	
	private void loadCommandDataJSON() {
		
		BufferedReader aBuffReader =null;
		JSONParser parser = new JSONParser();
		String curJsonLine = null;
		String commandName = null;
		String commandText = null;
		JSONObject curJsonObj = null;
		try {
			aBuffReader = new BufferedReader(new FileReader(dataDirectory +
															"commands.json"));
			System.out.println("JSON File reading from Class: CommandDialog");
			System.out.println("Start the LinkedHashMap load from file "
														+ "'commands.json'");
			while ((curJsonLine = aBuffReader.readLine() ) != null) {	
				curJsonObj = (JSONObject)parser.parse(curJsonLine);
				commandName = (String)curJsonObj.get("command_name");
				commandText = (String)curJsonObj.get("command_text");
				commandPreviews.put(new String(commandName),
											new String(commandText));
			}	
			System.out.println("LinkedHashMap --> OK");
		}
		catch (IOException e) {
            System.out.println("Can not open the input file .");
            System.out.println("Program exits !");
            System.exit(0);
            
        } 
		catch (ParseException e) {
			System.out.println("Can not parse the given file "
										+ "with JSON protocol.");
            System.out.println("Program exits !");
            System.exit(0);
		}
	}
	
	private void defineDialogProperties() {	
		setTitle("Add Latex Command");
		int dialogWidth = 650;
		int dialogHeight = 460;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		CommandDialog.this.setBounds((dim.width-dialogWidth)/2,
				(dim.height-dialogHeight)/2, dialogWidth, dialogHeight);
		CommandDialog.this.setResizable(false);
		CommandDialog.this.setModal(true);
	}
	
	private void initPanel() {
		JPanel commandPanel = createCommandPanel();
		initPreviewComponents();
		assignPreviewComponents(commandPanel);
		getContentPane().add(commandPanel);
	}
	
	private JPanel createCommandPanel() {
		JPanel commandPanel = new JPanel();
		commandPanel.setBackground(Color.DARK_GRAY);
		commandPanel.setBounds(0, 0, 650, 430);
		commandPanel.setLayout(null);
		initCommandJButtons();
		assignCommandJButtons(commandPanel) ;
		initCommandJLabels();
		assignCommandJLabels(commandPanel);
		return commandPanel;
	}
	
	private void initCommandJButtons() {
		insertButton = createInsertButton();
		cancelButton = createCancelButton();
	}
	
	private JButton createInsertButton() {
		JButton addButton = new JButton("Insert");
		addButton.setBackground(new Color(153, 204, 204));
		addButton.setForeground(Color.BLACK);
		addButton.setBounds(282, 271, 126, 25);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				latexEditorController.enact("AddLatexCommand");
				CommandDialog.this.dispatchEvent(new WindowEvent
						(CommandDialog.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		return addButton;	
	}
	
	private JButton createCancelButton() {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBackground(new Color(153, 204, 204));
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setBounds(267, 376, 114, 25);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CommandDialog.this.dispatchEvent(new WindowEvent
						(CommandDialog.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		return cancelButton;
	}
	
	private void assignCommandJButtons(JPanel commandPanel) {
		commandPanel.add(insertButton);
		commandPanel.add(cancelButton);
	}
	
	private void initCommandJLabels() {
		warningLabel = createWarningLabel();
		commandLabel = createCommandLabel();
		previewLabel = createPreviewLabel();
	}
	
	private JLabel createCommandLabel() {
		JLabel commandLabel = new JLabel("Commands:");
		commandLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		commandLabel.setForeground(Color.LIGHT_GRAY);
		commandLabel.setBounds(45, 54, 209, 25);
		return commandLabel;
	}
	
	private JLabel createPreviewLabel() {
		JLabel previewLabel = new JLabel("Preview:");
		previewLabel.setForeground(Color.LIGHT_GRAY);
		previewLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		previewLabel.setBounds(282, 54, 209, 25);
		return previewLabel;
	}

	private JLabel createWarningLabel() {
		JLabel warningLabel = new JLabel("Not available for this "
												+ "Document type.");
		warningLabel.setVisible(false);
		warningLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		warningLabel.setForeground(Color.LIGHT_GRAY);
		warningLabel.setBounds(282, 299, 275, 15);
		return warningLabel;
	}
		
	private void assignCommandJLabels(JPanel commandPanel) {
		commandPanel.add(commandLabel);
		commandPanel.add(previewLabel);
		commandPanel.add(warningLabel);
	}
	
	private void initPreviewComponents() {
		previewArea = createJTextArea();
		commandList = createCommandList();
		scrollPane = createJScrollPane();
	}
	
	private JTextArea createJTextArea() {
		JTextArea textArea = new JTextArea();
		textArea.setMargin(new Insets(10, 10, 3, 3));
		textArea.setFont(new Font("Dialog", Font.BOLD, 12));
		textArea.setEditable(false);
		textArea.setBounds(282, 81, 319, 178);
		return textArea;
	}
	
	private JList<String> createCommandList() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		JList<String> commandList = new JList<String>(model);
		commandList.setFont(new Font("Dialog", Font.BOLD, 13));
		for (String command : commandPreviews.keySet()) {
			model.addElement(command);
		}
		commandList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String commandName = commandList.getSelectedValue().toString();
				setCurrentCommandName(commandName);
				previewArea.setText(commandPreviews.get(commandName));
				if (latexEditorController.isAllowedCommand(commandName)) {
					warningLabel.setVisible(false);
					insertButton.setEnabled(true);
				}else {
					warningLabel.setVisible(true);
					insertButton.setEnabled(false);
				}
			}
		});
		commandList.addMouseListener(new MouseAdapter(){
		    @Override
		    public void mouseClicked(MouseEvent e){
		        if (e.getClickCount()==2){
		        	insertButton.doClick();
		        }
		    }
		});
		return commandList;
	}
	
	private JScrollPane createJScrollPane() {	
		JScrollPane commandScrollPane = new JScrollPane();
		commandScrollPane.setBorder(new MatteBorder(2, 2, 2, 2,
										(Color) new Color(0, 0, 0)));
		commandScrollPane.setBounds(45, 81, 175, 217);
		return commandScrollPane;
	}

	private void assignPreviewComponents(JPanel commandPanel) {
		scrollPane.setViewportView(commandList);
		commandPanel.add(scrollPane);
		commandPanel.add(previewArea);
	}

	private void initBackEnd(LatexEditorController latexEditorController) {
		this.latexEditorController = latexEditorController;
	}
	
	public void updateChoice() {
		if (!commandList.isSelectedIndex(-1)) {
			commandList.setSelectedValue(currentCommandName, false);
			if (latexEditorController.isAllowedCommand(currentCommandName)) {
				warningLabel.setVisible(false);
				insertButton.setEnabled(true);
			}else {
				warningLabel.setVisible(true);
				insertButton.setEnabled(false);
			}
		}
	}

	public String getCurrentCommandName() {
		return currentCommandName;
	}
	
	public void setCurrentCommandName(String commandName) {
		currentCommandName = commandName;
	}	
}
