package model.strategies;

public class VersionsStrategyFactory {
		
	private VersionsManager versionsManager = null;
	
	public VersionsStrategyFactory(VersionsManager versionsManager) {
		this.versionsManager = versionsManager;
	}
	
	public VersionsStrategy createStrategy(String strategyType) {
		switch(strategyType){
			case "Volatile":
				return new VolatileVersionsStrategy(); 
			case "Stable":
				String currentPath =
								versionsManager.getCurrentDocument().getPath();
				return new StableVersionsStrategy(currentPath);
			default: 
				throw new IllegalArgumentException(); 
		}
	}
}
