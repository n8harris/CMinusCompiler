/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import scanner.Token;

/**
 *
 * @author Nate H
 */
public class BinaryExpression extends Expression {
    
    private Expression lhs;
    private Expression rhs;
    private Token operator;
    
    public BinaryExpression(BinaryExpression b){
        lhs = b.lhs;
        operator = b.operator;
        rhs = b.rhs;
    }
    
    public BinaryExpression(Expression l){
        lhs = l;
        rhs = null;
        operator = null;
    }
    
    public BinaryExpression(Expression l, Expression r, Token o){
        lhs = l;
        rhs = r;
        operator = o;
    }
    public void printExpression(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "Expression");
        writer.newLine();
        if(operator != null){
            try {
                Token.TokenType operatorToken = getOperator().getType();
                switch(operatorToken){
                    case PLUS_TOKEN:
                        writer.write(offset + "    " + "+");
                        writer.newLine();
                        break;
                    case MINUS_TOKEN: 
                        writer.write(offset + "    " + "-");
                        writer.newLine();
                        break;
                    case MULT_TOKEN: 
                        writer.write(offset + "    " + "*");
                        writer.newLine();
                        break;
                    case DIV_TOKEN:
                        writer.write(offset + "    " + "/");
                        writer.newLine();
                        break;
                    case LT_TOKEN:
                        writer.write(offset + "    " + "<");
                        writer.newLine();
                        break;
                    case GT_TOKEN:
                        writer.write(offset + "    " + ">");
                        writer.newLine();
                        break;
                    case LTEQ_TOKEN:
                        writer.write(offset + "    " + "<=");
                        writer.newLine();
                        break;
                    case GTEQ_TOKEN:
                        writer.write(offset + "    " + ">=");
                        writer.newLine();
                        break;
                    case EQ_TOKEN:
                        writer.write(offset + "    " + "==");
                        writer.newLine();
                        break;
                    case NOTEQ_TOKEN:
                        writer.write(offset + "    " + "!=");
                        writer.newLine();
                        break;
                    default:
                        throw new Exception("Error");
               }
            } catch (Exception ex) {
                Logger.getLogger(BinaryExpression.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        getLhs().printExpression(offset + "        ", writer);
        if(rhs != null){
            getRhs().printExpression(offset + "        ", writer);
        }
        
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
    public Token getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(Token operator) {
        this.operator = operator;
    }
    
}
