/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author Nate H
 */
public abstract class Declaration {
    
    private Identifier id;
    protected FunctionDeclaration funDecl;
    
    public Declaration (Identifier ident, FunctionDeclaration f){
        id = ident;
        funDecl = f;
    }
    
    public Declaration (Identifier ident){
        id = ident;
    }
    
    
    
}
