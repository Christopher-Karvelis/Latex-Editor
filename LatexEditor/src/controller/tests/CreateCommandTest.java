package controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import controller.LatexEditorController;
import controller.commands.CreateCommand;
import model.document.Document;
import model.strategies.VersionsManager;
import view.MainFrame;

class CreateCommandTest {
	
	private String workingDirectory = System.getProperty("user.dir");
	private String report_template = null;
	private String book_template = null;
	private String letter_template = null;
	private String article_template = null;
	
    public CreateCommandTest() {
		report_template = loadTemplate(workingDirectory +
				"/resources/templates_testing/report-template.tex");
		book_template = loadTemplate(workingDirectory +
				"/resources/templates_testing/book-template.tex");
		letter_template = loadTemplate(workingDirectory +
				"/resources/templates_testing/letter-template.tex");
		article_template = loadTemplate(workingDirectory +
				"/resources/templates_testing/article-template.tex");
    }
    
    /* Assumes that latex templates have Unix line Endings */
	private String loadTemplate(String fileName) {
		String data = "";
	    try{
	    	data = new String ( Files.readAllBytes( Paths.get(fileName) ) );
	    }
	    catch (IOException e){
	        e.printStackTrace();
	    }
	    return new String(data);
	}
	
	@Test
	void creationOfEmptyDocumentShouldReturnDocumentWithoutContents() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Empty", currentDocument.getType(), "Should be Empty");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals("", currentDocument.getContents(),
													"Should be empty string");
	}
	
	@Test
	void creationOfReportDocumentShouldReturnDocumentWithSpecificTemplateContents() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Report");
		createCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Report", currentDocument.getType(), "Should be Report");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals(report_template, currentDocument.getContents(),
											"Should be equal to template");
	}
	
	@Test
	void creationOfArticleDocumentShouldReturnDocumentWithSpecificTemplateContents() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Article");
		createCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Article", currentDocument.getType(), "Should be Article");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals(article_template, currentDocument.getContents(),
												"Should be equal to template");
	}
	
	@Test
	void creationOfBookDocumentShouldReturnDocumentWithSpecificTemplateContents() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Book");
		createCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Book", currentDocument.getType(), "Should be Book");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals(book_template, currentDocument.getContents(),
												"Should be equal to template");
	}
	
	@Test
	void creationOfLetterDocumentShouldReturnDocumentWithSpecificTemplateContents() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Letter");
		createCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Letter", currentDocument.getType(), "Should be Letter");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals(letter_template, currentDocument.getContents(),
												"Should be equal to template");
	}
}
