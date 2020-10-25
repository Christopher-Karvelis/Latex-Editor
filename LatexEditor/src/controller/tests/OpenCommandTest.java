package controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import controller.LatexEditorController;
import controller.commands.OpenCommand;
import model.document.Document;
import view.MainFrame;

class OpenCommandTest {
	
	private String userDirectory = System.getProperty("user.home");
	private String filename = "test";
	
	
	@Test
	void openCommandShouldLoadADocumentFromDisk() {
		
		/* Write an Empty Document to disk */
		String contents = "Apple Pi Orange Sky";
		try {
			FileWriter fileWriter = 
					new FileWriter(userDirectory + "/" + filename + ".tex");
			fileWriter.write(contents);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		mainFrame.setLatestFilePath(userDirectory + "/" + filename + ".tex");
		OpenCommand openCommand = new OpenCommand(latexEditorController);
		openCommand.execute();
		Document currentDocument = latexEditorController
								.getVersionsManager().getCurrentDocument();
		assertEquals("Empty", currentDocument.getType(), "Should be Empty");
		/* Document paths is without extension (.tex) in model */
		assertEquals(userDirectory + "/" + filename + ".tex", currentDocument.getPath(),
												"Should have specific path");
		assertEquals(contents, currentDocument.getContents(),
											"Should have specific content");
	}

}
