package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lowlevel.Function;
import lowlevel.Operand;
import lowlevel.Operation;

/**
 *
 * @author Nate H
 */
public class Numeric extends Expression {
    
    private int num;
    
    public Numeric (int n){
        num = n;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    @Override
    public void printExpression(String offset, BufferedWriter writer){
        try {
            writer.write(offset + "Numeric");
            writer.newLine();
            writer.write(offset + "    " + num);
            writer.newLine();
        } catch (IOException ex) {
            Logger.getLogger(Numeric.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void genLLCode(Function f){
        this.setRegNum(f.getNewRegNum());
        Operation newOper =
        new Operation(Operation.OperationType.ASSIGN, f.getCurrBlock());
        Operand src = new Operand(Operand.OperandType.INTEGER, this.num);
        Operand dest = new Operand(Operand.OperandType.REGISTER, this.getRegNum());
        newOper.setSrcOperand(0, src);
        newOper.setDestOperand(0, dest);
        f.getCurrBlock().appendOper(newOper);
        
    }
}
