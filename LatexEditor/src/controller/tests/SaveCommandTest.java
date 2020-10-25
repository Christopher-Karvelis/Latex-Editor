package controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import controller.LatexEditorController;
import controller.commands.CreateCommand;
import controller.commands.SaveCommand;
import model.document.Document;
import model.strategies.VersionsManager;
import view.MainFrame;

class SaveCommandTest {
		
	private String userDirectory = System.getProperty("user.home");
	private String filename = "test";
	
	@Test
	void saveCommandShouldSaveDocumentContentsOnDisk() {
		
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		/* Add some random text on its contents */
		latexEditorController.setTypedText("Apple Pi Orange Sky");
		Document currentDocument = versionsManager.getCurrentDocument();
		currentDocument.setPath(userDirectory + "/" + filename);
		SaveCommand saveCommand = new SaveCommand(latexEditorController);
		saveCommand.execute();
		String file_data = "";
	    try{
	    	file_data = new String ( Files.readAllBytes( Paths
	    				.get(userDirectory + "/" + filename + ".tex") ) );
	    }
	    catch (IOException e){
	        e.printStackTrace();
	    }
	    assertEquals("Empty", currentDocument.getType(), "Should be Empty");
		assertEquals(userDirectory + "/" + filename, currentDocument.getPath(),
												"Should have specific path");
		assertEquals(file_data, currentDocument.getContents(),
											"Should have specific content");
	}
}
