/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author Nate H
 */
public class BinaryExpression extends Expression {
    
    private Expression lhs;
    private Expression rhs;
    
    public BinaryExpression(Expression l, Expression r){
        super();
        lhs = l;
        rhs = r;
    }
    
}
