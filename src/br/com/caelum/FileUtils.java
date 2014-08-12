package br.com.caelum;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	
	private String[] extensions;


	public FileUtils(String[] extensions) {
		this.extensions = extensions;
	}

	public String readStream(InputStream is) {
	    StringBuilder sb = new StringBuilder(512);
	    try {
	        Reader r = new InputStreamReader(is, "UTF-8");
	        int c = 0;
	        while ((c = r.read()) != -1) {
	            sb.append((char) c);
	        }
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	    return sb.toString();
	}
	

	public List<File> getFilesFrom(String projectRoot) {
		ArrayList<File> result = new ArrayList<File>();
		getAllFilesIn(new File(projectRoot), result);
		return result;
	}
	

	private void getAllFilesIn(File aStartingDir, ArrayList<File> result) {
		try {
			File[] filesAndDirs = aStartingDir.listFiles();
			for (File file : filesAndDirs) {
				if (file.isDirectory()) {
					getAllFilesIn(file, result);
				} else if (file.isFile() && isSourceCode(file)) {
						result.add(file);
				}
			}
		} catch (Throwable e) {
			System.err.println("getAllFiles error in " + aStartingDir.getPath());
		}
	}
	

	private boolean isSourceCode(File file) {
		for(String ext : extensions) {
			if(file.getName().toUpperCase().endsWith(ext))
				return true;
		}
		
		return false;
	}
}
