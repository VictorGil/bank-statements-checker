package com.victorgil.statementsChecker.entities;

import java.math.BigDecimal;
//import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.victorgil.statementsChecker.csv.CsvReader.HEADER;
public class Statement{
    private static int instanceCount;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    
    private LocalDate transactionDate;
    private String concept;
    private LocalDate valueDate;
    private BigDecimal amount;
    private String counterparty;
    private String reference;
    private boolean reimbursed;
    private int id;
    private int lineNumber;

    public Statement(Map<String, String> line, int lineNumber){
    	transactionDate=LocalDate.parse(line.get(HEADER[0]), dateFormatter);
        concept = line.get(HEADER[1]);
        valueDate=LocalDate.parse(line.get(HEADER[2]), dateFormatter);
        amount=new BigDecimal(line.get(HEADER[3]));
        //We do not parse HEADER[4], it is not meaningful for our purpose
        counterparty=line.get(HEADER[5]);
        reference=line.get(HEADER[6]);
        id=++instanceCount;
        this.lineNumber = lineNumber;
    }
    
    @Override
    public String toString(){
        String text="Statement{\n" +
            "    TRANSACTION DATE: " + transactionDate + "\n" +
            "    CONCEPT: " + concept + "\n" +
            "    VALUE DATE: " + valueDate + "\n" +
            "    AMOUNT: " + amount + "\n" +
            "    COUNTERPARTY: " + counterparty + "\n" +
            "    REFERENCE: " + reference + "\n" +
            "    IS REIMBURSED?: " + reimbursed + "\n" +
            "    ID: " + id + "\n" +
            "    LINE NUMBER: " + lineNumber + "\n" +
            "}";
        return text;
    }
    //Getters and setters
    public LocalDate getTransactionDate(){
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate){
        this.transactionDate = transactionDate;
    }

    public String getConcept(){
        return concept;
    }

    public void setConcept(String concept){
        this.concept = concept;
    }

    public LocalDate getValueDate(){
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate){
        this.valueDate = valueDate;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public String getCounterparty(){
        return counterparty;
    }

    public void setCounterparty(String counterparty){
        this.counterparty = counterparty;
    }

    public String getReference(){
        return reference;
    }

    public void setReference(String reference){
        this.reference = reference;
    }

    public boolean isReimbursed(){
        return reimbursed;
    }

    public void setReimbursed(boolean reimbursed){
        this.reimbursed = reimbursed;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getLineNumber(){
        return lineNumber;
    }

    public void setLineNumber(int lineNumber){
        this.lineNumber = lineNumber;
    }

    public static int getInstanceCount(){
        return instanceCount;
    }
}
