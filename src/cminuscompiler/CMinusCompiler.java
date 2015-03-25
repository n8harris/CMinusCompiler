package cminuscompiler;

import scanner.CMinusScanner;
import scanner.Token;
import parser.CMinusParser;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PushbackReader;
import parser.Program;

/**
 *
 * @author Jake P
 */
public class CMinusCompiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, Exception {
        //First command line argument is input, second is output
        try{
            PushbackReader reader = new PushbackReader(new FileReader(args[0]));
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[0].substring(0, args[0].lastIndexOf(".")) + "Tree.ast"));
            CMinusScanner scanner = new CMinusScanner(reader);
            //These two lines create the parser and start it. As the name 'recursive descent' implies, it works recursively.
            CMinusParser parser = new CMinusParser(scanner);
            Program parseResult = parser.startParse();
            
            //Calls the print routine. This works recursively as well, each Class's print routine will call
            //the appropriate print function on any child objects that it has, or print the data for terminals
            parseResult.printProgram(writer);

        }
        catch(FileNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
