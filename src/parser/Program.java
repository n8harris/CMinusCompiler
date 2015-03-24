/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.io.BufferedWriter;
import java.io.IOException;
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
    
    public void printProgram(BufferedWriter writer) throws IOException {
        String offset = "";
        writer.write(offset + "Program");
        writer.newLine();
        for (Declaration decl : declarationList) {
            decl.printDeclaration(offset + "    ", writer);
        }
        writer.close();
        
    }
    
    
    
}
