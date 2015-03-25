package parser;

import java.io.BufferedWriter;
import java.io.IOException;

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
    
}
