package cminuscompiler;

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
 * @author Nate H, Jake P
 */
public class CMinusScanner implements Scanner {
    private PushbackReader inFile; //PushbackReader so that we can unread chars
    private Token nextToken;
    //enum for referencing specific states
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
    //Our state variable which will change throughout execution
    private stateType state;
    
    public CMinusScanner (PushbackReader file) throws IOException {
        inFile = file;
        nextToken = scanToken(); //Instantiate a CMinusScanner with the first token in the file
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //First command line argument is input, second is output
        PushbackReader reader = new PushbackReader(new FileReader(args[0]));
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        CMinusScanner scanner = new CMinusScanner(reader);
        
        //Check to make sure we are not at the end of the file
        while(scanner.viewNextToken().getType() != Token.TokenType.EOF_TOKEN){
            //Get the next token and check the type
            Token currentToken = scanner.getNextToken();
            Token.TokenType type = currentToken.getType();
            //If token is identifier or num, print out the value too
            if(type == Token.TokenType.ID_TOKEN || type == Token.TokenType.NUM_TOKEN){
                String data = currentToken.getData().toString();
                writer.write(type + ": " + data);
                writer.newLine();
            } else {
                //Don't print out the comment tokens
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
        String tokenData = "";
        Token currentToken = null;
        state = stateType.START; //State begins in start state
        
        char c = ' ';
        int r = -1;
        
        //Loop for grabbing a token. When a token is found, state will be set to DONE
        while(state != stateType.DONE) {
            r = inFile.read(); //Read next char in file
            //If we have not gotten to end of file (EOF returns -1)
            if(r == -1){
                currentToken = new Token(Token.TokenType.EOF_TOKEN);
                return currentToken;
            }
            c = (char) r; //Explicitly cast what read() returns to a char
            
            switch(state) {
                case START:
                    //Check for possible kinds of tokens
                    //If character does not associate with valid token, throw exception
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
                        //One character tokens
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
                                throw new IOException("Unknown Token"); 
                        }
                    }
                    break;
                    
                case NOTEQUALS:
                    if(c == '='){
                        currentToken = new Token (Token.TokenType.NOTEQ_TOKEN);
                        state = stateType.DONE;
                    } else {
                        //Invalid token if next char is anything but "="
                        throw new IOException("Illegal token: !" + c);
                    }
                case FSLASH:
                    //State which could be either start of comment or divide
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
                    //State which could be greater than or greater than or equal to
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
                    //State which could be either less than or less than or equal to
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
                      //State which could be assign or compare
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
                      //Stay in this case till a non-num is found
                      if (!isDigit(c)) {
                          inFile.unread(c);
                          state = stateType.DONE;
                          currentToken = new Token (Token.TokenType.NUM_TOKEN, tokenData);
                      }
                    break;
                  case INID:
                      //Stay in this case till a non-letter is found
                      if (!isAlphabetic(c)) {
                          inFile.unread(c);
                          state = stateType.DONE;
                          currentToken = new Token (Token.TokenType.ID_TOKEN, tokenData);
                      }
                    break;
                  case INCOMMENT:
                      //Stay in this state till we get the closing comment token
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
                //Save data to tokenData (will always be saved, but will only
                //be saved for ID and NUM)
                if(c != ' ' && c != '\t' && c != '\n'){
                    tokenData += String.valueOf(c);
                }
            }
            if(state == stateType.DONE){
                //If token is identifier, lookup the token and compare with keywords
                if(currentToken.getType() == Token.TokenType.ID_TOKEN){
                    currentToken = lookupKeyword(tokenData, currentToken);
                }
            }
        }
        
        
        
        return currentToken;
    }
    
    public static Token lookupKeyword(String data, Token currentToken){
        //If token matches predefined keyword, return a keyword 
        //Otherwise, just return an identifier token
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
