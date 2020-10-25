package controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import controller.commands.CreateCommand;
import controller.commands.SaveCommand;
import model.document.Document;

import model.strategies.VersionsManager;
import view.MainFrame;

class ChangeStrategyCommandTest {
	private String userDirectory = System.getProperty("user.home");
	private String filename = "test";
	
	@Test
	void changeCommandShouldCangeVersionStrategyToVolatile() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										mainFrame.getLatexEditorController();
		CreateCommand createCommand = new CreateCommand(latexEditorController);		
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		mainFrame.getStrategiesButton().get(0).doClick();
		String currentStrategy = latexEditorController.getSelectedStrategyType();
		assertEquals("Volatile", currentStrategy,
				"Should be the same");
	}
	
	@Test
	void changeCommandShouldCangeVersionStrategyToStable() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										mainFrame.getLatexEditorController();
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		latexEditorController.setSelectedStrategyType("Stable");
		Document currentDocument = versionsManager.getCurrentDocument();
		currentDocument.setPath(userDirectory + "/" + filename);
		SaveCommand saveCommand = new SaveCommand(latexEditorController);
		saveCommand.execute();

		mainFrame.getStrategiesButton().get(1).doClick();
		String currentStrategy = latexEditorController.getSelectedStrategyType();
		assertEquals("Stable", currentStrategy,
				"Should be the same");
	}
}
