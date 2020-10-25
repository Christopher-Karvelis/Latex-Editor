package controller.commands;

import controller.LatexEditorController;
import model.command.CommandModel;
import model.command.CommandModelManager;
import model.document.Document;
import model.strategies.VersionsManager;

public class AddLatexCommand extends Command{

	public AddLatexCommand(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}
	
	@Override
	public void execute() {
		LatexEditorController latexEditorController = 
													getLatexEditorController();
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		CommandModelManager commandModelManager = latexEditorController
													.getCommandModelManager();
		Document currentDocument = versionsManager.getCurrentDocument();
		String currentContents = latexEditorController.getTypedText();
		String commandName = latexEditorController.getCommandName();
		CommandModel newCommandModel = commandModelManager
											.createCommandModel(commandName);
		String commandText = newCommandModel.getText();
		int carretPosition = latexEditorController.getTextAreaCarretPosition();
		currentDocument.setContents(insertCommandText(currentContents,
												commandText, carretPosition));
	}
	
	private String insertCommandText(String contents, String commandText,
																int position) {
		StringBuilder stringBuilder = new StringBuilder(contents);
		stringBuilder.insert(position, commandText);
		return stringBuilder.toString();
	}
	
}
