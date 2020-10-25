package controller.commands;

import controller.LatexEditorController;

public class CommandsFactory {
	
	private LatexEditorController latexEditorController = null;
	
	public CommandsFactory(LatexEditorController latexEditorController) {
		this.latexEditorController = latexEditorController;
	}

	public Command createCommand(String commandType) {
        switch(commandType){
            case "CreateCommand": 
            	return new CreateCommand(latexEditorController); 
            case "OpenCommand": 
	    		return new OpenCommand(latexEditorController);
            case "SaveCommand":
            	return new SaveCommand(latexEditorController);
            case "AddLatexCommand":
            	return new AddLatexCommand(latexEditorController);
            case "SubmitCommand":
            	return new SubmitCommand(latexEditorController);
            case "ChangeStrategyCommand":
            	return new ChangeStrategyCommand(latexEditorController);	
            case "DisableStrategyCommand":
            	return new DisableStrategyCommand(latexEditorController);
            case "RollbackToPreviousVersionCommand":
            	return new RollbackToPreviousVersionCommand(latexEditorController);
            case "ExportToPDFCommand":
            	return new ExportToPDFCommand(latexEditorController);
            default: 
            	throw new IllegalArgumentException(); 
        }
	}
}
