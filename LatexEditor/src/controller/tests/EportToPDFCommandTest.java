package controller.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import controller.commands.CreateCommand;
import controller.commands.SaveCommand;
import model.document.Document;

import model.strategies.VersionsManager;
import view.MainFrame;

class EportToPDFCommandTest {
	
	private String userDirectory = System.getProperty("user.home");
	private String filename = "test.tex";
	
	@Test
	void exportToPDFCommandShouldExportCurrentDocumentToPDF(){
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										mainFrame.getLatexEditorController();
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Article");
		createCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		mainFrame.setInputText(currentDocument.getContents());
		mainFrame.setLatestFilePath(userDirectory + File.separator + filename);
		SaveCommand saveCommand = new SaveCommand(latexEditorController);
		saveCommand.execute();
		mainFrame.changeToSaved();
		mainFrame.getExportToPDFButton().setEnabled(true);;
		mainFrame.getExportToPDFButton().doClick();
		assertEquals(true, (new File(userDirectory + File.separator + "test.pdf")).isFile(),
				"Returned value of getPath() should be the path to pdf file");
		
	}
}
