package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import lowlevel.Function;

/**
 *
 * @author Nate H
 */
public class CompoundStatement extends Statement {
    
    private ArrayList<VarDeclaration> localDecl;
    private ArrayList<Statement> stmtList;
    
    public CompoundStatement(ArrayList<VarDeclaration> l, ArrayList<Statement> s){
        super();
        localDecl = l;
        stmtList = s;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    public void printStatement(String offset, BufferedWriter writer) throws IOException {
        writer.write(offset + "CompoundStatement");
        writer.newLine();
        for (VarDeclaration vardecl : getLocalDecl()) {
            vardecl.printDeclaration(offset + "    ", writer);
        }
        
        for (Statement stmt : getStmtList()) {
            stmt.printStatement(offset + "    ", writer);
        }
    }

    /**
     * @return the localDecl
     */
    public ArrayList<VarDeclaration> getLocalDecl() {
        return localDecl;
    }

    /**
     * @param localDecl the localDecl to set
     */
    public void setLocalDecl(ArrayList<VarDeclaration> localDecl) {
        this.localDecl = localDecl;
    }

    /**
     * @return the stmtList
     */
    public ArrayList<Statement> getStmtList() {
        return stmtList;
    }

    /**
     * @param stmtList the stmtList to set
     */
    public void setStmtList(ArrayList<Statement> stmtList) {
        this.stmtList = stmtList;
    }
    
    @Override
    public void genLLCode(Function f) throws Exception{
        for(VarDeclaration v : localDecl){
            f.getTable().put(v.getId().getId(), f.getNewRegNum());
        }
        
        for(Statement currStmt : stmtList){
            currStmt.genLLCode(f);
        }
    }
}
