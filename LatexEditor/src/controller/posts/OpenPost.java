package controller.posts;

import controller.LatexEditorController;
import model.document.Document;
import model.strategies.VersionsManager;

public class OpenPost extends Post{

	public OpenPost(LatexEditorController latexEditorController) {
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
		latexEditorController.setProgramTitle(latexEditorController
													.getProgramName() + " - " +
											currentDocument.getDocumentName());
		latexEditorController.changeTitleToSaved();
		latexEditorController.setEditMode(true);
		latexEditorController.setPDFMode(true);
		if (versionsManager.isEnabled() && versionsManager.hasVersions()) {
			latexEditorController.setRollbackMode(true);
		}
		latexEditorController.refreshFrame();
	}
}
