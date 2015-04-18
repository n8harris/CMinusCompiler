package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author Nate H
 */
public class ReturnStatement extends Statement {
    
    Expression expr;
    
    public ReturnStatement(Expression e){
        expr = e;
    }
    
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    @Override
    public void printStatement(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "ReturnStatement");
        writer.newLine();
        if(expr != null){
            expr.printExpression(offset + "    ", writer);
        }
    }
    
    public void genLLCode(Function f){
        if(expr != null){
            expr.genLLCode();
            Operation newOper =
            new Operation(Operation.OperationType.ASSIGN, f.getCurrBlock());
            Operand src = new Operand(Operand.OperandType.REGISTER, expr.getRegNum());
            Operand dest = new Operand(Operand.OperandType.MACRO, "RetReg");
            newOper.setSrcOperand(0, src);
            newOper.setDestOperand(0, dest);
            f.getCurrBlock().appendOper(newOper);
        }
        
        Operation jumpOper =
        new Operation(Operation.OperationType.JMP, f.getCurrBlock());
        Operand jmpSrc = new Operand(Operand.OperandType.BLOCK, f.genReturnBlock());
        jumpOper.setSrcOperand(0, jmpSrc);
        f.getCurrBlock().appendOper(jumpOper);
        
    }
}
