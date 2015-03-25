package parser;

import java.util.ArrayList;
import scanner.CMinusScanner;
import scanner.Token;

/**
 *
 * @author Nate H
 */

//The interface that is implemented by CMinusParser.java
public interface Parser {

    
    
    public Program startParse() throws Exception;
    
    public void match(Token.TokenType t) throws Exception;
    
    public Program parseProgram() throws Exception;
    
    public Declaration parseDeclaration() throws Exception;
    
    public VarDeclaration parseVarDeclaration(String s) throws Exception;
    
    public Identifier parseIdentifier() throws Exception;
    
    public Numeric parseNumeric() throws Exception;
    
    public FunctionDeclaration parseFunctionDeclaration(String s) throws Exception;
    
    public Parameter parseParameter() throws Exception;
    
    public CompoundStatement parseCompoundStatement() throws Exception;
    
    public Statement parseStatement() throws Exception;
    
    public ExpressionStatement parseExpressionStatement() throws Exception;
    
   public Statement parseIfStatement() throws Exception; 
   
   public IterationStatement parseIterationStatement() throws Exception;
   
   public ReturnStatement parseReturnStatement() throws Exception;
   
   public Expression parseExpression() throws Exception;
   
   public Expression parseExpressionPrime(Identifier id) throws Exception;
   
   public Expression parseExpressionDoublePrime(Identifier id) throws Exception;
   
   public BinaryExpression parseBinaryExpression(BinaryExpression id) throws Exception;
   
    
}


