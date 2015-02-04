package cminuscompiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

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
        Boolean save;
        
        char c = ' ';
        int r = -1;
        
        while(state != stateType.DONE) {
            r = inFile.read();
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
                    else if ((c == ' ') || (c == '\t') || (c == '\n')) {
                        save = false;
                    }
                    else if (c == '/') {
                        save = false;
                        state = stateType.INCOMMENT;
                    }
                    else {
                        state = stateType.DONE;
                        switch (c) {
                            //More stuff goes here
                        }
                    }
            }
            
            
        }
        
        return null;
    }

    
}
