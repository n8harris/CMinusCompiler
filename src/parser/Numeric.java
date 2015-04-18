package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lowlevel.Function;

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
    }
}
