/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cminuscompiler;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PushbackReader;

/**
 *
 * @author Jake-
 */
public class CMinusCompiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //First command line argument is input, second is output
        try{
            PushbackReader reader = new PushbackReader(new FileReader(args[0]));
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
            CMinusScanner scanner = new CMinusScanner(reader);
            CMinusParser parser = new CMinusParser(scanner);
            
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
        catch(FileNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
