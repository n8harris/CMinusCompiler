package parser;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Nate H
 */
public abstract class Statement {
    
    private int regNum;
    //Sets up a prototype for the print functions in classes that extend this one.
    public void printStatement(String offset, BufferedWriter writer) throws IOException{
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
    
}
