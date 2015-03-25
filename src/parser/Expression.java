package parser;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Nate H
 */
public abstract class Expression {
    //Sets up a prototype for the print functions in classes that extend this one.
    public void printExpression(String offset, BufferedWriter writer) throws IOException{
    }
    
}
