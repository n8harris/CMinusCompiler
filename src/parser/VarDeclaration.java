/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

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
    
    public void printVarDeclaration(String offset){
        System.out.println(offset + "VarDeclaration");
        num.printNumeric(offset + "    ");
        id.printIdentifier(offset + "    ");
    }
}
