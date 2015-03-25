package parser;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Nate H
 */
public class IterationStatement extends Statement {
    
    Expression expr;
    Statement stmt;
    
    public IterationStatement(Expression e, Statement s){
        expr = e;
        stmt = s;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    @Override
    public void printStatement(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "IterationStatement");
        writer.newLine();
        expr.printExpression(offset + "    ", writer);
        stmt.printStatement(offset + "    ", writer);
    }
}
