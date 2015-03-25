package parser;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Nate H
 */
public abstract class Statement {
    //Sets up a prototype for the print functions in classes that extend this one.
    public void printStatement(String offset, BufferedWriter writer) throws IOException{
    }
    
}
