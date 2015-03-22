/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

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
    @Override
    public void printStatement(String offset){
        exp.printExpression(offset + "    ");
    }
    
}
