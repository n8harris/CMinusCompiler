package parser;

import java.io.BufferedWriter;
import java.io.IOException;

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
}
