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
public class ReturnStatement extends Statement {
    
    Expression expr;
    
    public ReturnStatement(Expression e){
        expr = e;
    }
    
    
    @Override
    public void printStatement(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "ReturnStatement");
        writer.newLine();
        if(expr != null){
            expr.printExpression(offset + "    ", writer);
        }
    }
}
