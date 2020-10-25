package controller.posts;

import controller.LatexEditorController;

public class DisableStrategyPost extends Post{

	public DisableStrategyPost(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}

	@Override
	public void execute() {
		LatexEditorController latexEditorController = 
													getLatexEditorController();
		latexEditorController.setSubmitMode(false);
		latexEditorController.setRollbackMode(false);
		latexEditorController.updateSelectedStrategy();
		latexEditorController.refreshFrame();
	}
}
