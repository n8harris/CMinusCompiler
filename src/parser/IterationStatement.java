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
public class IterationStatement extends Statement {
    
    Expression expr;
    Statement stmt;
    
    public IterationStatement(Expression e, Statement s){
        expr = e;
        stmt = s;
    }
    @Override
    public void printStatement(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "IterationStatement");
        writer.newLine();
        expr.printExpression(offset + "    ", writer);
        stmt.printStatement(offset + "    ", writer);
    }
}
