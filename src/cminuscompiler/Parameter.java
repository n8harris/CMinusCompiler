/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author Nate H
 */
public class Parameter extends Expression {
    
    private Identifier id;
    
    public Parameter(Identifier i){
        id = i;
    }
    
}
