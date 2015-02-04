package cminuscompiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
    }
    private stateType state;
    
    public CMinusScanner (BufferedReader file) {
        inFile = file;
        nextToken = scanToken();
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        CMinusScanner scanner = new CMinusScanner(reader);
        
        scanner.scanToken();
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
    
    public Token scanToken() throws IOException {
        char c = ' ';
        int r = -1;
        while(state != stateType.DONE) {
            r = inFile.read();
            c = (char) r;
            
            
            
        }
        
        return null;
    }

    
}
