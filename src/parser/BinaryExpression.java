/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Nate H
 */
public class BinaryExpression extends Expression {
    
    private Expression lhs;
    private Expression rhs;
    private String operator;
    
    public BinaryExpression(Expression l, Expression r, String o){
        lhs = l;
        rhs = r;
        operator = o;
    }
    public void printExpression(String offset){
        System.out.println(offset + "BinaryExpression");
        lhs.printExpression(offset + "    ");
        rhs.printExpression(offset + "    ");
        System.out.println(offset + operator);
    }
    
}
