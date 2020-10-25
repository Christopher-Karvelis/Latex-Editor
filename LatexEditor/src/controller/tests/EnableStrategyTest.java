package controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import controller.commands.CreateCommand;
import controller.commands.SaveCommand;
import model.document.Document;
import model.strategies.VersionsManager;
import view.MainFrame;

class EnableStrategyTest {
	private String userDirectory = System.getProperty("user.home");
	private String filename = "test";
	
	@Test
	void enableVolatileShouldEnableTheVersionsTrategy() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										mainFrame.getLatexEditorController();
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		assertEquals(false, mainFrame.isSubmitButtonEnabled(),
				"Should be false");	
		mainFrame.getStrategiesButton().get(0).doClick();
		
		
		assertEquals(true, mainFrame.isSubmitButtonEnabled(),
				"Should be true");
		assertEquals("Volatile", latexEditorController.getSelectedStrategyType(),
				"Should be Volatile");
	}
	
	@Test
	void enableStableShouldEnableTheVersionsTrategy() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										mainFrame.getLatexEditorController();
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
				.getVersionsManager();
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();

		assertEquals(false, mainFrame.isSubmitButtonEnabled(),
				"Should be false");
		latexEditorController.setTypedText("Apple Pi Orange Sky");
		Document currentDocument = versionsManager.getCurrentDocument();
		currentDocument.setPath(userDirectory + "/" + filename);
		SaveCommand saveCommand = new SaveCommand(latexEditorController);
		saveCommand.execute();
		
		mainFrame.getStrategiesButton().get(1).doClick();
		
		
		assertEquals(true, mainFrame.isSubmitButtonEnabled(),
				"Should be true");
		assertEquals("Stable", latexEditorController.getSelectedStrategyType(),
				"Should be Stable");
	}

}
