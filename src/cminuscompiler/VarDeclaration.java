/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author Nate H
 */
public class VarDeclaration extends Declaration {
    
    Numeric num;
    
    public VarDeclaration(Numeric n, Identifier i){
        super(i);
        num = n;
        
    }
    
    public VarDeclaration(Identifier i){
        super(i);
    }
    
}
