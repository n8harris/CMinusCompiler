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
        IF_TOKEN,
        ELSE_TOKEN,
        RETURN_TOKEN,
        VOID_TOKEN,
        WHILE_TOKEN,
        PLUS_TOKEN,
        MINUS_TOKEN,
        MULT_TOKEN,
        DIV_TOKEN,
        LT_TOKEN,
        LTEQ_TOKEN,
        GT_TOKEN,
        GTEQ_TOKEN,
        EQ_TOKEN,
        NOTEQ_TOKEN,
        ASSIGN_TOKEN,
        SEMICOLON_TOKEN,
        COMMA_TOKEN,
        OPENPAREN_TOKEN,
        CLOSEPAREN_TOKEN,
        OPENBRACKET_TOKEN,
        CLOSEBRACKET_TOKEN,
        OPENCURLY_TOKEN,
        CLOSECURLY_TOKEN,
        OPENCOMMENT_TOKEN,
        CLOSECOMMENT_TOKEN,
        ID_TOKEN,
        NUM_TOKEN,
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
    
    public TokenType getType() {
        return null;
    }

    public void setType(TokenType e) {
        
    }

    // some access methods
}

