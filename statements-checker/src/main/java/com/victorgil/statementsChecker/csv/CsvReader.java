package com.victorgil.statementsChecker.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.supercsv.io.CsvMapReader;
import org.supercsv.prefs.CsvPreference;

import com.victorgil.statementsChecker.entities.Statement;

public class CsvReader {
	private static Log log = LogFactory.getLog(CsvReader.class);
	private CsvMapReader csvReader;
    private InputStreamReader reader;
    public static String[] HEADER = {"FECHA_OPERACIÓN", "CONCEPTO", "FECHA_VALOR", "IMPORTE",
			"ACREEDOR", "REFERENCIA"};
    
	public List<Statement> readFile(File file) throws FileNotFoundException,
	IOException{
	    initCsvReader(file);
	    readOriginalHeader();
	    return readLines();
	}
	private void initCsvReader(File file) throws FileNotFoundException{
		reader = new InputStreamReader(new FileInputStream(file));
		//We assume that the file uses the same line separator chars as the current system.
		csvReader = new CsvMapReader(reader, new CsvPreference.Builder('"', '|', System.lineSeparator()).build());
	}
	private void readOriginalHeader() throws IOException{
		String[] originalHeader=csvReader.getHeader(false);
		log.trace("Original header from file: " + Arrays.toString(originalHeader));
	}
	private List<Statement> readLines() throws IOException{
	    List<Statement> statements = new ArrayList<Statement>();
	    int lineCount=0;
	    while (true){
	        Map<String,String> row = csvReader.read(HEADER);
	        if (row == null)
	            break;
	        lineCount++;
	        statements.add(new Statement(row, lineCount));
	    }
	    log.debug("Number of lines read: " + lineCount);
	    return statements;
	}	
}