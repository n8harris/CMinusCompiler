/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Nate H
 */
public class ReturnStatement extends Statement {
    
    Expression expr;
    
    public ReturnStatement(Expression e){
        expr = e;
    }
    
    public void printReturnStatement(String offset){
        System.out.println(offset + "ReturnStatement");
        expr.printExpression(offset + "    ")
    }
}
