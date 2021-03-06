package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.Function;

/**
 *
 * @author Nate H
 */
public class Parameter extends Expression {
    
    private Identifier id;
    
    public Parameter(Identifier i){
        id = i;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    public void printParameter(String offset, BufferedWriter writer) throws IOException{
        writer.write(offset + "Parameter");
        writer.newLine();
        getId().printExpression(offset + "    ", writer);
    }

    /**
     * @return the id
     */
    public Identifier getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Identifier id) {
        this.id = id;
    }
    
    public void genLLCode(Function f) throws Exception{
        id.genLLCode(f);
    }
}
