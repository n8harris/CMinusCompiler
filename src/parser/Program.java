package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import lowlevel.CodeItem;
import lowlevel.Data;

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
        CodeItem lowLevelCode = null;
        for(Declaration d : declarationList){
            switch (d.getClass().getName()){
                case "VarDeclaration":
                    VarDeclaration globalDecl = (VarDeclaration)d;
                    Data globalVar = new Data(1, globalDecl.getId().getId());
                    lowLevelCode.setNextItem(globalVar);
                    break;
                case "FunctionDeclaration":
                    break;
                default:
                    throw new Exception();
            }
        }
        
        return lowLevelCode;
    }
    
    
    
}
