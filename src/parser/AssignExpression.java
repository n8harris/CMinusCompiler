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
public class AssignExpression extends Expression {
    
    private Identifier id;
    private Expression expr;
    
    public AssignExpression(Identifier i, Expression e){
        id = i;
        expr = e;
    }
    
    public void printExpression(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "AssignExpression");
        writer.newLine();
        id.printExpression(offset + "    ", writer);
        expr.printExpression(offset + "    ", writer);
    }
    
}
