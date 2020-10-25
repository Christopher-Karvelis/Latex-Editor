package controller.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import controller.LatexEditorController;
import controller.commands.AddLatexCommand;
import controller.commands.CreateCommand;
import model.document.Document;
import model.strategies.VersionsManager;
import view.MainFrame;

class AddLatexCommandTest {

	@Test
	void additionOfChapterShouldReturnDocumentWithChapter() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		AddLatexCommand addLatexCommand = 
									new AddLatexCommand(latexEditorController);
		mainFrame.getCommandDialog().setCurrentCommandName("Chapter");
		addLatexCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Empty", currentDocument.getType(), "Should be Empty");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals("\\chapter{...}\n", currentDocument.getContents(),
												"Should have chapter command");
	}
	
	@Test
	void additionOfSectionShouldReturnDocumentWithSection() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		AddLatexCommand addLatexCommand = 
									new AddLatexCommand(latexEditorController);
		mainFrame.getCommandDialog().setCurrentCommandName("Section");
		addLatexCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Empty", currentDocument.getType(), "Should be Empty");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals("\\section{}\n", currentDocument.getContents(),
												"Should have section command");
	}
	
	@Test
	void additionOfSubsectionShouldReturnDocumentWithSubsection() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		AddLatexCommand addLatexCommand = 
									new AddLatexCommand(latexEditorController);
		mainFrame.getCommandDialog().setCurrentCommandName("Subsection");
		addLatexCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Empty", currentDocument.getType(), "Should be Empty");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals("\\subsection{}\n", currentDocument.getContents(),
											"Should have subsection command");
	}
	
	@Test
	void additionOfSubsubsectionShouldReturnDocumentWithSubsubsection() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		AddLatexCommand addLatexCommand = 
									new AddLatexCommand(latexEditorController);
		mainFrame.getCommandDialog().setCurrentCommandName("Subsubsection");
		addLatexCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Empty", currentDocument.getType(), "Should be Empty");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals("\\subsubsection{}\n", currentDocument.getContents(),
										"Should have subsubsection command");
	}
	
	@Test
	void additionOfItemListShouldReturnDocumentWithItemList() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		AddLatexCommand addLatexCommand = 
									new AddLatexCommand(latexEditorController);
		mainFrame.getCommandDialog().setCurrentCommandName("Item List");
		addLatexCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Empty", currentDocument.getType(), "Should be Empty");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals("\\begin{itemize}\n\\item ...\n\\item ...\n" +
				 			"\\end{itemize}\n",currentDocument.getContents(),
											"Should have item list command");
	}
	
	@Test
	void additionOfEnumerationListShouldReturnDocumentWithEnumerationList() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		AddLatexCommand addLatexCommand = 
									new AddLatexCommand(latexEditorController);
		mainFrame.getCommandDialog().setCurrentCommandName("Enumeration List");
		addLatexCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Empty", currentDocument.getType(), "Should be Empty");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals("\\begin{enumerate}\n\\item ...\n\\item ...\n" +
						"\\end{enumerate}\n", currentDocument.getContents(),
									"Should have enumeration list command");
	}
	
	@Test
	void additionOfTableShouldReturnDocumentWithTable() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		AddLatexCommand addLatexCommand = 
									new AddLatexCommand(latexEditorController);
		mainFrame.getCommandDialog().setCurrentCommandName("Table");
		addLatexCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Empty", currentDocument.getType(), "Should be Empty");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals("\\begin{table}\n\\caption{....}\\label{...}\n\\"
				+ "begin{tabular}{|c|c|c|}\n\\hline\n... &...&...\\\\\n"
				+ "... &...&...\\\\\n... &...&...\\\\\n\\hline\n\\end"
				+ "{tabular}\n\\end{table}\n",
				currentDocument.getContents(), "Should have table command");
	}
	
	@Test
	void additionOfFigureShouldReturnDocumentWithFigure() {
		MainFrame mainFrame = new MainFrame();
		LatexEditorController latexEditorController = 
										new LatexEditorController(mainFrame);
		CreateCommand createCommand = new CreateCommand(latexEditorController);
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		mainFrame.setCurrentDocumentType("Empty");
		createCommand.execute();
		AddLatexCommand addLatexCommand = 
									new AddLatexCommand(latexEditorController);
		mainFrame.getCommandDialog().setCurrentCommandName("Figure");
		addLatexCommand.execute();
		Document currentDocument = versionsManager.getCurrentDocument();
		assertEquals("Empty", currentDocument.getType(), "Should be Empty");
		assertEquals("", currentDocument.getPath(), "Should be empty string");
		assertEquals("\\begin{figure}\n\\includegraphics[width=...,height=...]"
				+ "{...}\n\\caption{....}\\label{...}\n\\end{figure}\n",
				currentDocument.getContents(), "Should have figure command");
	}

}
