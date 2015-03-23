/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;
import scanner.CMinusScanner;
import scanner.Token;

/**
 *
 * @author Nate H
 */
public class CMinusParser {
    
    private CMinusScanner scanner;
    
    public CMinusParser(CMinusScanner scan){
        scanner = scan;
    }
    
    public Program startParse() throws Exception{
            
            return parseProgram();
    }
    
    public void match(Token.TokenType t) throws Exception{
        if(scanner.viewNextToken().getType() == t){
            scanner.getNextToken();
        } else {
            throw new Exception("Error");
        }
    }
    
    public Program parseProgram() throws Exception{
        ArrayList<Declaration> declList = new ArrayList<>();
        while(scanner.viewNextToken().getType() == Token.TokenType.VOID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.INT_TOKEN){
            declList.add(parseDeclaration());
        }
        
        Program p = new Program(declList);
        return p;
        
    }
    
    public Declaration parseDeclaration() throws Exception{
        switch(scanner.viewNextToken().getType()) {
            case VOID_TOKEN:
                match(Token.TokenType.VOID_TOKEN);
                Declaration funDecl = parseFunctionDeclaration(null);
                return funDecl;
            case INT_TOKEN:
                match(Token.TokenType.INT_TOKEN);
                Declaration varDecl = parseDeclaration();
                return varDecl;
            case ID_TOKEN:
                Token t = scanner.getNextToken();
                if(scanner.viewNextToken().getType() == Token.TokenType.OPENBRACKET_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.SEMICOLON_TOKEN){
                    VarDeclaration vDecl = parseVarDeclaration(t.getData().toString());
                    return vDecl;
                } else if(scanner.viewNextToken().getType() == Token.TokenType.OPENPAREN_TOKEN){
                    FunctionDeclaration fDecl = parseFunctionDeclaration(t.getData().toString());
                    return fDecl;
                } else {
                    throw new Exception("Error");
                }
                
            default:
                throw new Exception("Error");
                
        }
    }
    
    public VarDeclaration parseVarDeclaration(String s) throws Exception{
        if(s != null){
            Identifier varDeclID = new Identifier(s);
            switch(scanner.viewNextToken().getType()) {
                case OPENBRACKET_TOKEN:
                    match(Token.TokenType.OPENBRACKET_TOKEN);
                    Numeric idNum = parseNumeric();
                    match(Token.TokenType.CLOSEBRACKET_TOKEN);
                    match(Token.TokenType.SEMICOLON_TOKEN);
                    return new VarDeclaration(idNum, varDeclID);
                case SEMICOLON_TOKEN:
                    return new VarDeclaration(varDeclID);
                default:
                    throw new Exception("Error");
                
            }
        } else {
                    match(Token.TokenType.INT_TOKEN);
                    Identifier id = parseIdentifier();
                    if(scanner.viewNextToken().getType() == Token.TokenType.OPENBRACKET_TOKEN){
                        match(Token.TokenType.OPENBRACKET_TOKEN);
                        Numeric idNum = parseNumeric();
                        match(Token.TokenType.CLOSEBRACKET_TOKEN);
                        match(Token.TokenType.SEMICOLON_TOKEN);
                        return new VarDeclaration(idNum, id);  
                    } else if (scanner.viewNextToken().getType() == Token.TokenType.SEMICOLON_TOKEN) {
                        return new VarDeclaration(id);
                    } else {
                        throw new Exception("Error");
                    }

            
        }
    }
    
