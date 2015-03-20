/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author Nate H
 */
public class CompoundStatement extends Statement {
    
    private LocalDeclaration localDecl;
    private StatementList stmtList;
    
    public CompoundStatement(LocalDeclaration l, StatementList s){
        super();
        localDecl = l;
        stmtList = s;
    }
    
}
