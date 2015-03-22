/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;

/**
 *
 * @author Nate H
 */
public class FunctionDeclaration extends Declaration {
    
    private ArrayList<Parameter> params;
    private CompoundStatement cmpdStatement;
    private Identifier id;
    
    public FunctionDeclaration(ArrayList<Parameter> p, CompoundStatement c, Identifier i){
        id = i;
        params = p;
        cmpdStatement = c;
    }
    
    @Override
    public void printDeclaration(String offset){
        System.out.println(offset + "FunctionDeclaration");
        for (Parameter param : params) {
            param.printParameter(offset + "    ");
        }
        cmpdStatement.printCompoundStatement(offset + "    ");
        id.printIdentifier(offset + "    ");               
    }
    
}
