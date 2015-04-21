package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.BasicBlock;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

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
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    @Override
    public void printStatement(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "IterationStatement");
        writer.newLine();
        expr.printExpression(offset + "    ", writer);
        stmt.printStatement(offset + "    ", writer);
    }
    
    public void genLLCode(Function f) throws Exception{
        expr.genLLCode(f);
        BasicBlock whileBody = new BasicBlock(f);
        BasicBlock postBlock = new BasicBlock(f);
        Operation branchOper = new Operation(Operation.OperationType.BEQ, f.getCurrBlock());
        Operand src1 = new Operand(Operand.OperandType.REGISTER, expr.getRegNum());
        Operand src2 = new Operand(Operand.OperandType.INTEGER, 0);
        Operand bbSrc = new Operand(Operand.OperandType.BLOCK, postBlock);
        branchOper.setSrcOperand(0, src1);
        branchOper.setSrcOperand(1, src2);
        branchOper.setSrcOperand(2, bbSrc);
        f.getCurrBlock().appendOper(branchOper);
        f.appendToCurrentBlock(whileBody);
        f.setCurrBlock(whileBody);
        stmt.genLLCode(f);
        expr.genLLCode(f);
        Operation branchNeqOper = new Operation(Operation.OperationType.BNE, f.getCurrBlock());
        Operand srcNeq1 = new Operand(Operand.OperandType.REGISTER, expr.getRegNum());
        Operand srcNeq2 = new Operand(Operand.OperandType.INTEGER, 0);
        Operand bbNeqSrc = new Operand(Operand.OperandType.BLOCK, whileBody);
        branchNeqOper.setSrcOperand(0, src1);
        branchNeqOper.setSrcOperand(1, src2);
        branchNeqOper.setSrcOperand(2, bbSrc);
        f.getCurrBlock().appendOper(branchNeqOper);
        f.appendToCurrentBlock(postBlock);
        f.setCurrBlock(postBlock);
        
    }
}
