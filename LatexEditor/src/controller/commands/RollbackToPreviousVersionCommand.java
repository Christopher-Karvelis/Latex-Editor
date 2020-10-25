package controller.commands;

import controller.LatexEditorController;
import model.strategies.VersionsManager;

public class RollbackToPreviousVersionCommand extends Command{

	public RollbackToPreviousVersionCommand(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}

	@Override
	public void execute() {
		LatexEditorController latexEditorController = 
													getLatexEditorController();
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		versionsManager.rollbackToPreviousVersion();
	}
}
