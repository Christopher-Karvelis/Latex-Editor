package controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import controller.commands.CreateCommand;
import controller.commands.SaveCommand;
import model.document.Document;

import model.strategies.VersionsManager;
import view.MainFrame;

class SubmitCommandTest {
	
	
	private String userDirectory = System.getProperty("user.home");
	private String filename = "test";
	
	@Test
	void submitCommandShouldSaveChangesToNewVersionInMainMemory(){
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										mainFrame.getLatexEditorController();
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		latexEditorController.setTypedText("Apple Pi Orange Sky");
		String contentsBeforeSubmit = latexEditorController.getTypedText();
		mainFrame.getStrategiesButton().get(0).doClick();
		mainFrame.getSubmitButton().doClick();
		latexEditorController.setTypedText("Orange Sky");
		String contentsOfVersion = versionsManager.getPreviousVersion().getContents();
		System.out.println(contentsOfVersion);
		assertEquals(contentsOfVersion, contentsBeforeSubmit,
				"Should have the same content");
		
	}
	@Test
	void submitCommandShouldSaveChangesToNewVersionInDisk(){
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										mainFrame.getLatexEditorController();
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		latexEditorController.setTypedText("Apple Pi Orange Sky");
		Document currentDocument = versionsManager.getCurrentDocument();
		currentDocument.setPath(userDirectory + "/" + filename);
		SaveCommand saveCommand = new SaveCommand(latexEditorController);
		saveCommand.execute();
		String contentsBeforeSubmit = currentDocument.getContents();
		mainFrame.getStrategiesButton().get(1).doClick();
		mainFrame.getSubmitButton().doClick();
		latexEditorController.setTypedText("Orange Sky");
		String contentsOfVersion = versionsManager.getPreviousVersion().getContents();
		System.out.println(contentsOfVersion);
		assertEquals(contentsOfVersion, contentsBeforeSubmit,
				"Should have the same content");
		
	}
}
