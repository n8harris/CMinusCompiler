/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author Nate H
 */
public abstract class Expression {
    
    Numeric num;
    Identifier id;
    ArgExpression arg;
    
    public Expression(Numeric n){
        num = n;
    }
    
    public Expression(Identifier i){
        id = i;
    }
    
    public Expression(ArgExpression a){
        arg = a;
    }
    
    public Expression(){}
    
}
