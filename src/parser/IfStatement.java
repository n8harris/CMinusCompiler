/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
