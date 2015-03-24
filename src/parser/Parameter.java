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
public class Parameter extends Expression {
    
    private Identifier id;
    
    public Parameter(Identifier i){
        id = i;
    }
    
    public void printParameter(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "Parameter");
        writer.newLine();
        id.printExpression(offset + "    ", writer);
    }
}
