package model.strategies;

import model.document.Document;

public class VersionsManager {
	
	private VersionsStrategyFactory versionsStrategyFactory = null;
	private Document currentDocument = null;
	private VersionsStrategy strategy = null;
	private boolean enabled = false;
	
	public VersionsManager() {
		versionsStrategyFactory = new VersionsStrategyFactory(this);
	}
	
	public VersionsStrategy createVersionStrategy(String strategyType) {
		return versionsStrategyFactory.createStrategy(strategyType);
	}
	
	public void rollbackToPreviousVersion() {
		setCurrentDocument(getPreviousVersion());
		strategy.removeVersion();
	}
	
	public void setCurrentVersion(Document newDocumentVersion) {
		/* Add the current version to Version's History */
		strategy.putVersion(currentDocument);
		/* Update the current Document */
		setCurrentDocument(newDocumentVersion);
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void disable() {
		enabled = false;
		strategy = null;
	}

	public Document getPreviousVersion() {
		//Returns the version from the list's tail
		return strategy.getVersion();
	}
	
	public Document getCurrentDocument() {
		return currentDocument;
	}
	
	public VersionsStrategy getVersionStrategy() {
		return strategy;
	}
	
	public void setStrategy(VersionsStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void setCurrentDocument(Document newDocument) {
		currentDocument = newDocument;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean hasVersions() {
		return !strategy.historyIsEmpty();
	}
}
