package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lowlevel.BasicBlock;
import lowlevel.Data;
import lowlevel.FuncParam;
import lowlevel.Function;

/**
 *
 * @author Nate H
 */
public class FunctionDeclaration extends Declaration {
    
    private ArrayList<Parameter> params;
    private CompoundStatement cmpdStatement;
    private Identifier id;
    private int type;
    
    public FunctionDeclaration(ArrayList<Parameter> p, CompoundStatement c, Identifier i, int t){
        id = i;
        params = p;
        cmpdStatement = c;
        type = t;
    }
    //Prints out the contents of BinaryExpression by recursively calling each objects
    //print function, or by printing the data in the case of a terminal.
    @Override
    public void printDeclaration(String offset, BufferedWriter writer){
        try {
            writer.write(offset + "FunctionDeclaration");
            writer.newLine();
            getId().printExpression(offset + "    ", writer);
            if(getParams().size() > 0){
                for (Parameter param : getParams()) {
                    if(param != null){
                        param.printParameter(offset + "    ", writer);
                    }
                }
            }
            getCmpdStatement().printStatement(offset + "    ", writer);
        } catch (IOException ex) {
            Logger.getLogger(FunctionDeclaration.class.getName()).log(Level.SEVERE, null, ex);
        }
                       
    }

    /**
     * @return the params
     */
    public ArrayList<Parameter> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(ArrayList<Parameter> params) {
        this.params = params;
    }

    /**
     * @return the cmpdStatement
     */
    public CompoundStatement getCmpdStatement() {
        return cmpdStatement;
    }

    /**
     * @param cmpdStatement the cmpdStatement to set
     */
    public void setCmpdStatement(CompoundStatement cmpdStatement) {
        this.cmpdStatement = cmpdStatement;
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

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }
    
    
    @Override
    public Function genLLCode() throws Exception{
        Function f = new Function(type, id.getId());
                    if(params.get(0) != null){
                        FuncParam funcParams = new FuncParam();
                        FuncParam nextParam = new FuncParam();
                        for(Parameter p : params){
                            if(funcParams.getName() == null){
                                funcParams.setname(p.getId().getId());
                                funcParams.setType(Data.TYPE_INT);
                                f.getTable().put(p.getId().getId(), f.getNewRegNum());
                                nextParam = funcParams;
                            } else {
                                FuncParam tempParam = new FuncParam(Data.TYPE_INT, p.getId().getId());
                                nextParam.setNextParam(tempParam);
                                f.getTable().put(p.getId().getId(), f.getNewRegNum());
                                nextParam = tempParam;
                            }
                        }

                        f.setFirstParam(funcParams);
                    }
                    f.createBlock0();
                    BasicBlock newBlock = new BasicBlock(f);
                    f.appendBlock(newBlock);
                    f.setCurrBlock(newBlock);
                    cmpdStatement.genLLCode(f);
                    f.appendBlock(f.getReturnBlock());
                    
                    return f;
        
    }
    
}
