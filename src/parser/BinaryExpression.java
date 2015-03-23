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
    
    public BinaryExpression(Expression l){
        lhs = l;
        rhs = null;
        operator = null;
    }
    
    public BinaryExpression(Expression l, Expression r, String o){
        lhs = l;
        rhs = r;
        operator = o;
    }
    public void printExpression(String offset){
        System.out.println(offset + "BinaryExpression");
        getLhs().printExpression(offset + "    ");
        getRhs().printExpression(offset + "    ");
        System.out.println(offset + getOperator());
    }

    /**
     * @return the lhs
     */
    public Expression getLhs() {
        return lhs;
    }

    /**
     * @param lhs the lhs to set
     */
    public void setLhs(Expression lhs) {
        this.lhs = lhs;
    }

    /**
     * @return the rhs
     */
    public Expression getRhs() {
        return rhs;
    }

    /**
     * @param rhs the rhs to set
     */
    public void setRhs(Expression rhs) {
        this.rhs = rhs;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
}
