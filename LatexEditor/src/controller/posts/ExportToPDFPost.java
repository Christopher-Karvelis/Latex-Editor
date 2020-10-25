package controller.posts;

import controller.LatexEditorController;
import model.document.Document;
import model.strategies.VersionsManager;

public class ExportToPDFPost extends Post{

	public ExportToPDFPost(LatexEditorController latexEditorController) {
		super(latexEditorController);
		
	}

	public void execute() {
		LatexEditorController latexEditorController = 
				getLatexEditorController();
		VersionsManager versionsManager = latexEditorController
				.getVersionsManager();
		Document currentDocument = versionsManager.getCurrentDocument();
		String exportStatus = currentDocument.getExportStatus();
		if(exportStatus.equals("Success")) {
			latexEditorController.showPDFCreationSuccess();
		}else if(exportStatus.equals("Failed")){
			latexEditorController.showPDFCreationFailed();
		}else {
			latexEditorController.showPDFCreationMissingLib();
		}
		currentDocument.resetExportStatus();
	}
}
