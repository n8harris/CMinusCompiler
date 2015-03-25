package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nate H
 */
public class FunctionDeclaration extends Declaration {
    
    private ArrayList<Parameter> params;
    private CompoundStatement cmpdStatement;
    private Identifier id;
    
    public FunctionDeclaration(ArrayList<Parameter> p, CompoundStatement c, Identifier i){
        id = i;
        params = p;
        cmpdStatement = c;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    @Override
    public void printDeclaration(String offset, BufferedWriter writer){
        try {
            writer.write(offset + "FunctionDeclaration");
            writer.newLine();
            id.printExpression(offset + "    ", writer);
            if(params.size() > 0){
                for (Parameter param : params) {
                    if(param != null){
                        param.printParameter(offset + "    ", writer);
                    }
                }
            }
            cmpdStatement.printStatement(offset + "    ", writer);
        } catch (IOException ex) {
            Logger.getLogger(FunctionDeclaration.class.getName()).log(Level.SEVERE, null, ex);
        }
                       
    }
    
}