    public Identifier parseIdentifier() throws Exception{
        if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN){
            return new Identifier(scanner.getNextToken().getData().toString());
        } else {
            throw new Exception("Error");
        }
    }
    
    public Numeric parseNumeric() throws Exception{
        if(scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
            return new Numeric((double)scanner.getNextToken().getData());
        } else {
            throw new Exception("Error");
        }
    }
    
    public FunctionDeclaration parseFunctionDeclaration(String s) throws Exception {
	if (s == null){
            Identifier id = parseIdentifier();  
        }
        match(Token.TokenType.OPENPAREN_TOKEN);
        ArrayList<Parameter> params = new ArrayList<>();
        params.add(parseParameter());
        while(scanner.viewNextToken().getType() == Token.TokenType.COMMA_TOKEN) {
            match(Token.TokenType.COMMA_TOKEN);
            params.add(parseParameter());
        }
        match(Token.TokenType.CLOSEPAREN_TOKEN);
        CompoundStatement compoundStmt = parseCompoundStatement();
        return new FunctionDeclaration(params, compoundStmt, new Identifier(s));
    }
    
    public Parameter parseParameter() throws Exception{
        Token.TokenType currentToken = scanner.viewNextToken().getType();
        switch(currentToken){
            case INT_TOKEN:
                match(Token.TokenType.INT_TOKEN);
                Identifier id = parseIdentifier();
                if(scanner.viewNextToken().getType() == Token.TokenType.OPENBRACKET_TOKEN){
                    match(Token.TokenType.OPENBRACKET_TOKEN);
                    match(Token.TokenType.CLOSEBRACKET_TOKEN);
                    return new Parameter(id);
                } else if (scanner.viewNextToken().getType() == Token.TokenType.COMMA_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.CLOSEPAREN_TOKEN){
                    return new Parameter(id);
                } else {
                    throw new Exception("Error");
                }
            
            default:
                throw new Exception("Error");
                
            
        }
    }
    
    public CompoundStatement parseCompoundStatement() throws Exception {
        match(Token.TokenType.OPENBRACKET_TOKEN);
        ArrayList<VarDeclaration> localDecl = new ArrayList<>();
        ArrayList<Statement> stmtList = new ArrayList<>();
        while(scanner.viewNextToken().getType() == Token.TokenType.INT_TOKEN){
            localDecl.add(parseVarDeclaration(null));
        }
        
        while(scanner.viewNextToken().getType() == Token.TokenType.OPENPAREN_TOKEN || 
              scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN || 
              scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN ||  
              scanner.viewNextToken().getType() == Token.TokenType.SEMICOLON_TOKEN || 
              scanner.viewNextToken().getType() == Token.TokenType.OPENCURLY_TOKEN || 
              scanner.viewNextToken().getType() == Token.TokenType.IF_TOKEN || 
              scanner.viewNextToken().getType() == Token.TokenType.WHILE_TOKEN || 
              scanner.viewNextToken().getType() == Token.TokenType.RETURN_TOKEN ){
            
            stmtList.add(parseStatement());
        }
        match(Token.TokenType.CLOSEBRACKET_TOKEN);
        return new CompoundStatement(localDecl, stmtList);
    }
    
    public Statement parseStatement() throws Exception{
        Token.TokenType currentToken = scanner.viewNextToken().getType();
        switch(currentToken){
            case OPENPAREN_TOKEN: case NUM_TOKEN: case ID_TOKEN: case SEMICOLON_TOKEN:
                return parseExpressionStatement();
            case OPENBRACKET_TOKEN:
                return parseCompoundStatement();
            case IF_TOKEN:
                return parseIfStatement();
            case WHILE_TOKEN:
                return parseIterationStatement();
            case RETURN_TOKEN:
                return parseReturnStatement();
            default:
                throw new Exception("Error");
        }
    }
    
    public ExpressionStatement parseExpressionStatement() throws Exception{
        ExpressionStatement exp = null;
        Token.TokenType currentToken = scanner.viewNextToken().getType();
        switch(currentToken){
            case SEMICOLON_TOKEN:
                return exp;
            case OPENPAREN_TOKEN: case NUM_TOKEN: case ID_TOKEN:
                exp = new ExpressionStatement(parseExpression());
                return exp;
            default:
                throw new Exception("Error");
        }
        
    }
    
   public Statement parseIfStatement() throws Exception{
       match(Token.TokenType.IF_TOKEN);
       match(Token.TokenType.OPENPAREN_TOKEN);
       Expression ifExpr = parseExpression();
       match(Token.TokenType.CLOSEPAREN_TOKEN);
       Statement thenStmt = parseStatement();
       Statement elseStmt = null;
       
       if(scanner.viewNextToken().getType() == Token.TokenType.ELSE_TOKEN) {
           match(Token.TokenType.ELSE_TOKEN);
           elseStmt = parseStatement();
       }
       
       Statement returnStmt = new IfStatement(ifExpr, thenStmt, elseStmt);
       return returnStmt;
       
   } 
   
   public IterationStatement parseIterationStatement() throws Exception{
       match(Token.TokenType.WHILE_TOKEN);
       match(Token.TokenType.OPENPAREN_TOKEN);
       Expression exp = parseExpression();
       match(Token.TokenType.CLOSEPAREN_TOKEN);
       Statement stmt = parseStatement();
       
       return new IterationStatement(exp, stmt);
   }
   
   public ReturnStatement parseReturnStatement() throws Exception {
       match(Token.TokenType.RETURN_TOKEN);
       Expression expr = null;
       Token.TokenType currentToken = scanner.viewNextToken().getType();
       if(currentToken == Token.TokenType.OPENPAREN_TOKEN || currentToken == Token.TokenType.NUM_TOKEN || currentToken == Token.TokenType.ID_TOKEN || currentToken == Token.TokenType.SEMICOLON_TOKEN) {
            expr = parseExpression();
       }
       match(Token.TokenType.SEMICOLON_TOKEN);
       return new ReturnStatement(expr);
   }
   
   public Expression parseExpression() throws Exception {
       Token.TokenType currentToken = scanner.viewNextToken().getType();
       switch(currentToken){
           case OPENPAREN_TOKEN:
               match(Token.TokenType.OPENPAREN_TOKEN);
               Expression exp = parseExpression();
               match(Token.TokenType.CLOSEPAREN_TOKEN);
               BinaryExpression binExp = new BinaryExpression(exp);
               binExp = parseBinaryExpression(binExp);
               return binExp;
           case NUM_TOKEN:
               Numeric num = parseNumeric();
               BinaryExpression bin = new BinaryExpression(num);
               bin = parseBinaryExpression(bin);
               return bin;
           case ID_TOKEN:
               Identifier id = parseIdentifier();
               Expression idExp = parseExpressionPrime(id);
               return idExp;
           default:
               throw new Exception("Error");
       }
   }
   
   public Expression parseExpressionPrime(Identifier id) throws Exception{
       Token.TokenType currentToken = scanner.viewNextToken().getType();
       switch(currentToken){
           case EQ_TOKEN:
               Expression exp = parseExpression();
               return new AssignExpression(id, exp);
           case PLUS_TOKEN: case MINUS_TOKEN: case MULT_TOKEN: case DIV_TOKEN:
               BinaryExpression binExp = new BinaryExpression(id);
               binExp = parseBinaryExpression(binExp);
               return binExp;
           case OPENPAREN_TOKEN:
               match(Token.TokenType.OPENPAREN_TOKEN);
               ArrayList<Expression> args = new ArrayList<>();
               if(scanner.viewNextToken().getType() == Token.TokenType.OPENPAREN_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN){
                   args.add(parseExpression());
                   while(scanner.viewNextToken().getType() == Token.TokenType.COMMA_TOKEN){
                       match(Token.TokenType.COMMA_TOKEN);
                       args.add(parseExpression());
                   }
               } else if (scanner.viewNextToken().getType() != Token.TokenType.CLOSEPAREN_TOKEN){
                   throw new Exception("Error");
               }
               
               match(Token.TokenType.CLOSEPAREN_TOKEN);
               id.setArgs(args);
               BinaryExpression bExp = new BinaryExpression(id);
               bExp = parseBinaryExpression(bExp);
               return bExp;
           case OPENBRACKET_TOKEN:
               match(Token.TokenType.OPENBRACKET_TOKEN);
               Expression arrExp = parseExpression();
               match(Token.TokenType.CLOSEBRACKET_TOKEN);
               id.setArrayData(arrExp);
               Expression retExp = parseExpressionDoublePrime(id);
               return retExp;
           default:
               throw new Exception("Error");
               
               
       }
   }
   
   public Expression parseExpressionDoublePrime(Identifier id) throws Exception{
       Token.TokenType currentToken = scanner.viewNextToken().getType();
       switch(currentToken){
           case ASSIGN_TOKEN:
               Expression exp = parseExpression();
               return new AssignExpression(id, exp);
           case PLUS_TOKEN: case MINUS_TOKEN: case MULT_TOKEN: case DIV_TOKEN:
               BinaryExpression binExp = new BinaryExpression(id);
               binExp = parseBinaryExpression(binExp);
               return binExp;
           default:
               throw new Exception("Error");
       }
           
   }
   
   public BinaryExpression parseBinaryExpression(Expression id) throws Exception{
       Token.TokenType currentToken = scanner.viewNextToken().getType();
       BinaryExpression recBinExp = new BinaryExpression(id);
       if(currentToken == Token.TokenType.MULT_TOKEN || 
          currentToken == Token.TokenType.DIV_TOKEN || 
          currentToken == Token.TokenType.PLUS_TOKEN || 
          currentToken == Token.TokenType.MINUS_TOKEN || 
          currentToken == Token.TokenType.LT_TOKEN ||
          currentToken == Token.TokenType.GT_TOKEN ||
          currentToken == Token.TokenType.LTEQ_TOKEN ||
          currentToken == Token.TokenType.GTEQ_TOKEN ||
          currentToken == Token.TokenType.EQ_TOKEN ||
          currentToken == Token.TokenType.NOTEQ_TOKEN){
                String op = scanner.getNextToken().getData().toString();
                currentToken = scanner.viewNextToken().getType();
                switch(currentToken){
                    case OPENPAREN_TOKEN:
                        match(Token.TokenType.OPENPAREN_TOKEN);
                        Expression multExp = parseExpression();
                        match(Token.TokenType.CLOSEPAREN_TOKEN);
                        recBinExp.setLhs(recBinExp);
                        recBinExp.setOperator(op);
                        recBinExp.setRhs(multExp);
                        return parseBinaryExpression(recBinExp);

                    case ID_TOKEN:
                        Identifier multId = parseIdentifier();
                        if(scanner.viewNextToken().getType() == Token.TokenType.OPENPAREN_TOKEN){
                           match(Token.TokenType.OPENPAREN_TOKEN);
                           Expression idMultExp = parseExpression();
                           ArrayList<Expression> multArgs = new ArrayList<>();
                           multArgs.add(idMultExp);
                           while(scanner.viewNextToken().getType() == Token.TokenType.COMMA_TOKEN){
                               match(Token.TokenType.COMMA_TOKEN);
                               multExp = parseExpression();
                               multArgs.add(multExp);
                           }
                           multId.setArgs(multArgs);
                           match(Token.TokenType.CLOSEPAREN_TOKEN);
                           recBinExp.setLhs(recBinExp);
                           recBinExp.setOperator(op);
                           recBinExp.setRhs(multId);
                           return parseBinaryExpression(recBinExp);
                        } else if (scanner.viewNextToken().getType() == Token.TokenType.OPENBRACKET_TOKEN){
                            match(Token.TokenType.OPENBRACKET_TOKEN);
                            Expression bracketExpression = parseExpression();
                            match(Token.TokenType.CLOSEBRACKET_TOKEN);
                            multId.setArrayData(bracketExpression);
                            recBinExp.setLhs(recBinExp);
                            recBinExp.setOperator(op);
                            recBinExp.setRhs(multId);
                            return parseBinaryExpression(recBinExp);
                        } else {
                            throw new Exception("Error");
                        }

                    case NUM_TOKEN:
                        Numeric factorNum = parseNumeric();
                        recBinExp.setLhs(recBinExp);
                        recBinExp.setOperator(op);
                        recBinExp.setRhs(factorNum);
                        return parseBinaryExpression(recBinExp);

                    default:
                        throw new Exception("Error");




                }

       } else {
            return recBinExp;
       }
       
       
   }
   
   
   
    
}
