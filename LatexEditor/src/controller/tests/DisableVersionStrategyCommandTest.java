package controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import controller.commands.CreateCommand;
import view.MainFrame;

class DisableVersionStrategyCommandTest {
	@Test
	void disableStrategyCommandShouldDisableAnyVersionsStrategy() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										mainFrame.getLatexEditorController();
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		mainFrame.getStrategiesButton().get(0).doClick();
		assertEquals(true, mainFrame.isSubmitButtonEnabled(),
				"Should be true");
		assertEquals("Volatile", latexEditorController.getSelectedStrategyType(),
				"Should be Volatile");
		mainFrame.getStrategiesButton().get(2).doClick();
		
		
		assertEquals(false, mainFrame.isSubmitButtonEnabled(),
				"Should be false");
		assertEquals("Disable", latexEditorController.getSelectedStrategyType(),
				"Should be Disable");
	}

}
