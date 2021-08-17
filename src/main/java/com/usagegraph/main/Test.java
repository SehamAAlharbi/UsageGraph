package com.usagegraph.main;

import java.io.FileNotFoundException;

import com.usagegraph.parser.Parser;

public class Test {
	
	private static final String DIRECTORYPATH ="src/main/resources/DataSet";
	private static final String SRC_PATH ="src/main/resources/API";
	
	public static void main (String [] args) {
		
		Parser parser = new Parser();
		try {
			parser.parseFiles(DIRECTORYPATH, SRC_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
