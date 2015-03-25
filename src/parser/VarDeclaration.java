package parser;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Nate H
 */
public class VarDeclaration extends Declaration {
    
    Numeric num;
    Identifier id;
    
    public VarDeclaration(Numeric n, Identifier i){
        id = i;
        num = n;
        
    }
    
    public VarDeclaration(Identifier i){
        id = i;
    }
    
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    @Override
    public void printDeclaration(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "VarDeclaration");
        writer.newLine();
        if(num != null){
            writer.write(offset + "    " + "ArraySize");
            writer.newLine();
            num.printExpression(offset + "        ", writer);
        }
        id.printExpression(offset + "    ", writer);
    }
}
