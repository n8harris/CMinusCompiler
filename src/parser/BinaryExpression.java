package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;
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
    
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
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
    
    public void genLLCode(Function f) throws Exception {
        lhs.genLLCode(f);
        rhs.genLLCode(f);
        
        Operand src1 = new Operand(Operand.OperandType.REGISTER, lhs.getRegNum());
        Operand src2 = new Operand(Operand.OperandType.REGISTER, rhs.getRegNum());
        this.setRegNum(f.getNewRegNum());
        Operand dest = new Operand(Operand.OperandType.REGISTER, this.getRegNum());
        
        switch(operator.getType()){
            case PLUS_TOKEN:
                addOper(Operation.OperationType.ADD_I, f, src1, src2, dest);
                break;
            case MINUS_TOKEN:
                addOper(Operation.OperationType.SUB_I, f, src1, src2, dest);
                break;
            case MULT_TOKEN:
                addOper(Operation.OperationType.MUL_I, f, src1, src2, dest);
                break;
            case DIV_TOKEN:
                addOper(Operation.OperationType.DIV_I, f, src1, src2, dest);
                break;
            case LT_TOKEN:
                addOper(Operation.OperationType.LT, f, src1, src2, dest);
                break;
            case LTEQ_TOKEN:
                addOper(Operation.OperationType.LTE, f, src1, src2, dest);
                break;
            case GT_TOKEN:
                addOper(Operation.OperationType.GT, f, src1, src2, dest);
                break;
            case GTEQ_TOKEN:
                addOper(Operation.OperationType.GTE, f, src1, src2, dest);
                break;
            case EQ_TOKEN:
                addOper(Operation.OperationType.EQUAL, f, src1, src2, dest);
                break;
            case NOTEQ_TOKEN:
                addOper(Operation.OperationType.NOT_EQUAL, f, src1, src2, dest);
                break;
            default:
                break;
                
                
        }
        
        
    }
    
    public void addOper(Operation.OperationType op, Function f, Operand src1, Operand src2, Operand dest){
        Operation newOper =
        new Operation(op, f.getCurrBlock());
        newOper.setSrcOperand(0, src1);
        newOper.setSrcOperand(1, src2);
        newOper.setDestOperand(0, dest);
        f.getCurrBlock().appendOper(newOper);
    }
    
}
