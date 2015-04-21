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
public class IfStatement extends Statement {
    
    Expression expr;
    Statement thenStmt;
    Statement elseStmt;

    public IfStatement (Expression express, Statement stmt) {
        this (express, stmt, null);
    }

    public IfStatement (Expression express, Statement stmt1, Statement stmt2) {
        expr = express;
        thenStmt = stmt1;
        elseStmt = stmt2;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    @Override
    public void printStatement(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "IfStatement");
        writer.newLine();
        expr.printExpression(offset + "    ", writer);
        thenStmt.printStatement(offset + "    ", writer);
        if(elseStmt != null){
            writer.write(offset + "    " + "ElseStatement");
            writer.newLine();
            elseStmt.printStatement(offset + "        ", writer);
        }
    }
    
    public void genLLCode(Function f) throws Exception{
        expr.genLLCode(f);
        BasicBlock thenBlock = new BasicBlock(f);
        BasicBlock postBlock = new BasicBlock(f);
        Operation branchOper = new Operation(Operation.OperationType.BEQ, f.getCurrBlock());
        Operand src1 = new Operand(Operand.OperandType.REGISTER, expr.getRegNum());
        Operand src2 = new Operand(Operand.OperandType.INTEGER, 0);
        
        if(elseStmt == null){
            
            Operand bbSrc = new Operand(Operand.OperandType.BLOCK, postBlock);
            branchOper.setSrcOperand(0, src1);
            branchOper.setSrcOperand(1, src2);
            branchOper.setSrcOperand(2, bbSrc);
            f.getCurrBlock().appendOper(branchOper);
            f.appendToCurrentBlock(thenBlock);
            f.setCurrBlock(thenBlock);
            thenStmt.genLLCode(f);
            f.appendToCurrentBlock(postBlock);
            f.setCurrBlock(postBlock);
            
        } else {

            BasicBlock elseBlock = new BasicBlock(f);
            Operand bbSrc = new Operand(Operand.OperandType.BLOCK, elseBlock);
            branchOper.setSrcOperand(0, src1);
            branchOper.setSrcOperand(1, src2);
            branchOper.setSrcOperand(2, bbSrc);
            f.getCurrBlock().appendOper(branchOper);
            f.appendToCurrentBlock(thenBlock);
            f.setCurrBlock(thenBlock);
            thenStmt.genLLCode(f);
            f.appendToCurrentBlock(postBlock);
            f.setCurrBlock(elseBlock);
            elseStmt.genLLCode(f);
            Operation jmpOperation = new Operation(Operation.OperationType.JMP, f.getCurrBlock());
            Operand jumpLoc = new Operand(Operand.OperandType.BLOCK, postBlock);
            jmpOperation.setSrcOperand(0, jumpLoc);
            f.getCurrBlock().appendOper(jmpOperation);
            f.appendUnconnectedBlock(elseBlock);
            f.setCurrBlock(postBlock);
        }
        
    }

    
}
