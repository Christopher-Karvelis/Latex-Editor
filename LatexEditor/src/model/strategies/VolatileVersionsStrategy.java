package model.strategies;

import java.util.ArrayList;
import model.document.Document;

public class VolatileVersionsStrategy implements VersionsStrategy{
	
	private ArrayList<Document> versionsHistory = null;
	
	public VolatileVersionsStrategy() {
		versionsHistory = new ArrayList<Document>();
	}

	@Override
	public void putVersion(Document document) {
		versionsHistory.add(new Document(document));
	}

	@Override
	public Document getVersion() {
		return versionsHistory.get(versionsHistory.size()-1);
	}

	@Override
	public void setEntireHistory(ArrayList<Document> documents) {
		versionsHistory = documents;
	}

	@Override
	public ArrayList<Document> getEntireHistory() {
		return versionsHistory;
	}

	@Override
	public void removeVersion() {
		versionsHistory.remove(versionsHistory.size()-1);
	}

	@Override
	public boolean historyIsEmpty() {
		return versionsHistory.isEmpty();
	}
}
