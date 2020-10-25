package controller.posts;

import controller.LatexEditorController;

public abstract class Post {
	
	private  LatexEditorController latexEditorController = null;
	
	public Post(LatexEditorController latexEditorController) {
		this.latexEditorController = latexEditorController;
	}
	
	public abstract void execute();
	
	public LatexEditorController getLatexEditorController() {
		return latexEditorController;
	}
}
