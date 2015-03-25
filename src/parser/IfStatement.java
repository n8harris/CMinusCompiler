package parser;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Nate H
 */
public class IfStatement extends Statement {
    
    Expression expr;
    Statement thenStmt;
    Statement elseStmt;

    public IfStatement (Expression express, Statement stmt) {
        this (express, stmt, null);
    }

    public IfStatement (Expression express, Statement stmt1, Statement stmt2) {
        expr = express;
        thenStmt = stmt1;
        elseStmt = stmt2;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    @Override
    public void printStatement(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "IfStatement");
        writer.newLine();
        expr.printExpression(offset + "    ", writer);
        thenStmt.printStatement(offset + "    ", writer);
        if(elseStmt != null){
            elseStmt.printStatement(offset + "    ", writer);
        }
    }

    
}
