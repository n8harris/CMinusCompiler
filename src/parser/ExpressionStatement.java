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
public class ExpressionStatement extends Statement {
    
    private Expression exp;
    
    public ExpressionStatement(Expression e){
        super();
        exp = e;
    }
    @Override
    public void printStatement(String offset, BufferedWriter writer) throws IOException{
        exp.printExpression(offset, writer);
    }
    
}
