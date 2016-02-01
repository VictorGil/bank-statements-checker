package com.victorgil.statementsChecker;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.victorgil.statementsChecker.entities.Statement;
import static com.victorgil.statementsChecker.entities.Statement.DATE_FORMATTER;
import static java.math.BigDecimal.ZERO;

public class Analyzer{
    private static Log log = LogFactory.getLog(Analyzer.class);
    
    //BTW, this is not the real credit card number and it was cancelled anyway
    private static String CREDIT_CARD_NUMBER ="5402053120000009"; 
    
    List<Statement> correctionStatements=new ArrayList<Statement>();
    List<Statement> fraudulentStatements=new ArrayList<Statement>();
    BigDecimal reimbursedAmount = ZERO;
    BigDecimal stolenAmount = ZERO;
    List<Statement> fraudulentWithNoCorrectionStatements=new ArrayList<Statement>();
    List<Statement> paidWithNoFraudulentStatements=new ArrayList<Statement>();
    
    public void analyze(List<Statement> statements){
        calculateReimbursedAmount(statements);
        calculateStolenAmount(statements);
        compareStatements();
    }
    private void calculateReimbursedAmount(List<Statement> statements){         
        for (Statement statement : statements){
            if (isCorrectionStatement(statement)){
                correctionStatements.add(statement);
                reimbursedAmount = reimbursedAmount.add(statement.getAmount());
            }
        }
    log.info("Number of correction statements: " + correctionStatements.size());
    log.info("Reimbursed amount: " + reimbursedAmount);
    //log.debug("List of correction statements: " + correctionStatements);
    }
    private void calculateStolenAmount(List<Statement> statements){         
        for (Statement statement : statements){
            if (isFraudulentStatement(statement)){
                fraudulentStatements.add(statement);
                stolenAmount = stolenAmount.add(statement.getAmount().abs());
            }
        }
    log.info("Number of fraudulent statements: " + fraudulentStatements.size());
    log.info("Stolen amount: " + stolenAmount);
    //log.debug("List of fraudulent statements: " + fraudulentStatements);
    }
    private void compareStatements(){
        for (Statement statement : correctionStatements)
            paidWithNoFraudulentStatements.add(statement);
        for (Statement statement : fraudulentStatements)
            fraudulentWithNoCorrectionStatements.add(statement);
        for (int i=0; i<paidWithNoFraudulentStatements.size();){
            for (int j=0; j<fraudulentWithNoCorrectionStatements.size()
                    && i<paidWithNoFraudulentStatements.size();){
                if(fraudulentWithNoCorrectionStatements.get(j).getAmount().abs().compareTo(
                        paidWithNoFraudulentStatements.get(i).getAmount()) == 0){
                    fraudulentWithNoCorrectionStatements.remove(j);
                    paidWithNoFraudulentStatements.remove(i);
                } else
                    j++;
            }
            i++;
        }
        log.info("Number of fraudulent statements with no correction statements: " + fraudulentWithNoCorrectionStatements.size());
        log.info("Number of paid statements with no correction statements: " + paidWithNoFraudulentStatements.size());
        log.debug("Aggregated amount of fraudulent statements with no correction statements: " + 
                calculateAmount(fraudulentWithNoCorrectionStatements));
        log.debug("Aggregated amout of paid statements with no fraudulent statements: " +
                calculateAmount(paidWithNoFraudulentStatements));
        log.trace("List of fraudulent statements which have not been reimbursed: " + fraudulentWithNoCorrectionStatements);
    }
  
    private BigDecimal calculateAmount(List<Statement> statements){
        BigDecimal aggAmount = ZERO;
        for (Statement statement : statements)
            aggAmount=aggAmount.add(statement.getAmount().abs());
        return aggAmount;
    }
    private boolean isCorrectionStatement(Statement statement){
        if (statement.getConcept().contains("CORRECCION")
                || statement.getConcept().contains("ANUL COMPRA TARJ") 
                || (statement.getConcept().contains("COMPRA TARJ") && 
                        statement.getAmount().compareTo(ZERO) > 0)){
            if (statement.getAmount().compareTo(ZERO) < 0)
                throw new AssertionError("The amount of a correction statement"
                        + " should always be positive.");
            if (!statement.getReference().equals(CREDIT_CARD_NUMBER))
                throw new AssertionError("The reference should be equal"
                        + " to the credit card number.");
            return true;
        }
        return false;
    }
    private boolean isFraudulentStatement(Statement statement){
        if (statement.getReference() != null && 
                statement.getReference().equals(CREDIT_CARD_NUMBER) && 
                statement.getAmount().compareTo(ZERO)<0 &&
                !statement.getConcept().contains("REINTEGRO CAJERO AUTOMATICO") &&
                statement.getTransactionDate().compareTo(
                LocalDate.parse("14/10/2015", DATE_FORMATTER)) >= 0 &&
                !statement.getConcept().contains("MAKUTO-PATERNA") &&
                !statement.getConcept().contains("IKEA VALENCIA")){
            return true;
        }
        return false;
    }
}
