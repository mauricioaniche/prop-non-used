package br.com.caelum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Runner {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
//		if(args.length!=2) {
//			System.out.println("usage: java -jar prop-non-used.jar <project dir> <path to property file>");
//			System.exit(1);
//		}
//		
//		String propFile = args[1];
//		String projectRoot = args[0];
		String propFile = "/Users/mauricioaniche/dev/workspace/gnarus/src/main/alura/messages.properties";
		String projectRoot = "/Users/mauricioaniche/dev/workspace/gnarus/";
		
		Properties propertyFile = new Properties();
		propertyFile.load(new FileInputStream(propFile));
		
		List<File> sourceCodeFiles = getFilesFrom(projectRoot);
		
		Set<PropertyNotUsed> errors = new HashSet<PropertyNotUsed>();
		for(Object k : propertyFile.keySet()) {
			String property = (String) k;
			
			for(File file : sourceCodeFiles) {
				if(existsIn(property, file)) errors.add(new PropertyNotUsed(property));
			}
		}
		
		if(errors.isEmpty()) System.exit(0);
		
		for(PropertyNotUsed error : errors) {
			System.out.println("Non-used property: " + error.getName());
		}
		System.exit(1);
	}

	private static boolean existsIn(String key, File file) throws FileNotFoundException {
		String source = readStream(new FileInputStream(file));
		return source.contains(key);
	}
	
	public static String readStream(InputStream is) {
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
	

	private static List<File> getFilesFrom(String projectRoot) {
		ArrayList<File> result = new ArrayList<File>();
		getAllFilesIn(new File(projectRoot), result);
		return result;
	}
	

	private static void getAllFilesIn(File aStartingDir, ArrayList<File> result) {
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

	private static boolean isSourceCode(File file) {
		return file.getName().toUpperCase().endsWith(".JAVA") || 
				file.getName().toUpperCase().endsWith(".JSP");
	}
	
}
