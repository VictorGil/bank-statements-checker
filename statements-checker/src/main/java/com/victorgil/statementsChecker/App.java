package com.victorgil.statementsChecker;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.victorgil.statementsChecker.csv.CsvReader;
import com.victorgil.statementsChecker.entities.Statement;

public class App {
    private static Log log = LogFactory.getLog(App.class);
    public static void main(String[] args) throws IOException, URISyntaxException{
        File file = Paths.get(ClassLoader.getSystemResource("statements.txt").toURI()).toFile();
        List<Statement> statements = new CsvReader().readFile(file);
        //log.info("Statements: " + statements);
        new Analyzer().analyze(statements);
    }
}
