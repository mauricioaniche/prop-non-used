package br.com.caelum;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Runner {


	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		if(args.length<2) {
			System.out.println("usage: java -jar prop-non-used.jar <project dir> <path to property file> <output file or empty>");
			System.exit(1);
		}

		String propFile = args[1];
		String projectRoot = args[0];
		String outputFile = (args.length==3 ? args[2] : null);
				
		new PropertyChecker(propFile, projectRoot, outputFile).execute();
	}
}
