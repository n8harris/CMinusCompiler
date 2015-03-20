/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Nate H
 */
public class IterationStatement extends Statement {
    
    Expression expr;
    Statement stmt;
    
    public IterationStatement(Expression e, Statement s){
        expr = e;
        stmt = s;
    }
    
}
