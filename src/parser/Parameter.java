package parser;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Nate H
 */
public class Parameter extends Expression {
    
    private Identifier id;
    
    public Parameter(Identifier i){
        id = i;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    public void printParameter(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "Parameter");
        writer.newLine();
        id.printExpression(offset + "    ", writer);
    }
}
