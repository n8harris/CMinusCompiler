/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

import java.io.BufferedReader;

/**
 *
 * @author Nate H
 */
public class CMinusScanner implements Scanner {
    private BufferedReader inFile;
    private Token nextToken;

    public CMinusScanner (BufferedReader file) {
        inFile = file;
        nextToken = scanToken();
    }
    public Token getNextToken () {
        Token returnToken = nextToken;
        if (nextToken.getType() != Token.TokenType.EOF_TOKEN)
            nextToken = scanToken();
        return returnToken;
    }
    public Token viewNextToken () {
        return nextToken;
    }
    
    public Token scanToken() {
        
    }

    
}
