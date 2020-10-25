package model.strategies;

import java.util.ArrayList;
import model.document.Document;

public interface VersionsStrategy {
	
	public void putVersion(Document document);
	
	public Document getVersion();
	
	public void setEntireHistory(ArrayList<Document> documents);
	
	public ArrayList<Document> getEntireHistory();
	
	public void removeVersion();

	public boolean historyIsEmpty();
}
