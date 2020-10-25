package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class LatexFileLoader {
	
	private String filePath = null;
	private String type = null;
	private String contents = null;
	
	public LatexFileLoader(String filePath) {
		this.filePath = filePath;
		extractType();
		extractContents();
	}

	private void extractType(){
		try {
			Pattern pattern = Pattern
								.compile("^(\\\\documentclass).*\\{(.+?)\\}");
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line = "";
			type = new String("Empty");
			Matcher matcher = null;
			while ((line = br.readLine()) != null){
				matcher = pattern.matcher(line);
				if (matcher.find()) {
					type = new String(
							StringUtils.capitalize(matcher.group(2)));
					break;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void extractContents() {
		String data = new String("");
	    try{
	    	data = new String ( Files.readAllBytes( Paths.get(filePath) ) );
	    }
	    catch (IOException e){
	        e.printStackTrace();
	    }
	    contents = new String(data);
	}
	
	public String getType() {
		return type;
	}
	
	public String getContents() {
		return contents;
	}

}
