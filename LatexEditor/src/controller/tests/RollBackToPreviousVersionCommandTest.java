package controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import controller.commands.CreateCommand;
import controller.commands.SaveCommand;
import model.document.Document;
import model.strategies.VersionsManager;
import view.MainFrame;

class RollBackToPreviousVersionCommandTest {
	
	private String userDirectory = System.getProperty("user.home");
	private String filename = "test";
	
	@Test
	void rollbackCommandShouldShouldSetTheCurrentDocumentContentsToTheOnesOfThePreviousVersionInMainMemory(){
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										mainFrame.getLatexEditorController();
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		mainFrame.getStrategiesButton().get(0).doClick();
		latexEditorController.setTypedText("Apple Pi Orange Sky");
		mainFrame.getSubmitButton().doClick();;
		latexEditorController.setTypedText("Orange Sky");
		String contentsOfPreviousVersion = versionsManager.getPreviousVersion()
																	.getContents();
		mainFrame.getRollbackButton().doClick();
		assertEquals(contentsOfPreviousVersion,versionsManager.getCurrentDocument().getContents(), 
				"Should have the same content with the previous verion");
		
	}
	
	@Test
	void rollbackCommandShouldShouldSetTheCurrentDocumentContentsToTheOnesOfThePreviousVersionInDisk(){
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										mainFrame.getLatexEditorController();
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		latexEditorController.setSelectedStrategyType("Volatile");
		latexEditorController.setTypedText("Apple Pi Orange Sky");
		Document currentDocument = versionsManager.getCurrentDocument();
		currentDocument.setPath(userDirectory + "/" + filename);
		SaveCommand saveCommand = new SaveCommand(latexEditorController);
		saveCommand.execute();
		mainFrame.getStrategiesButton().get(1).doClick();
		mainFrame.getSubmitButton().doClick();
		latexEditorController.setTypedText("Orange Sky");
		String contentsOfPreviousVersion = versionsManager.getPreviousVersion()
																	.getContents();
		mainFrame.getRollbackButton().doClick();
		assertEquals(contentsOfPreviousVersion,versionsManager.getCurrentDocument().getContents(), 
				"Should have the same content with the previous verion");
		
	}
}
