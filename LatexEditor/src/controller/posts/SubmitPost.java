package controller.posts;

import controller.LatexEditorController;

public class SubmitPost extends Post{

	public SubmitPost(LatexEditorController latexEditorController) {
		super(latexEditorController);
	}

	@Override
	public void execute() {
		LatexEditorController latexEditorController = 
													getLatexEditorController();
		latexEditorController.setRollbackMode(true);
		latexEditorController.refreshFrame();
	}

}
