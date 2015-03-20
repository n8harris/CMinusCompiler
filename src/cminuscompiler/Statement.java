/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author Nate H
 */
public abstract class Statement {
    
    private ExpressionStatement expStatement;
    private CompoundStatement cmpdStatement;
    private IfStatement selStatement;
    private IterationStatement itStatement;
    private ReturnStatement retStatement;
    
    public Statement(ExpressionStatement e){
        expStatement = e;
    }
    
    public Statement(CompoundStatement c){
        cmpdStatement = c;
    }
    
    public Statement(IfStatement i){
        selStatement = i;
    }
    
    public Statement(IterationStatement i){
        itStatement = i;
    }
    
    public Statement(ReturnStatement r){
        retStatement = r;
    }
    
    public Statement(){}
    
}
