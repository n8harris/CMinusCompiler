/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Nate H
 */
public class AssignExpression extends Expression {
    
    private Identifier id;
    private Expression expr;
    
    public AssignExpression(Identifier i, Expression e){
        id = i;
        expr = e;
    }
    
}
