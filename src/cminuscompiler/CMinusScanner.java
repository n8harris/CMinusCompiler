package cminuscompiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PushbackReader;
import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

/**
 *
 * @author Nate H
 */
public class CMinusScanner implements Scanner {
    private PushbackReader inFile;
    private Token nextToken;
    public enum stateType {
        START,
        INCOMMENT,
        INNUM,
        INID,
        DONE,
        FSLASH,
        LESSTHAN,
        GREATERTHAN,
        EQUALS,
        NOTEQUALS
    }
    private stateType state;
    
    public CMinusScanner (PushbackReader file) throws IOException {
        inFile = file;
        nextToken = scanToken();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        PushbackReader reader = new PushbackReader(new FileReader(args[0]));
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        CMinusScanner scanner = new CMinusScanner(reader);
        
        while(scanner.viewNextToken().getType() != Token.TokenType.EOF_TOKEN){
            Token currentToken = scanner.getNextToken();
            Token.TokenType type = currentToken.getType();
            
            if(type == Token.TokenType.ID_TOKEN || type == Token.TokenType.NUM_TOKEN){
                String data = currentToken.getData().toString();
                writer.write(type + ": " + data);
                writer.newLine();
            } else {
                if(type != Token.TokenType.OPENCOMMENT_TOKEN && type != Token.TokenType.CLOSECOMMENT_TOKEN){
                    writer.write(type.toString());
                    writer.newLine();
                }
            }
        }
        
        writer.close();
    }
    
