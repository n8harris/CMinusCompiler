package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import lowlevel.Function;

/**
 *
 * @author Nate H
 */
public abstract class Expression {
    private int regNum;
    //Sets up a prototype for the print functions in classes that extend this one.
    public void printExpression(String offset, BufferedWriter writer) throws IOException{
    }

    /**
     * @return the regNum
     */
    public int getRegNum() {
        return regNum;
    }

    /**
     * @param regNum the regNum to set
     */
    public void setRegNum(int regNum) {
        this.regNum = regNum;
    }
    
    public abstract void genLLCode(Function f) throws Exception;
    
}
