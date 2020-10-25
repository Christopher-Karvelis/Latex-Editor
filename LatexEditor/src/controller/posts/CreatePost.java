package controller.posts;

import controller.LatexEditorController;
import model.document.Document;
import model.strategies.VersionsManager;

public class CreatePost extends Post{

	public CreatePost(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}
	
	public void execute() {
		LatexEditorController latexEditorController = 
													getLatexEditorController();
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		Document currentDocument = versionsManager.getCurrentDocument();
		latexEditorController.setTypedText(currentDocument.getContents());
		latexEditorController.setProgramTitle(latexEditorController
														.getProgramName());
		latexEditorController.changeTitleToUnsaved();
		latexEditorController.setEditMode(true);
		latexEditorController.setPDFMode(false);
		if (latexEditorController.getSelectedStrategyType().equals("AutoDisable")) {
			latexEditorController.setSelectedStrategyType("Disable");
			latexEditorController.updateSelectedStrategy();
			latexEditorController.setSubmitMode(false);
			latexEditorController.setRollbackMode(false);
			latexEditorController.showAutoDisableNotification();
		}
		latexEditorController.setLatestFilePath("");
		latexEditorController.refreshFrame();
	}
}
