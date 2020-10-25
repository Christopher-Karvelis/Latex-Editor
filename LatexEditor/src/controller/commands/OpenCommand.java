package controller.commands;

import controller.LatexEditorController;
import controller.LatexFileLoader;
import controller.MetadataFileLoader;
import model.document.Document;
import model.document.DocumentManager;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategy;

public class OpenCommand extends Command{
	
	public OpenCommand(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}
	
	@Override
	public void execute() {
		LatexEditorController latexEditorController = 
													getLatexEditorController();
		DocumentManager documentManager = latexEditorController
														.getDocumentManager();
		VersionsManager versionsManager = latexEditorController
														.getVersionsManager();
		String latestPath = latexEditorController.getLatestFilePath();
		LatexFileLoader latexFileLoader = new LatexFileLoader(latestPath);
		MetadataFileLoader metadataFileLoader =
											new MetadataFileLoader(latestPath);
		Document loadedDocument = documentManager.loadDocument(latestPath,
				latexFileLoader.getType(),
				metadataFileLoader.getAuthor(),
				metadataFileLoader.getDate(), "","",
				latexFileLoader.getContents());
		versionsManager.setCurrentDocument(loadedDocument);
		if (versionsManager.isEnabled()) {
			String strategyType =
							latexEditorController.getSelectedStrategyType();
			VersionsStrategy versionStrategy = versionsManager
										.createVersionStrategy(strategyType);
			loadVersionsHistory(latestPath, versionStrategy);
			versionsManager.setStrategy(versionStrategy);
		}
	}
}
