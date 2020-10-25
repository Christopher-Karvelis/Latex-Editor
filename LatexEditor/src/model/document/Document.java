package model.document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import de.nixosoft.jlr.JLRGenerator;

public class Document {
	
	private String path = null;
	private String type = null;
	private String author = null;
	private String date = null;
	private String copyright = null;
	private String versionID = null;
	private String contents = null;
	private String exportStatus = "None"; 
	
	public Document(String path, String type, String author, String date,
					String copyright, String versionID, String contents) {
		this.path = new String(path);
		this.type = new String(type);
		this.author = new String(author);
		this.date = new String(date);
		this.copyright = new String(copyright);
		this.versionID = new String(versionID);
		this.contents = new String(contents);
	}
	
	public Document(Document document) {
			this.path = new String(document.path);
			this.type = new String(document.type);
			this.author = new String(document.author);
			this.date = new String(document.date);
			this.copyright = new String(document.copyright);
			this.versionID = new String(document.versionID);
			this.contents = new String(document.contents);
	}

	public void save() {
		saveLatex();
		String metadataFilePath =
				FilenameUtils.removeExtension(path).concat(".data.json");
		saveMetadataJSON(metadataFilePath);
	}
	
	private void saveLatex() {
		try {
			FileWriter fileWriter = new FileWriter(path);
			fileWriter.write(contents);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveMetadataJSON(String filePath) {
		JSONObject curJsonObj = new JSONObject();
		curJsonObj.put("author", author);
		curJsonObj.put("date", date);
		try {
			FileWriter fileWriter = new FileWriter(filePath);
			fileWriter.write(curJsonObj.toJSONString() + "\n");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportToPdf() {
		File latexFile = new File(path);
		File destination = new File(File.separator + FilenameUtils.getFullPath(path));
		File workingDirectory = new File(System.getProperty("user.home"));
		JLRGenerator pdfGen = new JLRGenerator();
		boolean exported = false;
		try {
			exported = pdfGen.generate(latexFile, destination, workingDirectory);
			if(exported) {
				exportStatus = "Success";
			}
			else {
				exportStatus = "Failed";
			}
		} catch (IOException e) {
			exportStatus = "MissingLib";
		}
	}
	
	public Document clone() {
		return new Document (this.path, this.type, this.author,
					this.date, this.copyright, this.versionID,
					this.contents);
	}

	public String getPath() {
		return path;
	}
	
	public String getDocumentName() {
		return FilenameUtils.getName(path);
	}
	
	public String getType() {
		return type;
	}
	
	public String getAuthor() {
		return author;
	}
		
	public String getDate() {
		return date;
	}
	
	public Object getCopyright() {
		return copyright;
	}
	
	public String getVersionID() {
		return versionID;
	}
	
	public String getContents() {
		return this.contents;
	}
	
	public String getExportStatus() {
		return exportStatus;
	}
	
	public void setPath(String path) {
		this.path = new String(path);
	}
	
	public void setAuthor(String author) {
		this.author = new String(author);
	}
	
	public void setDate(String date) {
		this.date = new String(date);
	}
	
	public void setVersionID(String versionID) {
		this.versionID = new String(versionID);
	}

	public void setContents(String contents) {
		this.contents = new String(contents);
	}
	
	public void resetExportStatus() {
		this.exportStatus = "None";
	}
	
	public boolean hasPath() {
		return !(path.isEmpty() || path == null);
	}
}
