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
public class CompoundStatement extends Statement {
    
    private ArrayList<VarDeclaration> localDecl;
    private ArrayList<Statement> stmtList;
    
    public CompoundStatement(ArrayList<VarDeclaration> l, ArrayList<Statement> s){
        super();
        localDecl = l;
        stmtList = s;
    }
    
}
