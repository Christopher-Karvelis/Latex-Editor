package controller.commands;

import model.document.Document;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategy;
import controller.LatexEditorController;
import controller.VersionsFileLoader;

public abstract class Command {
	
	private  LatexEditorController latexEditorController = null;
	
	public Command(LatexEditorController latexEditorController) {
		this.latexEditorController = latexEditorController;
	}
	
	public abstract void execute();
	
	protected void updateModel(){
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		Document currentDocument = versionsManager.getCurrentDocument();
		currentDocument.setContents(latexEditorController.getTypedText());
		if(!currentDocument.hasPath()) {
			currentDocument.setPath(latexEditorController.getLatestFilePath());
			System.out.println(latexEditorController.getLatestFilePath());
		}
	}
	
	protected void loadVersionsHistory(String pathFile,
											VersionsStrategy versionStrategy) {
		VersionsFileLoader versionsFileLoader =
											new VersionsFileLoader(pathFile);
		versionStrategy.setEntireHistory(versionsFileLoader
														.getVersionsHistory());
	}
	
	public LatexEditorController getLatexEditorController() {
		return latexEditorController;
	}
}
