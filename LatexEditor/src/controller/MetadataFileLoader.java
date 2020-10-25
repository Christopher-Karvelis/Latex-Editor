package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MetadataFileLoader {
	
	private String filePath = null;
	private String author = null;
	private String date = null;
	
	public MetadataFileLoader(String filePath) {
		this.filePath = filePath;
		extractMetaData();
	}
	
	private void extractMetaData() {
		String baseFileName = FilenameUtils.removeExtension(filePath);
		String dataFileName = baseFileName.concat(".data.json");
		author = new String("");
		date = new String("");
		if(new File(dataFileName).isFile()) {
			loadMetadataJSON(dataFileName);
		}
	}
	
	private void loadMetadataJSON(String filePath) {
		BufferedReader aBuffReader = null;
		JSONParser parser = new JSONParser();
		String curJsonLine = null;
		String author = null;
		String date = null;
		JSONObject curJsonObj = null;
		try {
			aBuffReader = new BufferedReader(new FileReader(filePath));
			System.out.println("JSON File reading from Class: "
													+ "MetaDataFileLoader");
			while ((curJsonLine = aBuffReader.readLine()) != null) {	
				curJsonObj = (JSONObject)parser.parse(curJsonLine);
				author = (String)curJsonObj.get("author");
				date = (String)curJsonObj.get("date");
			}
			this.author = new String(author);
			this.date = new String(date);
		}
		catch (IOException e) {
            System.out.println("Can not open the input file .");
        } 
		catch (ParseException e) {
			System.out.println("Can not parse the given file "
													+ "with JSON protocol.");
		}
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getDate() {
		return date;
	}
}
