package parser;

import cminuscompiler.CMinusCompiler;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import lowlevel.BasicBlock;
import lowlevel.CodeItem;
import lowlevel.Data;
import lowlevel.FuncParam;
import lowlevel.Function;

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
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    public void printProgram(BufferedWriter writer) throws IOException {
        String offset = "";
        writer.write(offset + "Program");
        writer.newLine();
        for (Declaration decl : declarationList) {
            decl.printDeclaration(offset + "    ", writer);
        }
        writer.close();
        
    }
    
    public CodeItem genLLCode() throws Exception{
        CodeItem returnItem = null;
        CodeItem tail = null;
        for(Declaration d : declarationList){
                if(d instanceof VarDeclaration){
                    VarDeclaration globalDecl = (VarDeclaration)d;
                    CodeItem next = globalDecl.genLLCode();
                    if(returnItem == null){
                        tail = next;
                        returnItem = tail;
                    } else {
                        tail.setNextItem(next);
                        tail = next;
                    }
                    
                } else {
                    FunctionDeclaration funDecl = (FunctionDeclaration)d;
                    CodeItem next = funDecl.genLLCode();
                    
                    
                    if(returnItem == null){
                        tail = next;
                        returnItem = tail;
                    } else {
                        tail.setNextItem(next);
                        tail = next;
                    }
                    
                    
                }
            }
        
        return returnItem;
    }
    
    
    
}
