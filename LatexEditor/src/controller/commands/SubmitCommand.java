package controller.commands;

import controller.LatexEditorController;
import model.document.Document;
import model.strategies.VersionsManager;

public class SubmitCommand extends Command{

	public SubmitCommand(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}

	@Override
	public void execute() {
		LatexEditorController latexEditorController = 
													getLatexEditorController();
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		updateModel();
		Document currentDocument = versionsManager.getCurrentDocument();
		versionsManager.setCurrentVersion(currentDocument);
	}
}
