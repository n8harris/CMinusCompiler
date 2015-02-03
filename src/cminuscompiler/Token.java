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

    private TokenType tokenType;
    private Object tokenData;

    public Token (type) {
        this (type, null);
    }

    public Token (TokenType type, Object data) {
        tokenType = type;
        tokenData = data;
    }

    // some access methods
}

