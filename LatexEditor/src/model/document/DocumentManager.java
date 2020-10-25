package model.document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DocumentManager {
	private String workingDirectory = System.getProperty("user.dir");
	private String dataDirectory = workingDirectory +
				File.separator + "resources" + File.separator + "database" +
				File.separator;
	private HashMap<String, Document> documentPrototypesMap = null;
	private HashMap<String, HashSet<String>> documentIllegalCommandsMap = null;
	
	public DocumentManager() {
		documentPrototypesMap = new HashMap<String,Document>();
		documentIllegalCommandsMap = new HashMap<String, HashSet<String>>();
		loadFromJsonFile();
	}
	
	public Document createDocument(String documentType) {
		return documentPrototypesMap.get(documentType).clone();
	}
	
	public Document loadDocument(String path, String type, String author,
			String date, String copyright, String versionID, String contents) {
		return new Document(path, type, author, date, copyright, versionID,
																	contents);
	}
	
	public boolean isAllowedCommand(String commandName,
													Document currentDocument) {
		String currentDocumentType = currentDocument.getType();
		return !documentIllegalCommandsMap.get(currentDocumentType)
											.contains(commandName);
	}
	
	private void loadFromJsonFile() {
		loadDocumentTemplatesJSON();
		loadIllegalCommandsJSON();
	}
	
	private void loadDocumentTemplatesJSON() {
		BufferedReader aBuffReader = null;
		JSONParser parser = new JSONParser();
		String curJsonLine = null;
		String documentType = null;
		String documentTamplate = null;
		JSONObject curJsonObj = null;
		try {
			aBuffReader = new BufferedReader(new FileReader(dataDirectory +
														"documents.json"));
			System.out.println("JSON File reading from Class: "
														+ "DocumentManager");
			System.out.println("Start the HashMap load from file "
														+ "'documents.json'");
			while ((curJsonLine = aBuffReader.readLine()) != null) {
					
				curJsonObj = (JSONObject)parser.parse(curJsonLine);
				documentType = (String)curJsonObj.get("document_type");
				documentTamplate = (String)curJsonObj.get("document_template");
				documentPrototypesMap.put(new String(documentType),
						new Document("", documentType, "", "", "", "",
															documentTamplate));
			}	
			System.out.println("HashMap --> OK");
		}
		catch (IOException e) {
            System.out.println("Can not open the input file .");
            System.out.println("Program exits !");
            System.exit(0);
        } 
		catch (ParseException e) {
			System.out.println("Can not parse the given file "
													+ "with JSON protocol.");
            System.out.println("Program exits !");
            System.exit(0);
		}
	}

	private void loadIllegalCommandsJSON() {
		BufferedReader aBuffReader = null;
		JSONParser parser = new JSONParser();
		String curJsonLine = null;
		String documentType = null;
		JSONObject curJsonObj = null;
		JSONArray illegalCommandsArray = null;
		HashSet<String> commandsSet = null;
		try {
			aBuffReader = new BufferedReader(new FileReader(dataDirectory +
											"document_command_matching.json"));
			System.out.println("JSON File reading from Class: "
														+ "DocumentManager");
			System.out.println("Start the HashMap load from file "
										+ "'document_command_matching.json'");
			while ((curJsonLine = aBuffReader.readLine()) != null) {	
				curJsonObj = (JSONObject)parser.parse(curJsonLine);
				documentType = (String)curJsonObj.get("document_type");
				illegalCommandsArray = (JSONArray) curJsonObj
													.get("illegal_commands");
				commandsSet = new HashSet<String>();
				for (int i = 0; i < illegalCommandsArray.size(); i++) {
					commandsSet.add(new String(illegalCommandsArray.get(i)
																.toString()));
				}
				documentIllegalCommandsMap.put(new String(documentType),
																commandsSet);
			}
			System.out.println("HashMap --> OK");
		}
		catch (IOException e) {
            System.out.println("Can not open the input file .");
            System.out.println("Program exits !");
            System.exit(0);
        } 
		catch (ParseException e) {
			System.out.println("Can not parse the given file "
													+ "with JSON protocol.");
            System.out.println("Program exits !");
            System.exit(0);
		}
		
	}
	
}
