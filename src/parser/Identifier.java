/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nate H
 */
public class Identifier extends Expression {
    
    private String id;
    private Expression arrayData;
    private ArrayList<Expression> args;
    
    public Identifier(String i){
        id = i;
    }
    
    public Identifier(String i, Expression a){
        id = i;
        arrayData = a;
    }
    
    public Identifier(String i, ArrayList<Expression> a){
        id = i;
        args = a;
    }
    
    public void printExpression(String offset, BufferedWriter writer) {
        try {
            writer.write(offset + "Identifier");
            writer.newLine();
            writer.write(offset + "    " + getId());
            writer.newLine();
            if(arrayData != null){
                writer.write(offset + "    " + "ArrayExpression");
                writer.newLine();
                getArrayData().printExpression(offset + "        ", writer);
            }
            if(args != null){
                writer.write(offset + "    " + "Args:");
                writer.newLine();
                for (Expression exp : getArgs()) {
                    exp.printExpression(offset + "        ", writer);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Identifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the arrayData
     */
    public Expression getArrayData() {
        return arrayData;
    }

    /**
     * @param arrayData the arrayData to set
     */
    public void setArrayData(Expression arrayData) {
        this.arrayData = arrayData;
    }

    /**
     * @return the args
     */
    public ArrayList<Expression> getArgs() {
        return args;
    }

    /**
     * @param args the args to set
     */
    public void setArgs(ArrayList<Expression> args) {
        this.args = args;
    }
}
