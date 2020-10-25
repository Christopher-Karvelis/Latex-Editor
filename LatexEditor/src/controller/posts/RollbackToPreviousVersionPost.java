package controller.posts;

import controller.LatexEditorController;
import model.document.Document;
import model.strategies.VersionsManager;

public class RollbackToPreviousVersionPost extends Post{

	public RollbackToPreviousVersionPost(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}

	@Override
	public void execute() {
		LatexEditorController latexEditorController = 
													getLatexEditorController();
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		Document currentDocument = versionsManager.getCurrentDocument();
		latexEditorController.setTypedText(currentDocument.getContents());
		if(!versionsManager.hasVersions()) {
			latexEditorController.setRollbackMode(false);
		}
		latexEditorController.refreshFrame();
	}

}
