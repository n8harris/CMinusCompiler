/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author Nate H
 */
public class CallExpression extends Expression {
    
    private Identifier id;
    private ArgExpression arg;
    
    public CallExpression(Identifier i, ArgExpression a){
        id = i;
        arg = a;
    }
    
}
