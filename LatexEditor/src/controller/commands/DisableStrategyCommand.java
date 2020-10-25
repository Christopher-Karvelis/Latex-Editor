package controller.commands;

import controller.LatexEditorController;
import model.strategies.VersionsManager;

public class DisableStrategyCommand extends Command{

	public DisableStrategyCommand(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}

	@Override
	public void execute() {
		LatexEditorController latexEditorController = 
													getLatexEditorController();
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		versionsManager.disable();
	}

}
