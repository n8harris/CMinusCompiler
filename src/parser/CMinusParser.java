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
    
    //Constructor for CMinusScanner
    public CMinusParser(CMinusScanner scan){
        scanner = scan;
    }
    
    //Starts the parse
    @Override
    public Program startParse() throws ParseException {
            Program p = null;
            try {
                p = parseProgram();
            } catch (ParseException e){
                //Prints out the error message and stops the parse if one occurs
                System.out.println(e.getMessage());
                System.exit(0);
            }
            return p;
    }
    
    @Override
    public void match(Token.TokenType t) throws ParseException{
        //Matches and munches the token parameter. If it doesn't match,
        //an error is thrown.
        if(scanner.viewNextToken().getType() == t){
            scanner.getNextToken();
        } else {
            //Print out data if token is NUM or ID
            if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
               scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                         ": " + scanner.viewNextToken().getData().toString() + ", looking for " + 
                                         t.toString());
            } else {
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                         ", looking for " + t.toString());
            }
            
        }
    }
    
    @Override
    public Program parseProgram() throws ParseException{
        ArrayList<Declaration> declList = new ArrayList<>();
        
        //Iterate through all of the declarations whether they be var-decls
        //or fun-decls
        while(scanner.viewNextToken().getType() == Token.TokenType.VOID_TOKEN || 
              scanner.viewNextToken().getType() == Token.TokenType.INT_TOKEN){
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
                //Check first sets
                if(scanner.viewNextToken().getType() == Token.TokenType.OPENBRACKET_TOKEN || 
                   scanner.viewNextToken().getType() == Token.TokenType.SEMICOLON_TOKEN){
                    VarDeclaration vDecl = parseVarDeclaration(t.getData().toString());
                    return vDecl;
                } else if(scanner.viewNextToken().getType() == Token.TokenType.OPENPAREN_TOKEN){
                    FunctionDeclaration fDecl = parseFunctionDeclaration(t.getData().toString());
                    return fDecl;
                } else {
                    if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                       scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                        throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                                 ": " + scanner.viewNextToken().getData().toString() + " in parseDeclaration()");
                    } else {
                        throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                                 " in parseDeclaration()");
                    }
                }
                
            default:
                if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                   scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             ": " + scanner.viewNextToken().getData().toString() + " in parseDeclaration()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             " in parseDeclaration()");
                }
                
        }
    }
    
    @Override
    public VarDeclaration parseVarDeclaration(String s) throws ParseException{
        //Checks if there is no Identifier already associated
        if(s != null){
            Identifier varDeclID = new Identifier(s);
            switch(scanner.viewNextToken().getType()) {
                case OPENBRACKET_TOKEN:
                    match(Token.TokenType.OPENBRACKET_TOKEN);
                    Numeric idNum = parseNumeric(); //Get array data
                    match(Token.TokenType.CLOSEBRACKET_TOKEN);
                    match(Token.TokenType.SEMICOLON_TOKEN);
                    return new VarDeclaration(idNum, varDeclID);
                case SEMICOLON_TOKEN:
                    match(Token.TokenType.SEMICOLON_TOKEN);
                    return new VarDeclaration(varDeclID);
                default:
                    if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                       scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                        throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                                 ": " + scanner.viewNextToken().getData().toString() + " in parseVarDeclaration()");
                    } else {
                        throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                                 " in parseVarDeclaration()");
                    }
                
            }
        } else {
                    match(Token.TokenType.INT_TOKEN);
                    Identifier id = parseIdentifier();
                    if(scanner.viewNextToken().getType() == Token.TokenType.OPENBRACKET_TOKEN){
                        match(Token.TokenType.OPENBRACKET_TOKEN);
                        Numeric idNum = parseNumeric(); //Get array data
                        match(Token.TokenType.CLOSEBRACKET_TOKEN);
                        match(Token.TokenType.SEMICOLON_TOKEN);
                        return new VarDeclaration(idNum, id);  
                    } else if (scanner.viewNextToken().getType() == Token.TokenType.SEMICOLON_TOKEN) {
                        match(Token.TokenType.SEMICOLON_TOKEN);
                        return new VarDeclaration(id);
                    } else {
                        if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                           scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                            throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                                     ": " + scanner.viewNextToken().getData().toString() + " in parseVarDeclaration()");
                        } else {
                            throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                                     " in parseVarDeclaration()");
                        }
                    }

            
        }
    }
    
    @Override
    public Identifier parseIdentifier() throws ParseException{
        if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN){
            return new Identifier(scanner.getNextToken().getData().toString());
        } else {
            if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
               scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                         ": " + scanner.viewNextToken().getData().toString() + " in parseIdentifier()");
            } else {
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                         " in parseIdentifier()");
            }
        }
    }
    
    @Override
    public Numeric parseNumeric() throws ParseException{
        if(scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
            return new Numeric(scanner.getNextToken().getData().toString());
        } else {
            if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
               scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                         ": " + scanner.viewNextToken().getData().toString() + " in parseNumeric()");
            } else {
                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                         " in parseNumeric()");
            }
        }
    }
    
    @Override
    public FunctionDeclaration parseFunctionDeclaration(String s) throws ParseException {
        Identifier id;
        //Checks if there is not an Identifier already associated with this fun-decl
	if (s == null){
            id = parseIdentifier();  
        } else {
            id = new Identifier(s);
        }
        match(Token.TokenType.OPENPAREN_TOKEN);
        ArrayList<Parameter> params = new ArrayList<>(); //Get ready for params
        params.add(parseParameter()); //Add first param to list in case there is only one
        while(scanner.viewNextToken().getType() == Token.TokenType.COMMA_TOKEN) {
            match(Token.TokenType.COMMA_TOKEN);
            params.add(parseParameter());
        }
        match(Token.TokenType.CLOSEPAREN_TOKEN);
        CompoundStatement compoundStmt = parseCompoundStatement();
        return new FunctionDeclaration(params, compoundStmt, id);
    }
    
    public Parameter parseParameter() throws ParseException{
        switch(scanner.viewNextToken().getType()){
            case INT_TOKEN:
                match(Token.TokenType.INT_TOKEN);
                Identifier id = parseIdentifier();
                //Case for array param
                if(scanner.viewNextToken().getType() == Token.TokenType.OPENBRACKET_TOKEN){
                    match(Token.TokenType.OPENBRACKET_TOKEN);
                    match(Token.TokenType.CLOSEBRACKET_TOKEN);
                    return new Parameter(id);
                } else if (scanner.viewNextToken().getType() == Token.TokenType.COMMA_TOKEN ||
                           scanner.viewNextToken().getType() == Token.TokenType.CLOSEPAREN_TOKEN){
                    return new Parameter(id);
                } else {
                    if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                       scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                        throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                                 ": " + scanner.viewNextToken().getData().toString() + " in parseParameter()");
                    } else {
                        throw new ParseException("Error: Unexpected Token " + 
                                                 scanner.viewNextToken().getType().toString() + " in parseParameter()");
                    }
                }
            case VOID_TOKEN:
                //Case for no params (cannot have empty param list, but VOID token is valid
                match(Token.TokenType.VOID_TOKEN);
                return null;
            default:
                if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                   scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             ": " + scanner.viewNextToken().getData().toString() + " in parseParameter()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() +
                                             " in parseParameter()");
                }
                
            
        }
    }
    
    @Override
    public CompoundStatement parseCompoundStatement() throws ParseException {
        match(Token.TokenType.OPENCURLY_TOKEN);
        ArrayList<VarDeclaration> localDecl = new ArrayList<>();
        ArrayList<Statement> stmtList = new ArrayList<>();
        //Iterate through local decls (they will all start with an int)
        while(scanner.viewNextToken().getType() == Token.TokenType.INT_TOKEN){
            localDecl.add(parseVarDeclaration(null));
        }
        
        //Iterate through statement list
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
        switch(scanner.viewNextToken().getType()){
            //First set of expression statement
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
                if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                   scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() +
                                             ": " + scanner.viewNextToken().getData().toString() + " in parseStatement()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             " in parseStatement()");
                }
        }
    }
    
    @Override
    public ExpressionStatement parseExpressionStatement() throws ParseException{
        ExpressionStatement exp = null;
        switch(scanner.viewNextToken().getType()){
            case SEMICOLON_TOKEN:
                return exp;
            //First set of expression
            case OPENPAREN_TOKEN: case NUM_TOKEN: case ID_TOKEN:
                exp = new ExpressionStatement(parseExpression());
                match(Token.TokenType.SEMICOLON_TOKEN);
                return exp;
            default:
                if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                   scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             ": " + scanner.viewNextToken().getData().toString() + " in parseExpressionStatement()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             " in parseExpressionStatement()");
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
       
       //Only parse else statement if else token exists
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
       //First set of expression
       if(scanner.viewNextToken().getType() == Token.TokenType.OPENPAREN_TOKEN || 
          scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN || 
          scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN) {
            expr = parseExpression();
       }
       match(Token.TokenType.SEMICOLON_TOKEN);
       return new ReturnStatement(expr);
   }
   
    @Override
   public Expression parseExpression() throws ParseException {
       switch(scanner.viewNextToken().getType()){
           case OPENPAREN_TOKEN:
               match(Token.TokenType.OPENPAREN_TOKEN);
               Expression exp = parseExpression();
               match(Token.TokenType.CLOSEPAREN_TOKEN);
               //We go to recursive function which takes care of all of 
               //simple-expression'
               
               return parseSimpleExpression(exp);
           case NUM_TOKEN:
               Numeric num = parseNumeric();
            
               return parseSimpleExpression(num);
           case ID_TOKEN:
               Identifier id = parseIdentifier();
               return parseExpressionPrime(id);
               
           default:
               if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                  scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             ": " + scanner.viewNextToken().getData().toString() + " in parseExpression()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             " in parseExpression()");
                }
       }
   }
   
    @Override
   public Expression parseExpressionPrime(Identifier id) throws ParseException{
       switch(scanner.viewNextToken().getType()){
           //First case in expression'. If assign token, create an assign expression
           case ASSIGN_TOKEN:
               match(Token.TokenType.ASSIGN_TOKEN);
               Expression exp = parseExpression();
               return new AssignExpression(id, exp);
           //First set of simple-expression', so we go to recursive fuction for binary expression
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
               return parseSimpleExpression(id);
           //Case for function call
           case OPENPAREN_TOKEN:
               match(Token.TokenType.OPENPAREN_TOKEN);
               CallExpression callExp = parseCallExpression(id);
               match(Token.TokenType.CLOSEPAREN_TOKEN);
               return parseSimpleExpression(callExp);
           //Case for expression within array brackets 
           case OPENBRACKET_TOKEN:
               match(Token.TokenType.OPENBRACKET_TOKEN);
               Expression arrExp = parseExpression();
               match(Token.TokenType.CLOSEBRACKET_TOKEN);
               id.setArrayData(arrExp);
               //In this case we need to continue to expression'', according to
               //the grammar
               Expression retExp = parseExpressionDoublePrime(id);
               return retExp;
           case CLOSEPAREN_TOKEN:
           case SEMICOLON_TOKEN:
           case COMMA_TOKEN:
           case CLOSEBRACKET_TOKEN:
               return id;
           default:
               if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                  scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             ": " + scanner.viewNextToken().getData().toString() + " in parseExpressionPrime()");
                } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             " in parseExpressionPrime()");
                }
               
               
       }
   }
   
    @Override
   public Expression parseExpressionDoublePrime(Identifier id) throws ParseException{
       switch(scanner.viewNextToken().getType()){
           case ASSIGN_TOKEN:
               //We still have the assign expression case here, according to the grammar
               match(Token.TokenType.ASSIGN_TOKEN);
               Expression exp = parseExpression();
               return new AssignExpression(id, exp);
           //First set of simple-expression'
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
               return parseSimpleExpression(id);
           case CLOSEPAREN_TOKEN:
           case SEMICOLON_TOKEN:
           case COMMA_TOKEN:
           case CLOSEBRACKET_TOKEN:
               return id;
           default:
               if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                  scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             ": " + scanner.viewNextToken().getData().toString() + " in parseExpressionDoublePrime()");
               } else {
                    throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                             " in parseExpressionDoublePrime()");
               }
       }
           
   }
   
   public Expression parseSimpleExpression(Expression exp) throws ParseException{
        Expression lhs = parseAdditiveExpression(exp);

        if (scanner.viewNextToken().getType() == Token.TokenType.LT_TOKEN ||
            scanner.viewNextToken().getType() == Token.TokenType.GT_TOKEN ||
            scanner.viewNextToken().getType() == Token.TokenType.LTEQ_TOKEN ||
            scanner.viewNextToken().getType() == Token.TokenType.GTEQ_TOKEN ||
            scanner.viewNextToken().getType() == Token.TokenType.EQ_TOKEN ||
            scanner.viewNextToken().getType() == Token.TokenType.NOTEQ_TOKEN) {
                Token op = scanner.getNextToken();
                Expression rhs = parseAdditiveExpression(null);
                    // make lhs the result, so set up for next iter
                lhs = new BinaryExpression (lhs, rhs, op);               
        }

        return lhs;

   }
   
   public Expression parseAdditiveExpression(Expression exp) throws ParseException{
       Expression lhs = parseTerm(exp);

        while (scanner.viewNextToken().getType() == Token.TokenType.PLUS_TOKEN || 
               scanner.viewNextToken().getType() == Token.TokenType.MINUS_TOKEN) {
            Token op = scanner.getNextToken();
            Expression rhs = parseTerm(null);
            // make lhs the result, so set up for next iter
            lhs = new BinaryExpression (lhs, rhs, op);               
        }

        return lhs;

   }
   
   public Expression parseTerm(Expression exp) throws ParseException{
       Expression lhs;
       //If null was passed, we make lhs the result of parseFactor. If not, we assign lhs the parameter
       if(exp == null){
            lhs = parseFactor();

            while (scanner.viewNextToken().getType() == Token.TokenType.MULT_TOKEN || 
                   scanner.viewNextToken().getType() == Token.TokenType.DIV_TOKEN) {
                Token op = scanner.getNextToken();
                Expression rhs = parseFactor();
                // make lhs the result, so set up for next iter
                lhs = new BinaryExpression (lhs, rhs, op);               
            }

            return lhs;
           
           
       } else {
            lhs = exp;

            while (scanner.viewNextToken().getType() == Token.TokenType.MULT_TOKEN || 
                   scanner.viewNextToken().getType() == Token.TokenType.DIV_TOKEN) {
                Token op = scanner.getNextToken();
                Expression rhs = parseFactor();
                // make lhs the result, so set up for next iter
                lhs = new BinaryExpression (lhs, rhs, op);               
            }

            return lhs;
           
       }
   }
   
   public Expression parseFactor() throws ParseException{
                //Check first set of factor
                switch(scanner.viewNextToken().getType()){
                    case OPENPAREN_TOKEN:
                        //Expression within parentheses case
                        match(Token.TokenType.OPENPAREN_TOKEN);
                        Expression multExp = parseExpression();
                        match(Token.TokenType.CLOSEPAREN_TOKEN);
                        return multExp;

                    case ID_TOKEN:
                        //Var-call or array case
                        Identifier multId = parseIdentifier();
                        if(scanner.viewNextToken().getType() == Token.TokenType.OPENPAREN_TOKEN){
                           match(Token.TokenType.OPENPAREN_TOKEN);
                           CallExpression idCallExp = parseCallExpression(multId);
                           match(Token.TokenType.CLOSEPAREN_TOKEN);

                           return idCallExp;
                        } else if (scanner.viewNextToken().getType() == Token.TokenType.OPENBRACKET_TOKEN){
                            match(Token.TokenType.OPENBRACKET_TOKEN);
                            Expression bracketExpression = parseExpression();
                            match(Token.TokenType.CLOSEBRACKET_TOKEN);
                            multId.setArrayData(bracketExpression);

                            return multId;
                        } else if (scanner.viewNextToken().getType() == Token.TokenType.MULT_TOKEN || 
                                   scanner.viewNextToken().getType() == Token.TokenType.DIV_TOKEN || 
                                   scanner.viewNextToken().getType() == Token.TokenType.PLUS_TOKEN || 
                                   scanner.viewNextToken().getType() == Token.TokenType.MINUS_TOKEN || 
                                   scanner.viewNextToken().getType() == Token.TokenType.LT_TOKEN ||
                                   scanner.viewNextToken().getType() == Token.TokenType.GT_TOKEN ||
                                   scanner.viewNextToken().getType() == Token.TokenType.LTEQ_TOKEN ||
                                   scanner.viewNextToken().getType() == Token.TokenType.GTEQ_TOKEN ||
                                   scanner.viewNextToken().getType() == Token.TokenType.EQ_TOKEN ||
                                   scanner.viewNextToken().getType() == Token.TokenType.NOTEQ_TOKEN ||
                                   scanner.viewNextToken().getType() == Token.TokenType.CLOSEPAREN_TOKEN ||
                                   scanner.viewNextToken().getType() == Token.TokenType.SEMICOLON_TOKEN) {
                            //Simply just the ID (not call or array)
                            return multId;
                        } else {
                            if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                               scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                                         ": " + scanner.viewNextToken().getData().toString() + " in parseFactor()");
                            } else {
                                throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                                         " in parseFactor()");
                            }
                        }

                    case NUM_TOKEN:
                        //Num case
                        Numeric factorNum = parseNumeric();
                        return factorNum;

                    default:
                        if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
                           scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                            throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                                     ": " + scanner.viewNextToken().getData().toString() + " in parseFactor()");
                        } else {
                            throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                                     " in parseFactor()");
                        }




                }
   }
    

   public CallExpression parseCallExpression(Identifier id) throws ParseException{
        ArrayList<Expression> args = new ArrayList<>();
        //Start parsing the list of expressions within the call
        //If first condition fails and the next token is not a close paren,
        //an exception is thrown
        if(scanner.viewNextToken().getType() == Token.TokenType.OPENPAREN_TOKEN || 
           scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN || 
           scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN){
            args.add(parseExpression());
            while(scanner.viewNextToken().getType() == Token.TokenType.COMMA_TOKEN){
                match(Token.TokenType.COMMA_TOKEN);
                args.add(parseExpression());
            }
        } else if (scanner.viewNextToken().getType() != Token.TokenType.CLOSEPAREN_TOKEN){
            if(scanner.viewNextToken().getType() == Token.TokenType.ID_TOKEN || 
               scanner.viewNextToken().getType() == Token.TokenType.NUM_TOKEN){
                 throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                          ": " + scanner.viewNextToken().getData().toString() + " in parseExpressionPrime()");
             } else {
                 throw new ParseException("Error: Unexpected Token " + scanner.viewNextToken().getType().toString() + 
                                          " in parseExpressionPrime()");
             }
        }
        
        return new CallExpression(id, args);
   }
   
   
   
    
}
