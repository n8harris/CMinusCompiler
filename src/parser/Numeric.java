/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nate H
 */
public class Numeric extends Expression {
    
    private String num;
    
    public Numeric (String n){
        num = n;
    }
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
}
