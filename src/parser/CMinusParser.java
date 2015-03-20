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
    
    public Program startParse(){
        //Check to make sure we are not at the end of the file
            Program p = null;
            p = parseProgram();
            
            return p;
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
            Token.TokenType currentToken = scanner.viewNextToken().getType();
            switch(currentToken){
                case VOID_TOKEN: 
                    match(Token.TokenType.VOID_TOKEN);
                    
                    break;
            }
        }
    }
    
    
}
