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
public class VarDeclaration extends Declaration {
    
    Numeric num;
    Identifier id;
    
    public VarDeclaration(Numeric n, Identifier i){
        id = i;
        num = n;
        
    }
    
    public VarDeclaration(Identifier i){
        id = i;
    }
    
    public void printVarDeclaration(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "VarDeclaration");
        writer.newLine();
        if(num != null){
            writer.write(offset + "    " + "ArraySize");
            writer.newLine();
            num.printExpression(offset + "        ", writer);
        }
        id.printExpression(offset + "    ", writer);
    }
}
