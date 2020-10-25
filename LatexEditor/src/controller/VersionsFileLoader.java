package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import model.document.Document;

public class VersionsFileLoader {
	
	private String filePath = null;
	private ArrayList<Document> versionsHistory = null;
	
	public VersionsFileLoader(String filePath) {
		this.filePath = filePath;
		extractVersions();
	}
	
	private void extractVersions() {
		String baseFilePath = FilenameUtils.removeExtension(filePath);
		String versionFilePath = baseFilePath.concat(".ver.json");
		versionsHistory = new ArrayList<Document>();
		if(new File(versionFilePath).isFile()) {
			loadVersionsJSON(versionFilePath);
		}
		
	}
	
	private void loadVersionsJSON(String filePath) {
		BufferedReader aBuffReader = null;
		JSONParser parser = new JSONParser();
		String curJsonLine = null;
		JSONObject curJsonObj = null;
		String path = null;
		String type = null;
		String author = null;
		String date = null;
		String copyright = null;
		String versionID = null;
		String contents = null;
		try {
			aBuffReader = new BufferedReader(new FileReader(filePath));
			System.out.println("JSON File reading from Class: "
													+ "VersionsFileLoader");
			while ((curJsonLine = aBuffReader.readLine()) != null) {	
				curJsonObj = (JSONObject)parser.parse(curJsonLine);
				path = (String) curJsonObj.get("path");
				type = (String) curJsonObj.get("type");
				author = (String) curJsonObj.get("author");
				date = (String) curJsonObj.get("date");
				copyright = (String) curJsonObj.get("copyright");
				versionID = (String) curJsonObj.get("versionID");
				contents = (String) curJsonObj.get("contents");
				versionsHistory.add(new Document(path, type,
						author, date, copyright, versionID, contents));
			}
			System.out.println("ArrayList --> OK");
		}
		catch (IOException e) {
            System.out.println("Can not open the input file .");
        } 
		catch (ParseException e) {
			System.out.println("Can not parse the given file "
													+ "with JSON protocol.");
		}
	}
	
	public ArrayList<Document> getVersionsHistory(){
		return versionsHistory;
	}
}
