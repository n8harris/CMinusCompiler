package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

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
}
