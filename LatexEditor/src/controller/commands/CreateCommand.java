package controller.commands;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import controller.LatexEditorController;
import model.document.Document;
import model.document.DocumentManager;
import model.strategies.VersionsManager;

public class CreateCommand extends Command{
	
	public CreateCommand(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}
	
	@Override
	public void execute() {
		LatexEditorController latexEditorController = 
													getLatexEditorController();
		DocumentManager documentManager = latexEditorController
														.getDocumentManager();
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		String documentType = latexEditorController.getSelectedDocumentType();
		Document newDocument = documentManager.createDocument(documentType);
		newDocument.setAuthor(getComputerName());
		newDocument.setDate(getDateTime());
		versionsManager.setCurrentDocument(newDocument);
		if (versionsManager.isEnabled()) {
			latexEditorController.setSelectedStrategyType("AutoDisable");
			versionsManager.disable();
		}
	}
	
	private String getComputerName() {
		String computerName = "";
		try {
			computerName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			computerName = "Unknown Owner";
		}
		return computerName;
	}
	
	private String getDateTime() {
		SimpleDateFormat formatter =
						new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());  
		return formatter.format(date); 
	}
}
