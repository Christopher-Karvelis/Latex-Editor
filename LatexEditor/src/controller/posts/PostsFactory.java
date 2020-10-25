package controller.posts;

import controller.LatexEditorController;

public class PostsFactory {
	
	private LatexEditorController latexEditorController = null;
	
	public PostsFactory(LatexEditorController latexEditorController) {
		this.latexEditorController = latexEditorController;
	}
	
	public Post createPost(String commandType) {
        switch(commandType){
            case "CreateCommand": 
            	return new CreatePost(latexEditorController); 
            case "OpenCommand": 
	    		return new OpenPost(latexEditorController);
            case "SaveCommand":
            	return new SavePost(latexEditorController);
            case "AddLatexCommand":
            	return new AddLatexPost(latexEditorController);
            case "SubmitCommand":
            	return new SubmitPost(latexEditorController);
            case "ChangeStrategyCommand":
            	return new ChangeStrategyPost(latexEditorController);	
            case "DisableStrategyCommand":
            	return new DisableStrategyPost(latexEditorController);
            case "RollbackToPreviousVersionCommand":
            	return new RollbackToPreviousVersionPost(latexEditorController);
            case "ExportToPDFCommand":
            	return new ExportToPDFPost(latexEditorController);
            
            default: 
            	throw new IllegalArgumentException(); 
        }
        
	}
}
