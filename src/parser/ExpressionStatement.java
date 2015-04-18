package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.Function;

/**
 *
 * @author Nate H
 */
public class ExpressionStatement extends Statement {
    
    private Expression exp;
    
    public ExpressionStatement(Expression e){
        super();
        exp = e;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    @Override
    public void printStatement(String offset, BufferedWriter writer) throws IOException{
        exp.printExpression(offset, writer);
    }
    
    public void genLLCode(Function f) throws Exception{
        exp.genLLCode(f);
    }
    
}
