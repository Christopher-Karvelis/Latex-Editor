package controller.posts;

import controller.LatexEditorController;
import model.document.Document;
import model.strategies.VersionsManager;

public class AddLatexPost extends Post{

	public AddLatexPost(LatexEditorController latexEditorController) {
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
		latexEditorController.changeTitleToUnsaved();
		latexEditorController.refreshFrame();
	}

}
