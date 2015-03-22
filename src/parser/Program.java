/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;

/**
 *
 * @author Nate H
 */
public class Program {
    
    private ArrayList<Declaration> declarationList;
    
    public Program(ArrayList<Declaration> decl){
        declarationList = decl;
    }

    /**
     * @return the declarationList
     */
    public ArrayList<Declaration> getDeclarationList() {
        return declarationList;
    }

    /**
     * @param declarationList the declarationList to set
     */
    public void setDeclarationList(ArrayList<Declaration> declarationList) {
        this.declarationList = declarationList;
    }
    
    public void printProgram() {
        String offset = "";
        System.out.println("Program");
        for (Declaration decl : declarationList) {
            decl.printDeclaration(offset + "    ");
        }
        
    }
    
    
    
}
