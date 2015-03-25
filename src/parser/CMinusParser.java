package parser;

import java.util.ArrayList;
import scanner.CMinusScanner;
import scanner.Token;

/**
 *
 * @author Nate H
 * @author Jake P
 * 
 */
public class CMinusParser implements Parser {
    
    private CMinusScanner scanner;
    
    public CMinusParser(CMinusScanner scan){
        scanner = scan;
    }
    
    //Starts the parse. Again, this works recursively
    @Override
    public Program startParse() throws ParseException {
            Program p = null;
            try {
                p = parseProgram();
            } catch (ParseException e){
                System.out.println(e.getMessage());
                System.exit(0);
            }
            return p;
    }
    
    @Override
    public void match(Token.TokenType t) throws ParseException{
        if(scanner.viewNextToken().getType() == t){
            scanner.getNextToken();
        } else {
            if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + ", looking for " + t.toString());
            } else {
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ", looking for " + t.toString());
            }
            
        }
    }
    
    @Override
    public Program parseProgram() throws ParseException{
        ArrayList<Declaration> declList = new ArrayList<>();

        while(scanner.viewNextToken().getType() == Token.TokenType.VOID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.INT_TOKEN){
            declList.add(parseDeclaration());
        }

        Program p = new Program(declList);
        return p;
        
    }
    
    @Override
    public Declaration parseDeclaration() throws ParseException{
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
                    if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                        throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseDeclaration()");
                    } else {
                        throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseDeclaration()");
                    }
                }
                
            default:
                if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseDeclaration()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseDeclaration()");
                }
                
        }
    }
    
    @Override
    public VarDeclaration parseVarDeclaration(String s) throws ParseException{
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
                    match(Token.TokenType.SEMICOLON_TOKEN);
                    return new VarDeclaration(varDeclID);
                default:
                    if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                        throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseVarDeclaration()");
                    } else {
                        throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseVarDeclaration()");
                    }
                
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
                        match(Token.TokenType.SEMICOLON_TOKEN);
                        return new VarDeclaration(id);
                    } else {
                        if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                            throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseVarDeclaration()");
                        } else {
                            throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseVarDeclaration()");
                        }
                    }

            
        }
    }
    
    @Override
    public Identifier parseIdentifier() throws ParseException{
        if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN){
            return new Identifier(scanner.getNextToken().getData().toString());
        } else {
            if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseIdentifier()");
            } else {
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseIdentifier()");
            }
        }
    }
    
    @Override
    public Numeric parseNumeric() throws ParseException{
        if(scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
            return new Numeric(scanner.getNextToken().getData().toString());
        } else {
            if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseNumeric()");
            } else {
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseNumeric()");
            }
        }
    }
    
    @Override
    public FunctionDeclaration parseFunctionDeclaration(String s) throws ParseException {
        Identifier id;
	if (s == null){
            id = parseIdentifier();  
        } else {
            id = new Identifier(s);
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
        return new FunctionDeclaration(params, compoundStmt, id);
    }
    
    public Parameter parseParameter() throws ParseException{
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
                    if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                        throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseParameter()");
                    } else {
                        throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseParameter()");
                    }
                }
            case VOID_TOKEN:
                match(Token.TokenType.VOID_TOKEN);
                return null;
            default:
                if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseParameter()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseParameter()");
                }
                
            
        }
    }
    
    @Override
    public CompoundStatement parseCompoundStatement() throws ParseException {
        match(Token.TokenType.OPENCURLY_TOKEN);
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
        match(Token.TokenType.CLOSECURLY_TOKEN);
        return new CompoundStatement(localDecl, stmtList);
    }
    
    @Override
    public Statement parseStatement() throws ParseException{
        Token.TokenType currentToken = scanner.viewNextToken().getType();
        switch(currentToken){
            case OPENPAREN_TOKEN: case NUM_TOKEN: case ID_TOKEN: case SEMICOLON_TOKEN:
                return parseExpressionStatement();
            case OPENCURLY_TOKEN:
                return parseCompoundStatement();
            case IF_TOKEN:
                return parseIfStatement();
            case WHILE_TOKEN:
                return parseIterationStatement();
            case RETURN_TOKEN:
                return parseReturnStatement();
            default:
                if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseStatement()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseStatement()");
                }
        }
    }
    
    @Override
    public ExpressionStatement parseExpressionStatement() throws ParseException{
        ExpressionStatement exp = null;
        Token.TokenType currentToken = scanner.viewNextToken().getType();
        switch(currentToken){
            case SEMICOLON_TOKEN:
                return exp;
            case OPENPAREN_TOKEN: case NUM_TOKEN: case ID_TOKEN:
                exp = new ExpressionStatement(parseExpression());
                match(Token.TokenType.SEMICOLON_TOKEN);
                return exp;
            default:
                if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseExpressionStatement()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseExpressionStatement()");
                }
        }
        
    }
    
    @Override
   public Statement parseIfStatement() throws ParseException{
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
   
    @Override
   public IterationStatement parseIterationStatement() throws ParseException{
       match(Token.TokenType.WHILE_TOKEN);
       match(Token.TokenType.OPENPAREN_TOKEN);
       Expression exp = parseExpression();
       match(Token.TokenType.CLOSEPAREN_TOKEN);
       Statement stmt = parseStatement();
       
       return new IterationStatement(exp, stmt);
   }
   
    @Override
   public ReturnStatement parseReturnStatement() throws ParseException {
       match(Token.TokenType.RETURN_TOKEN);
       Expression expr = null;
       Token.TokenType currentToken = scanner.viewNextToken().getType();
       if(currentToken == Token.TokenType.OPENPAREN_TOKEN || currentToken == Token.TokenType.NUM_TOKEN || currentToken == Token.TokenType.ID_TOKEN) {
            expr = parseExpression();
       }
       match(Token.TokenType.SEMICOLON_TOKEN);
       return new ReturnStatement(expr);
   }
   
    @Override
   public Expression parseExpression() throws ParseException {
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
               Expression idExp;
               if(scanner.viewNextToken().getType() != Token.TokenType.SEMICOLON_TOKEN && scanner.viewNextToken().getType() != Token.TokenType.CLOSEBRACKET_TOKEN && scanner.viewNextToken().getType() != Token.TokenType.COMMA_TOKEN && scanner.viewNextToken().getType() != Token.TokenType.CLOSEPAREN_TOKEN){
                    idExp = parseExpressionPrime(id);
               } else {
                   idExp = id;
               }
               
               return idExp;
           default:
               if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseExpression()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseExpression()");
                }
       }
   }
   
    @Override
   public Expression parseExpressionPrime(Identifier id) throws ParseException{
       Token.TokenType currentToken = scanner.viewNextToken().getType();
       switch(currentToken){
           case ASSIGN_TOKEN:
               match(Token.TokenType.ASSIGN_TOKEN);
               Expression exp = parseExpression();
               return new AssignExpression(id, exp);
           case PLUS_TOKEN: 
           case MINUS_TOKEN: 
           case MULT_TOKEN: 
           case DIV_TOKEN:
           case LT_TOKEN:
           case GT_TOKEN:
           case LTEQ_TOKEN:
           case GTEQ_TOKEN:
           case EQ_TOKEN:
           case NOTEQ_TOKEN:
               BinaryExpression binExp = new BinaryExpression(id);
               binExp = parseBinaryExpression(binExp);
               return binExp;
           case OPENPAREN_TOKEN:
               match(Token.TokenType.OPENPAREN_TOKEN);
               CallExpression callExp = parseCallExpression(id);
               match(Token.TokenType.CLOSEPAREN_TOKEN);
               if(scanner.viewNextToken().getType() == Token.TokenType.SEMICOLON_TOKEN){
                   return callExp;
               } else {
                   BinaryExpression bExp = new BinaryExpression(callExp);
                   bExp = parseBinaryExpression(bExp);
                   return bExp;
               }
               
           case OPENBRACKET_TOKEN:
               match(Token.TokenType.OPENBRACKET_TOKEN);
               Expression arrExp = parseExpression();
               match(Token.TokenType.CLOSEBRACKET_TOKEN);
               id.setArrayData(arrExp);
               Expression retExp = parseExpressionDoublePrime(id);
               return retExp;
           default:
               if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseExpressionPrime()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseExpressionPrime()");
                }
               
               
       }
   }
   
    @Override
   public Expression parseExpressionDoublePrime(Identifier id) throws ParseException{
       Token.TokenType currentToken = scanner.viewNextToken().getType();
       switch(currentToken){
           case ASSIGN_TOKEN:
               match(Token.TokenType.ASSIGN_TOKEN);
               Expression exp = parseExpression();
               return new AssignExpression(id, exp);
           case PLUS_TOKEN: 
           case MINUS_TOKEN: 
           case MULT_TOKEN: 
           case DIV_TOKEN:
           case LT_TOKEN:
           case GT_TOKEN:
           case LTEQ_TOKEN:
           case GTEQ_TOKEN:
           case EQ_TOKEN:
           case NOTEQ_TOKEN:
               BinaryExpression binExp = new BinaryExpression(id);
               binExp = parseBinaryExpression(binExp);
               return binExp;
           case CLOSEPAREN_TOKEN:
           case SEMICOLON_TOKEN:
               return id;
           default:
               if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseExpressionDoublePrime()");
               } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseExpressionDoublePrime()");
               }
       }
           
   }
   
    @Override
   public BinaryExpression parseBinaryExpression(BinaryExpression id) throws ParseException{
       Token.TokenType currentToken = scanner.viewNextToken().getType();
       BinaryExpression recBinExp = id;
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
                Token op = scanner.getNextToken();
                currentToken = scanner.viewNextToken().getType();
                switch(currentToken){
                    case OPENPAREN_TOKEN:
                        match(Token.TokenType.OPENPAREN_TOKEN);
                        Expression multExp = parseExpression();
                        match(Token.TokenType.CLOSEPAREN_TOKEN);
                        if(recBinExp.getOperator() == null){
                                Expression tempId = null;
                                if(recBinExp.getLhs() instanceof Numeric){
                                    tempId = (Numeric)recBinExp.getLhs();
                                } else {
                                    tempId = (Identifier)recBinExp.getLhs();
                                }
                                recBinExp.setLhs(tempId);
                                recBinExp.setOperator(op);
                                recBinExp.setRhs(multExp);
                        } else {
                            BinaryExpression tempBinExp = new BinaryExpression(recBinExp);
                            recBinExp.setLhs(tempBinExp);
                            recBinExp.setOperator(op);
                            recBinExp.setRhs(multExp);
                        }
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
                           if(recBinExp.getOperator() == null){
                                Expression tempId = null;
                                if(recBinExp.getLhs() instanceof Numeric){
                                    tempId = (Numeric)recBinExp.getLhs();
                                } else {
                                    tempId = (Identifier)recBinExp.getLhs();
                                }
                                recBinExp.setLhs(tempId);
                                recBinExp.setOperator(op);
                                recBinExp.setRhs(multId);
                            } else {
                                BinaryExpression tempBinExp = new BinaryExpression(recBinExp);
                                recBinExp.setLhs(tempBinExp);
                                recBinExp.setOperator(op);
                                recBinExp.setRhs(multId);
                            }
                           return parseBinaryExpression(recBinExp);
                        } else if (scanner.viewNextToken().getType() == Token.TokenType.OPENBRACKET_TOKEN){
                            match(Token.TokenType.OPENBRACKET_TOKEN);
                            Expression bracketExpression = parseExpression();
                            match(Token.TokenType.CLOSEBRACKET_TOKEN);
                            multId.setArrayData(bracketExpression);
                            if(recBinExp.getOperator() == null){
                                Expression tempId = null;
                                if(recBinExp.getLhs() instanceof Numeric){
                                    tempId = (Numeric)recBinExp.getLhs();
                                } else {
                                    tempId = (Identifier)recBinExp.getLhs();
                                }
                                recBinExp.setLhs(tempId);
                                recBinExp.setOperator(op);
                                recBinExp.setRhs(multId);
                            } else {
                                BinaryExpression tempBinExp = new BinaryExpression(recBinExp);
                                recBinExp.setLhs(tempBinExp);
                                recBinExp.setOperator(op);
                                recBinExp.setRhs(multId);
                            }
                            return parseBinaryExpression(recBinExp);
                        } else if(scanner.viewNextToken().getType() == Token.TokenType.CLOSEPAREN_TOKEN || 
                                  scanner.viewNextToken().getType() == Token.TokenType.SEMICOLON_TOKEN ||
                                  scanner.viewNextToken().getType() == Token.TokenType.MULT_TOKEN || 
                                  scanner.viewNextToken().getType() == Token.TokenType.DIV_TOKEN || 
                                  scanner.viewNextToken().getType() == Token.TokenType.PLUS_TOKEN || 
                                  scanner.viewNextToken().getType() == Token.TokenType.MINUS_TOKEN || 
                                  scanner.viewNextToken().getType() == Token.TokenType.LT_TOKEN ||
                                  scanner.viewNextToken().getType() == Token.TokenType.GT_TOKEN ||
                                  scanner.viewNextToken().getType() == Token.TokenType.LTEQ_TOKEN ||
                                  scanner.viewNextToken().getType() == Token.TokenType.GTEQ_TOKEN ||
                                  scanner.viewNextToken().getType() == Token.TokenType.EQ_TOKEN ||
                                  scanner.viewNextToken().getType() == Token.TokenType.NOTEQ_TOKEN){
                            if(recBinExp.getOperator() == null){
                                Expression tempId = null;
                                if(recBinExp.getLhs() instanceof Numeric){
                                    tempId = (Numeric)recBinExp.getLhs();
                                } else {
                                    tempId = (Identifier)recBinExp.getLhs();
                                }
                                recBinExp.setLhs(tempId);
                                recBinExp.setOperator(op);
                                recBinExp.setRhs(multId);
                            } else {
                                BinaryExpression tempBinExp = new BinaryExpression(recBinExp);
                                recBinExp.setLhs(tempBinExp);
                                recBinExp.setOperator(op);
                                recBinExp.setRhs(multId);
                            }
                            
                            return parseBinaryExpression(recBinExp);
                        }
                        else {
                            if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseBinaryExpression()");
                            } else {
                                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseBinaryExpression()");
                            }
                        }

                    case NUM_TOKEN:
                        Numeric factorNum = parseNumeric();
                        if(recBinExp.getOperator() == null){
                            Expression tempId = null;
                            if(recBinExp.getLhs() instanceof Numeric){
                                tempId = (Numeric)recBinExp.getLhs();
                            } else {
                                tempId = (Identifier)recBinExp.getLhs();
                            }
                            recBinExp.setLhs(tempId);
                            recBinExp.setOperator(op);
                            recBinExp.setRhs(factorNum);
                        } else {
                            BinaryExpression tempBinExp = new BinaryExpression(recBinExp);
                            recBinExp.setLhs(tempBinExp);
                            recBinExp.setOperator(op);
                            recBinExp.setRhs(factorNum);
                        }
                        return parseBinaryExpression(recBinExp);

                    default:
                        if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                            throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseBinaryExpression()");
                        } else {
                            throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseBinaryExpression()");
                        }




                }

       } else {
            return recBinExp;
       }
       
       
   }
    
   public CallExpression parseCallExpression(Identifier id) throws ParseException{
        ArrayList<Expression> args = new ArrayList<>();
        if(scanner.viewNextToken().getType() == Token.TokenType.OPENPAREN_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN){
            args.add(parseExpression());
            while(scanner.viewNextToken().getType() == Token.TokenType.COMMA_TOKEN){
                match(Token.TokenType.COMMA_TOKEN);
                args.add(parseExpression());
            }
        } else if (scanner.viewNextToken().getType() != Token.TokenType.CLOSEPAREN_TOKEN){
            if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                 throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + ": " + scanner.viewNextToken().getData().toString() + " in parseExpressionPrime()");
             } else {
                 throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + " in parseExpressionPrime()");
             }
        }
        
        return new CallExpression(id, args);
   }
   
   
   
    
}
