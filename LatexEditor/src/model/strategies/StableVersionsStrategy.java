package model.strategies;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import controller.VersionsFileLoader;
import model.document.Document;

public class StableVersionsStrategy implements VersionsStrategy{
	
	private String filePath = null;
	private String versionsFilePath = null;
	
	public StableVersionsStrategy(String filePath) {
		this.filePath = filePath;
		versionsFilePath = FilenameUtils.removeExtension(filePath)
														.concat(".ver.json");
	}
	
	@Override
	public void putVersion(Document document) {
		appendVersionJSON(document);
	}
	
	private void appendVersionJSON(Document document) {
		JSONObject curJsonObj = new JSONObject();
		curJsonObj.put("path", document.getPath());
		curJsonObj.put("type", document.getType());
		curJsonObj.put("author", document.getAuthor());
		curJsonObj.put("date", document.getDate());
		curJsonObj.put("copyright", document.getCopyright());
		curJsonObj.put("versionID", document.getVersionID());
		curJsonObj.put("contents", document.getContents());
		try {
			Writer output = new BufferedWriter(new FileWriter(versionsFilePath, true));
			output.append(curJsonObj.toJSONString() + "\n");
			output.close();
		} 
		 catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public Document getVersion() {
		BufferedReader aBuffReader = null;
		JSONParser parser = new JSONParser();
		String curJsonLine = null;
		JSONObject curJsonObj = null;
		try {
			aBuffReader = new BufferedReader(new FileReader(versionsFilePath));
			System.out.println("JSON File reading from Class: "
													+ "MetaDataFileLoader");
			while ((curJsonLine = aBuffReader.readLine()) != null) {	
				curJsonObj = (JSONObject)parser.parse(curJsonLine);
			}
		}
		catch (IOException e) {
            System.out.println("Can not open the input file .");
            System.exit(1);
        } 
		catch (ParseException e) {
			System.out.println("Can not parse the given file "
													+ "with JSON protocol.");
			System.exit(1);
		}
		return new Document((String)curJsonObj.get("path"),
							(String)curJsonObj.get("type"),
							(String)curJsonObj.get("author"),
							(String)curJsonObj.get("date"),
							(String)curJsonObj.get("copyright"),
							(String)curJsonObj.get("versionID"),
							(String)curJsonObj.get("contents"));
	}

	@Override
	public void setEntireHistory(ArrayList<Document> documents) {
		writeVersionsHistoryJSON(documents);
	}

	@Override
	public ArrayList<Document> getEntireHistory() {
		VersionsFileLoader versionsFileLoader =
									new VersionsFileLoader(filePath);
		return versionsFileLoader.getVersionsHistory();
	}

	@Override
	public void removeVersion() {
		ArrayList<Document> versionsHistory = getEntireHistory();
		writeVersionsHistoryJSON(versionsHistory.subList(0, versionsHistory.size()-1));
	}

	private void writeVersionsHistoryJSON(List<Document> list) {
		JSONObject curJsonObj = new JSONObject();
		try {
			Writer output = new BufferedWriter(
									new FileWriter(versionsFilePath));
			for(Document document: list) {
				curJsonObj.put("path", document.getPath());
				curJsonObj.put("type", document.getType());
				curJsonObj.put("author", document.getAuthor());
				curJsonObj.put("date", document.getDate());
				curJsonObj.put("copyright", document.getCopyright());
				curJsonObj.put("versionID", document.getVersionID());
				curJsonObj.put("contents", document.getContents());
				output.write(curJsonObj.toJSONString() + "\n");
			}
			output.close();
		} 
		 catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	@Override
	public boolean historyIsEmpty() {
		File file = new File(versionsFilePath);
		return file.length() == 0;
	}
}
