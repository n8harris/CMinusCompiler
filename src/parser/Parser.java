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

    
    
    public Program startParse() throws ParseException;
    
    public void match(Token.TokenType t) throws ParseException;
    
    public Program parseProgram() throws ParseException;
    
    public Declaration parseDeclaration() throws ParseException;
    
    public VarDeclaration parseVarDeclaration(String s) throws ParseException;
    
    public Identifier parseIdentifier() throws ParseException;
    
    public Numeric parseNumeric() throws ParseException;
    
    public FunctionDeclaration parseFunctionDeclaration(String s) throws ParseException;
    
    public Parameter parseParameter() throws ParseException;
    
    public CompoundStatement parseCompoundStatement() throws ParseException;
    
    public Statement parseStatement() throws ParseException;
    
    public ExpressionStatement parseExpressionStatement() throws ParseException;
    
   public Statement parseIfStatement() throws ParseException; 
   
   public IterationStatement parseIterationStatement() throws ParseException;
   
   public ReturnStatement parseReturnStatement() throws ParseException;
   
   public Expression parseExpression() throws ParseException;
   
   public Expression parseExpressionPrime(Identifier id) throws ParseException;
   
   public Expression parseExpressionDoublePrime(Identifier id) throws ParseException;
   
   public Expression parseSimpleExpression(Expression exp) throws ParseException;
   
   public Expression parseAdditiveExpression(Expression exp) throws ParseException;
   
   public Expression parseTerm(Expression exp) throws ParseException;
   
   public Expression parseFactor() throws ParseException;
   
   public Expression parseCallExpression(Identifier id) throws ParseException;
   
   
   
    
}


