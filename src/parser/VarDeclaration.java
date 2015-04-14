package parser;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Nate H
 */
public class VarDeclaration extends Declaration {
    
    private Numeric num;
    private Identifier id;
    
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
        if(getNum() != null){
            writer.write(offset + "    " + "ArraySize");
            writer.newLine();
            getNum().printExpression(offset + "        ", writer);
        }
        getId().printExpression(offset + "    ", writer);
    }


    /**
     * @return the num
     */
    public Numeric getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(Numeric num) {
        this.num = num;
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
}
