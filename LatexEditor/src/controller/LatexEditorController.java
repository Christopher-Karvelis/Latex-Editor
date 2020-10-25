package controller;

import java.io.File;
import java.util.HashMap;
import org.apache.commons.io.FilenameUtils;
import controller.commands.Command;
import controller.commands.CommandsFactory;
import controller.posts.Post;
import controller.posts.PostsFactory;
import model.command.CommandModelManager;
import model.document.DocumentManager;
import model.strategies.VersionsManager;
import view.MainFrame;

public class LatexEditorController {
	
	private MainFrame mainFrame = null;
	private CommandsFactory commandsFactory = null;
	private PostsFactory postsFactory = null;
	private DocumentManager documentManager = null;
	private VersionsManager versionsManager = null;
	private CommandModelManager commandModelManager = null;
	private HashMap<String,Command> commandsMap = null;	
	private HashMap<String,Post> postsMap = null;
	
	public LatexEditorController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		commandsFactory = new CommandsFactory(this);
		postsFactory = new PostsFactory(this);
		documentManager = new DocumentManager();
		versionsManager = new VersionsManager();
		commandModelManager = new CommandModelManager();
		commandsMap = new HashMap<String,Command>();
		postsMap = new HashMap<String,Post>();
	}
	
	public void addCommand(String commandType) {
		commandsMap.put(commandType, 
							commandsFactory.createCommand(commandType));
	}
	
	public void addPost(String commandType) {
		postsMap.put(commandType, postsFactory.createPost(commandType));
	}
	
	public void enact(String commandType) {
		commandsMap.get(commandType).execute();
		postsMap.get(commandType).execute();
	}
	
	public String getProgramName() {
		return mainFrame.getProgramName();
	}
	public String getProgramTitle() {
		return mainFrame.getProgramTitle();
	}
	
	public String getSelectedDocumentType() {
		return mainFrame.getCurrentDocumentType();
	}
	
	public String getLatestFilePath() {
		return mainFrame.getLatestFilePath();
	}
	
	public String getTypedText() {
		return mainFrame.getTypedText();
	}
	
	public int getTextAreaCarretPosition() {
		return mainFrame.getCarretPosition();
	}
	
	public String getCommandName() {
		return mainFrame.getCommandName();
	}
	
	public String getSelectedStrategyType() {
		return mainFrame.getSelectedStrategy();
	}
	
	public String getCurrentDocumentType() {
		return versionsManager.getCurrentDocument().getType();
	}
	
	public String getCurrentDocumentAuthor() {
		return versionsManager.getCurrentDocument().getAuthor();
	}
	
	public String getCurrentDocumentDate() {
		return versionsManager.getCurrentDocument().getDate();
	}
	
	public String getCurrentDocumentPath() {
		return versionsManager.getCurrentDocument().getPath();
	}
	
	public DocumentManager getDocumentManager() {
		return documentManager;
	}
	
	public VersionsManager getVersionsManager() {
		return versionsManager;
	}
	
	public CommandModelManager getCommandModelManager() {
		return commandModelManager;
	}
	public boolean isSubmitButtonEnabled() {
		return mainFrame.isSubmitButtonEnabled();
	}
	public void setProgramTitle(String title) {
		mainFrame.setProgramTitle(title);
	}
	
	public void setLatestFilePath(String path) {
		mainFrame.setLatestFilePath(path);
	}
		
	public void setTypedText(String text) {
		mainFrame.setInputText(text);
	}
	
	public void setSelectedStrategyType(String strategyType) {
		mainFrame.setSelectedStrategy(strategyType);
	}
	
	public void setEditMode(boolean mode) {
		mainFrame.setEditMode(mode);
	}
		
	public void setSubmitMode(boolean mode) {
		mainFrame.setSubmitMode(mode);
	}
	
	public void setRollbackMode(boolean mode) {
		mainFrame.setRollbackMode(mode);
	}
	
	public void setPDFMode(boolean mode) {
		mainFrame.setPDFMode(mode);
	}

	public void updateSelectedStrategy() {
		mainFrame.selectStrategy();
	}
	
	public void showVersionTrackingWarning() {
		mainFrame.showVersionTrackingWarning();
	}
	
	public void showPDFCreationSuccess() {
		mainFrame.showPDFCreationSuccess();
	}
	
	public void showPDFCreationFailed() {
		mainFrame.showPDFCreationFailed();
	}
	
	public void showPDFCreationMissingLib() {
		mainFrame.showPDFCreationMissingLib();
	}
	
	public void showAutoDisableNotification() {
		mainFrame.showAutoDisableNotification();
	}
	
	public void changeTitleToSaved() {
		mainFrame.changeToSaved();
	}
	
	public void changeTitleToUnsaved() {
		mainFrame.changeToUnsaved();
	}
	
	public boolean isDocumentSaved() {
		return mainFrame.isSaved();
	}
	
	public boolean isDocumentOpened(String filePath) {
		return  versionsManager.getCurrentDocument() != null
				&& filePath.equals(versionsManager.getCurrentDocument()
																.getPath());
	}
	
	public boolean isAllowedCommand(String commandName) {
		return documentManager.isAllowedCommand(commandName,
										versionsManager.getCurrentDocument());
	}
	
	public boolean hasCurrentDocumentPath() {
		return versionsManager.getCurrentDocument().hasPath();
	}
	
	public void refreshFrame() {
		mainFrame.updateRendering();
	}

	public boolean isValidOpenning(String filePath) {
		// filePath must be already trimmed
		return (new File(filePath).isFile() && hasLatexExtension(filePath));
	}
	
	public boolean isValidSaving(String filePath) {
		// filePath must be already trimmed
		String fileName = FilenameUtils.getBaseName(filePath).trim();
		return !(fileName.isEmpty());
	}
	
	public String addLatexExtension(String filePath) {
		// filePath must be already trimmed
		return FilenameUtils.removeExtension(filePath).concat(".tex");
	}
	
	private boolean hasLatexExtension(String filePath) {
		return (FilenameUtils.getExtension(filePath).equals("tex"));
	}
	
}
