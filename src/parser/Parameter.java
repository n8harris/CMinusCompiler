/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Nate H
 */
public class Parameter extends Expression {
    
    private Identifier id;
    private Numeric num;
    
    public Parameter(Identifier i){
        id = i;
    }
    
    public Parameter(Identifier i, Numeric n){
        id = i;
        num = n;
    }
    
}
