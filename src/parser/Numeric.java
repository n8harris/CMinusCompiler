/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Nate H
 */
public class Numeric extends Expression {
    
    private String num;
    
    public Numeric (String n){
        num = n;
    }
    public void printNumeric(String offset){
        System.out.println(offset + "Numeric");
        System.out.println(offset + num);
    }
}
