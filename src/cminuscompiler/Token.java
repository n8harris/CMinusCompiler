/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cminuscompiler;

/**
 *
 * @author Nate H, Jake P
 */
public class Token {
    
    public enum TokenType {
        INT_TOKEN,
        DOUBLE_TOKEN,
        IF_TOKEN,
        EOF_TOKEN
        // rest of tokens ....
    }


    private TokenType tokenType;
    private Object tokenData;

    public Token (TokenType type) {
        this (type, null);
    }

    public Token (TokenType type, Object data) {
        tokenType = type;
        tokenData = data;
    }

    // some access methods
}

