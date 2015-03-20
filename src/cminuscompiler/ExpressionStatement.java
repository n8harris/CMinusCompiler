/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author Nate H
 */
public class ExpressionStatement extends Statement {
    
    private Expression exp;
    
    public ExpressionStatement(Expression e){
        super();
        exp = e;
    }
    
}
