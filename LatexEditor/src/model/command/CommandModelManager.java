package model.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CommandModelManager {
	
	private String workingDirectory = System.getProperty("user.dir");
	private String dataDirectory = workingDirectory +
			File.separator + "resources" + File.separator + "database" +
			File.separator;
	private HashMap<String, CommandModel> commandPrototypesMap = null;
	
	public CommandModelManager() {
		commandPrototypesMap = new HashMap<String, CommandModel>();
		loadFromJsonFile();
	}

	public CommandModel createCommandModel(String commandName) {
		return commandPrototypesMap.get(commandName).clone();
	}
	
	private void loadFromJsonFile() {
		loadCommandDataJSON();
	}
	
	private void loadCommandDataJSON() {
		BufferedReader aBuffReader =null;
		JSONParser parser = new JSONParser();
		String curJsonLine=null;
		String commandName = null;
		String commandText = null;
		JSONObject curJsonObj = null;
		try {
			aBuffReader = new BufferedReader(new FileReader(dataDirectory +
														"commands.json"));
			System.out.println("JSON File reading from Class: "
													+ "CommandModelManager");
			System.out.println("Start the LinkedHashMap load from file "
														+ "'commands.json'");
			while ((curJsonLine = aBuffReader.readLine()) != null) {
				curJsonObj = (JSONObject)parser.parse(curJsonLine);
				commandName = (String)curJsonObj.get("command_name");
				commandText = (String)curJsonObj.get("command_text");
				commandPrototypesMap.put(new String(commandName),
									new CommandModel(commandName,commandText));
			}	
			System.out.println("HashMap --> OK");
		}
		catch (IOException e) {
            System.out.println("Can not open the input file .");
            System.out.println("Program exits !");
            System.exit(0);
        } 
		catch (ParseException e) {
			System.out.println("Can not parse the given file with "
														+ "JSON protocol.");
            System.out.println("Program exits !");
            System.exit(0);
		}
	}
}
