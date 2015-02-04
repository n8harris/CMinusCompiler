package cminuscompiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
    
    public CMinusScanner (BufferedReader file) throws IOException {
        inFile = file;
        nextToken = scanToken();
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        CMinusScanner scanner = new CMinusScanner(reader);
        
        while(scanner.nextToken.getType() != Token.TokenType.EOF_TOKEN){
            writer.write(scanner.getNextToken().toString());
            writer.newLine();
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
        char c = ' ';
        int r = -1;
        while(state != stateType.DONE) {
            r = inFile.read();
            c = (char) r;
            
            
            
        }
        
        return null;
    }

    
}
