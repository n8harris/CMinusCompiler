/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;
import scanner.CMinusScanner;
import scanner.Token;

/**
 *
 * @author Nate H
 */
public class CMinusParser {
    
    private CMinusScanner scanner;
    
    public CMinusParser(CMinusScanner scan){
        scanner = scan;
    }
    
    public Program startParse() throws Exception{
            
            return parseProgram();
    }
    
    public void match(Token.TokenType t) throws Exception{
        if(scanner.viewNextToken().getType() == t){
            scanner.getNextToken();
        } else {
            throw new Exception("Error");
        }
    }
    
    public Program parseProgram() throws Exception{
        ArrayList<Declaration> declList = new ArrayList<>();
        while(scanner.viewNextToken().getType() == Token.TokenType.VOID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.INT_TOKEN){
            declList.add(parseDeclaration());
        }
        
        Program p = new Program(declList);
        return p;
        
    }
    
    public Declaration parseDeclaration() throws Exception{
        switch(scanner.viewNextToken().getType()) {
            case VOID_TOKEN:
                match(Token.TokenType.VOID_TOKEN);
                parseIdentifier();
                parseFunctionDeclaration();                
                break;
            case INT_TOKEN:
                match(Token.TokenType.INT_TOKEN);
                
        }
    }
    
}
