package controller.posts;

import controller.LatexEditorController;
import model.document.Document;
import model.strategies.VersionsManager;

public class SavePost extends Post{

	public SavePost(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}

	@Override
	public void execute() {
		LatexEditorController latexEditorController =
													getLatexEditorController();
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		Document currentDocument = versionsManager.getCurrentDocument();
		latexEditorController.setProgramTitle(latexEditorController
												.getProgramName() + " - " +
											currentDocument.getDocumentName());
		latexEditorController.changeTitleToSaved();
		latexEditorController.setPDFMode(true);
		latexEditorController.refreshFrame();
		
	}
}
