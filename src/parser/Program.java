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
        CodeItem lowLevelCode = null;
        for(Declaration d : declarationList){
                if(d instanceof VarDeclaration){
                    VarDeclaration globalDecl = (VarDeclaration)d;
                    Data globalVar = new Data(Data.TYPE_INT, globalDecl.getId().getId());
                    CMinusCompiler.globalHash.put(globalDecl.getId().getId(), globalDecl.getId().getId());
                    if(lowLevelCode == null){
                        lowLevelCode = globalVar;
                    } else {
                        lowLevelCode.setNextItem(globalVar);
                    }
                    
                } else {
                    FunctionDeclaration funDecl = (FunctionDeclaration)d;
                    Function f = new Function(funDecl.getType(), funDecl.getId().getId());
                    if(funDecl.getParams() != null){
                        FuncParam params = new FuncParam();
                        FuncParam nextParam = new FuncParam();
                        for(Parameter p : funDecl.getParams()){
                            if(params.getName() == null){
                                params.setname(p.getId().getId());
                                params.setType(Data.TYPE_INT);
                                f.getTable().put(f.getNewRegNum(), p.getId().getId());
                                nextParam = params;
                            } else {
                                FuncParam tempParam = new FuncParam(Data.TYPE_INT, p.getId().getId());
                                nextParam.setNextParam(tempParam);
                                f.getTable().put(f.getNewRegNum(), p.getId().getId());
                                nextParam = tempParam;
                            }
                        }

                        f.setFirstParam(params);
                    }
                    f.createBlock0();
                    BasicBlock newBlock = new BasicBlock(f);
                    f.appendBlock(newBlock);
                    f.setCurrBlock(newBlock);
                    //funDecl.getCmpdStatement().genLLCode(f);
                    
                    if(lowLevelCode == null){
                        lowLevelCode = f;
                    } else {
                        lowLevelCode.setNextItem(f);
                    }
                    
                    
                }
            }
        
        return lowLevelCode;
    }
    
    
    
}
