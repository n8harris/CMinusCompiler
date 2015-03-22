/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

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
    public void printStatement(String offset){
        System.out.println(offset + "IfStatement");
        thenStmt.printStatement(offset + "    ");
        elseStmt.printStatement(offset + "    ");
    }

    
}
