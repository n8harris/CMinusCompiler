package cminuscompiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Character.isAlphabetic;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import java.util.List;

/**
 *
 * @author Nate H
 */
public class CMinusScanner implements Scanner {
    private BufferedReader inFile;
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
    
    public CMinusScanner (BufferedReader file) throws IOException {
        inFile = file;
        nextToken = scanToken();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        CMinusScanner scanner = new CMinusScanner(reader);
        
        while(scanner.viewNextToken().getType() != Token.TokenType.EOF_TOKEN){
            Token currentToken = scanner.getNextToken();
            Token.TokenType type = currentToken.getType();
            String data = currentToken.getData().toString();
            if(type == Token.TokenType.ID_TOKEN || type == Token.TokenType.NUM_TOKEN){
                writer.write(type + ": " + data);
                writer.newLine();
            } else {
                writer.write(type.toString());
                writer.newLine();
            }
        }
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
        boolean readNext = true;
        List<String> tokenData;
        Token currentToken = null;
        state = stateType.START;
        
        char c = ' ';
        int r = -1;
        
        while(state != stateType.DONE) {
            if(readNext){
                r = inFile.read();
                if(r == -1){
                    currentToken = new Token(Token.TokenType.EOF_TOKEN);
                    return currentToken;
                }
            }
            c = (char) r;
            save = true;
            
            switch(state) {
                case START:
                    if (isDigit(c)) {
                        state = stateType.INNUM;
                        inFile.mark(2);
                    }
                    else if (isLetter(c)) {
                        state = stateType.INID;
                        inFile.mark(2);
                    }
                    else if ((c == ' ') || (c == '\t') || (c == '\n')) {
                        save = false;
                    }
                    else if (c == '/') {
                        save = false;
                        inFile.mark(2);
                    }
                    else if (c == '>') {
                        state = stateType.GREATERTHAN;
                        inFile.mark(2);
                    }
                    else if (c == '<') {
                        state = stateType.LESSTHAN;
                        inFile.mark(2);
                    }
                    else if (c == '=') {
                        state = stateType.EQUALS;
                        inFile.mark(2);
                    }
                    else if (c == '!'){
                        state = stateType.NOTEQUALS;
                        inFile.mark(2);
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
                    readNext = true;
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
                        currentToken = new Token (Token.TokenType.OPENCOMMENT_TOKEN);
                        readNext = true;
                        state = stateType.INCOMMENT;
                    }
                    else {
                        currentToken = new Token ( Token.TokenType.DIV_TOKEN);
                        inFile.reset();
                        state = stateType.DONE;
                    }
                    break;
                case GREATERTHAN:
                    if (c == '=') {
                        currentToken = new Token (Token.TokenType.GTEQ_TOKEN);                        
                        readNext = true;
                        state = stateType.DONE;
                    }
                    else {
                        currentToken = new Token (Token.TokenType.GT_TOKEN); 
                        inFile.reset();
                        state = stateType.DONE;
                    }
                    
                    break;
                  case LESSTHAN:
                    if (c == '=') {
                        currentToken = new Token (Token.TokenType.LTEQ_TOKEN);
                        readNext = true;
                        state = stateType.DONE;
                    }
                    else {
                        currentToken = new Token (Token.TokenType.LT_TOKEN);
                        inFile.reset();
                        state = stateType.DONE;
                    }
                    break;
                  case EQUALS:
                      if (c == '=') {
                          currentToken = new Token (Token.TokenType.EQ_TOKEN);
                          readNext = true;
                          state = stateType.DONE;
                      }
                      else {
                          currentToken = new Token (Token.TokenType.ASSIGN_TOKEN);
                          inFile.reset();
                          state = stateType.DONE;
                      }
                    break;
                  case INNUM:
                      if (!isDigit(c)) {
                          save = false;
                          inFile.reset();
                          state = stateType.DONE;
                          currentToken = new Token (Token.TokenType.NUM_TOKEN);
                      }
                    break;
                  case INID:
                      if (!isAlphabetic(c)) {
                          save = false;
                          inFile.reset();
                          state = stateType.DONE;
                          currentToken = new Token (Token.TokenType.ID_TOKEN);
                      }
                    break;
                  case INCOMMENT:
                      if (c == '*') {
                          r = inFile.read();
                          c = (char) r;
                          if (c == '/') {
                              currentToken = new Token (Token.TokenType.CLOSECOMMENT_TOKEN);
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
        }
        
        return currentToken;
    }

    
}
