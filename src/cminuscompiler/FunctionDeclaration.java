/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.util.ArrayList;

/**
 *
 * @author Nate H
 */
public class FunctionDeclaration extends Declaration {
    
    private ArrayList<Parameter> params;
    private CompoundStatement cmpdStatement;
    
    public FunctionDeclaration(ArrayList<Parameter> p, CompoundStatement c, Identifier i){
        super(i);
        params = p;
        cmpdStatement = c;
    }
    
    
}
