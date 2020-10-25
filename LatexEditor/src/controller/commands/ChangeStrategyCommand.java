package controller.commands;

import controller.LatexEditorController;
import model.strategies.VersionsManager;
import model.strategies.VersionsStrategy;

public class ChangeStrategyCommand extends Command{

	public ChangeStrategyCommand(LatexEditorController latexEditorController) {
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
			VersionsStrategy versionStrategy = versionsManager
										.createVersionStrategy(strategyType);
			if (versionsManager.isEnabled()) {
				versionStrategy.setEntireHistory(versionsManager
									.getVersionStrategy().getEntireHistory());
			}else {
				if(versionsManager
						.getCurrentDocument().hasPath()){
					loadVersionsHistory(versionsManager
							.getCurrentDocument().getPath(), versionStrategy);
				}
				
			}
			versionsManager.setStrategy(versionStrategy);
			versionsManager.enable();
		}
	}
}
