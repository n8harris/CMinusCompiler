package parser;

import cminuscompiler.CMinusCompiler;
import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author Nate H
 */
public class AssignExpression extends Expression {
    
    private Identifier id;
    private Expression expr;
    
    public AssignExpression(Identifier i, Expression e){
        id = i;
        expr = e;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    public void printExpression(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "AssignExpression");
        writer.newLine();
        id.printExpression(offset + "    ", writer);
        expr.printExpression(offset + "    ", writer);
    }
    
    @Override
    public void genLLCode(Function f) throws Exception{
        expr.genLLCode(f);
        if(f.getTable().containsKey(id)){
            Operation newOper =
            new Operation(Operation.OperationType.ASSIGN, f.getCurrBlock());
            Operand src = new Operand(Operand.OperandType.REGISTER, expr.getRegNum());
            Operand dest = new Operand(Operand.OperandType.REGISTER, (int)f.getTable().get(id));
            newOper.setSrcOperand(0, src);
            newOper.setDestOperand(0, dest);
            f.getCurrBlock().appendOper(newOper);
        } else if(CMinusCompiler.globalHash.containsKey(id)){
            if(CMinusCompiler.globalHash.get(id) == null){
                CMinusCompiler.globalHash.put(id, f.getNewRegNum());
            }
            
            Operation newOper =
            new Operation(Operation.OperationType.ASSIGN, f.getCurrBlock());
            Operand src = new Operand(Operand.OperandType.REGISTER, expr.getRegNum());
            Operand dest = new Operand(Operand.OperandType.REGISTER, (int)CMinusCompiler.globalHash.get(id));
            newOper.setSrcOperand(0, src);
            newOper.setDestOperand(0, dest);
            f.getCurrBlock().appendOper(newOper);
        } else {
            throw new Exception("Could not find variable");
        }
    }
}
