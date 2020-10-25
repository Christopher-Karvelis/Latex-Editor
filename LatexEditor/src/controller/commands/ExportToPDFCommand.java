package controller.commands;

import controller.LatexEditorController;
import model.strategies.VersionsManager;

public class ExportToPDFCommand extends Command{
	
	public ExportToPDFCommand(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}

	@Override
	public void execute() {
		LatexEditorController latexEditorController = 
				getLatexEditorController();
		VersionsManager versionsManager = latexEditorController
				.getVersionsManager();
		if(versionsManager.getCurrentDocument().hasPath()) { 
			versionsManager.getCurrentDocument().exportToPdf();
		}
	}
}
