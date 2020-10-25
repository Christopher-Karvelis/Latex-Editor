package controller.posts;

import controller.LatexEditorController;
import model.strategies.VersionsManager;

public class ChangeStrategyPost extends Post{

	public ChangeStrategyPost(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}

	@Override
	public void execute() {
		LatexEditorController latexEditorController = 
													getLatexEditorController();
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		String strategyType = latexEditorController.getSelectedStrategyType();
		if (!strategyType.equals("Stable") ||
							latexEditorController.hasCurrentDocumentPath()) {
			latexEditorController.setSubmitMode(true);
			if (versionsManager.hasVersions()) {
				latexEditorController.setRollbackMode(true);
			}
			latexEditorController.updateSelectedStrategy();
			latexEditorController.refreshFrame();	
		}else {
			latexEditorController.showVersionTrackingWarning();
		}
	}
}
