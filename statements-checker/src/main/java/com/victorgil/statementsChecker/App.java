package com.victorgil.statementsChecker;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.victorgil.statementsChecker.csv.CsvReader;
import com.victorgil.statementsChecker.entities.Statement;

public class App {
    protected static Log log = LogFactory.getLog(App.class);
    public static void main(String[] args) throws IOException{
        String filePath="/home/user1/java/github/2016.01.31_bank-statements-checker.git/"
                + "statements-checker/src/test/resources/"
                + "extracto_cuenta_01.txt";
        List<Statement> statements = new CsvReader().readFile(filePath);
        log.info("Statements: " + statements);

    }
}