    public Token getNextToken () {
        Token returnToken = nextToken;
        if (nextToken.getType() != Token.TokenType.EOF_TOKEN){
            try {
                nextToken = scanToken();
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        return returnToken;
    }
    public Token viewNextToken () {
        return nextToken;
    }
    
    public Token scanToken() throws IOException {
        boolean save;
        String tokenData = "";
        Token currentToken = null;
        state = stateType.START;
        
        char c = ' ';
        int r = -1;
        
        while(state != stateType.DONE) {
            r = inFile.read();
            if(r == -1){
                currentToken = new Token(Token.TokenType.EOF_TOKEN);
                return currentToken;
            }
            c = (char) r;
            save = true;
            
            switch(state) {
                case START:
                    if (isDigit(c)) {
                        state = stateType.INNUM;
                    }
                    else if (isLetter(c)) {
                        state = stateType.INID;
                    }
                    else if (c == '/') {
                        state = stateType.FSLASH;
                    }
                    else if (c == '>') {
                        state = stateType.GREATERTHAN;
                    }
                    else if (c == '<') {
                        state = stateType.LESSTHAN;
                    }
                    else if (c == '=') {
                        state = stateType.EQUALS;
                    }
                    else if (c == '!'){
                        state = stateType.NOTEQUALS;
                    }
                    else if (c == ' ' || c == '\t' || c == '\n' || c == '\r'){
                        continue;
                    }
                    else {
                        state = stateType.DONE;
                        switch (c) {
                            case '+':
                                currentToken = new Token(Token.TokenType.PLUS_TOKEN);
                                break;
                            case '-':
                                currentToken = new Token(Token.TokenType.MINUS_TOKEN);
                                break;
                            case '*':
                                currentToken = new Token(Token.TokenType.MULT_TOKEN);
                                break;
                            case '(':
                                currentToken = new Token(Token.TokenType.OPENPAREN_TOKEN);
                                break;
                            case ')':
                                currentToken = new Token(Token.TokenType.CLOSEPAREN_TOKEN);
                                break;
                            case ';':
                                currentToken = new Token(Token.TokenType.SEMICOLON_TOKEN);
                                break;
                            case ',':
                                currentToken = new Token(Token.TokenType.COMMA_TOKEN);
                                break;
                            case '[':
                                currentToken = new Token(Token.TokenType.OPENBRACKET_TOKEN);
                                break;
                            case ']':
                                currentToken = new Token(Token.TokenType.CLOSEBRACKET_TOKEN);
                                break;
                            case '{':
                                currentToken = new Token(Token.TokenType.OPENCURLY_TOKEN);
                                break;
                            case '}':
                                currentToken = new Token(Token.TokenType.CLOSECURLY_TOKEN);
                                break;
                            default:
                                throw new IOException("Error Scanning File"); 
                        }
                    }
                    break;
                    
                case NOTEQUALS:
                    if(c == '='){
                        currentToken = new Token (Token.TokenType.NOTEQ_TOKEN);
                        state = stateType.DONE;
                    } else {
                        throw new IOException("Illegal token");
                    }
                case FSLASH:
                    if (c == '*') {
                        currentToken = new Token ( Token.TokenType.OPENCOMMENT_TOKEN);
                        state = stateType.INCOMMENT;
                    }
                    else {
                        currentToken = new Token ( Token.TokenType.DIV_TOKEN);
                        inFile.unread(c);
                        state = stateType.DONE;
                    }
                    break;
                case GREATERTHAN:
                    if (c == '=') {
                        currentToken = new Token (Token.TokenType.GTEQ_TOKEN);                        
                        state = stateType.DONE;
                    }
                    else {
                        currentToken = new Token (Token.TokenType.GT_TOKEN); 
                        inFile.unread(c);
                        state = stateType.DONE;
                    }
                    
                    break;
                  case LESSTHAN:
                    if (c == '=') {
                        currentToken = new Token (Token.TokenType.LTEQ_TOKEN);
                        state = stateType.DONE;
                    }
                    else {
                        currentToken = new Token (Token.TokenType.LT_TOKEN);
                        inFile.unread(c);
                        state = stateType.DONE;
                    }
                    break;
                  case EQUALS:
                      if (c == '=') {
                          currentToken = new Token (Token.TokenType.EQ_TOKEN);
                          state = stateType.DONE;
                      }
                      else {
                          currentToken = new Token (Token.TokenType.ASSIGN_TOKEN);
                          inFile.unread(c);
                          state = stateType.DONE;
                      }
                    break;
                  case INNUM:
                      if (!isDigit(c)) {
                          save = true;
                          inFile.unread(c);
                          state = stateType.DONE;
                          currentToken = new Token (Token.TokenType.NUM_TOKEN, tokenData);
                      }
                    break;
                  case INID:
                      if (!isAlphabetic(c)) {
                          save = true;
                          inFile.unread(c);
                          state = stateType.DONE;
                          currentToken = new Token (Token.TokenType.ID_TOKEN, tokenData);
                      }
                    break;
                  case INCOMMENT:
                      if (c == '*') {
                          r = inFile.read();
                          c = (char) r;
                          if (c == '/') {
                              currentToken = new Token ( Token.TokenType.CLOSECOMMENT_TOKEN);
                              state = stateType.DONE;
                          }
                          else {
                              state = stateType.INCOMMENT;
                          }
                      }
                  case DONE:
                      break;
                  default:
                      state = stateType.DONE;                      
                      throw new IOException("You shouldn't have reached this state.");                      
            }
            
            if(state != stateType.DONE){
                if(c != ' ' && c != '\t' && c != '\n'){
                    tokenData += String.valueOf(c);
                }
            }
            if(state == stateType.DONE){
                if(currentToken.getType() == Token.TokenType.ID_TOKEN){
                    currentToken = lookupKeyword(tokenData, currentToken);
                }
            }
        }
        
        
        
        return currentToken;
    }
    
    public static Token lookupKeyword(String data, Token currentToken){
        Token newToken;
        switch(data){
            case "if":
                newToken = new Token(Token.TokenType.IF_TOKEN);
                return newToken;
            case "else":
                newToken = new Token(Token.TokenType.ELSE_TOKEN);
                return newToken;
            case "int":
                newToken = new Token(Token.TokenType.INT_TOKEN);
                return newToken;
            case "return":
                newToken = new Token(Token.TokenType.RETURN_TOKEN);
                return newToken;
            case "void":
                newToken = new Token(Token.TokenType.VOID_TOKEN);
                return newToken;
            case "while":
                newToken = new Token(Token.TokenType.WHILE_TOKEN);
                return newToken;
            default:
                return currentToken;
        }

    }

    
}
