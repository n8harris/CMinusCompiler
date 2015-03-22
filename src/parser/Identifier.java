/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

/**
 *
 * @author Nate H
 */
public class Identifier {
    
    private String id;
    
    public Identifier(String i){
        id = i;
    }
    
    public void printIdentifier(String offset) {
        System.out.println(offset + "Identifier");
        System.out.println(offset + id);
    }
}
