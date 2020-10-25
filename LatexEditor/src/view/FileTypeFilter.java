package view;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileTypeFilter extends FileFilter{

	private final String EXTENSION;
	private final String DESCRIPTION;
	
	public FileTypeFilter(String extension, String description) {
		this.EXTENSION = extension;
		this.DESCRIPTION = description;
	}
	
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		return file.getName().endsWith(EXTENSION);
	}

	@Override
	public String getDescription() {
		return DESCRIPTION + String.format(" (*%s)", EXTENSION);
	}

}
