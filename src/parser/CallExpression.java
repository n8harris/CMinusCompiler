package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import lowlevel.Attribute;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author Nate H
 */
public class CallExpression extends Expression {
    
    private Identifier id;
    private ArrayList<Expression> args;
    
    public CallExpression(Identifier i, ArrayList<Expression> a){
        id = i;
        args = a;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    @Override
    public void printExpression(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "CallExpression");
        writer.newLine();
        id.printExpression(offset + "    ", writer);
        for (Expression expr : args) {
            expr.printExpression(offset + "    ", writer);
        }
    }
    
    @Override
    public void genLLCode(Function f) throws Exception{
        int parmNum = 0;
        for(Expression exp : args){
            exp.genLLCode(f);
            Operation newOper =
            new Operation(Operation.OperationType.PASS, f.getCurrBlock());
            newOper.addAttribute(new Attribute("PARAM_NUM", Integer.toString(parmNum)));
            Operand src = new Operand(Operand.OperandType.REGISTER, exp.getRegNum());
            newOper.setSrcOperand(0, src);
            f.getCurrBlock().appendOper(newOper);
            parmNum++;
        }
        
        Operation callOper =
        new Operation(Operation.OperationType.CALL, f.getCurrBlock());
        callOper.addAttribute(new Attribute("numParams", Integer.toString(args.size())));
        Operand src = new Operand(Operand.OperandType.STRING, id.getId());
        callOper.setSrcOperand(0, src);
        f.getCurrBlock().appendOper(callOper);
        
        Operation retOper =
        new Operation(Operation.OperationType.ASSIGN, f.getCurrBlock());
        Operand retSrc = new Operand(Operand.OperandType.MACRO, "RetReg");
        this.setRegNum(f.getNewRegNum());
        Operand dest = new Operand(Operand.OperandType.REGISTER, this.getRegNum());
        retOper.setSrcOperand(0, retSrc);
        retOper.setDestOperand(0, dest);
        f.getCurrBlock().appendOper(retOper);
        
    }
}
