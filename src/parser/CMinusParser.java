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
        ArrayList<Parameter> params;
        params.push(parseParameter());
        while(scanner.viewNextToken().getType() == Token.TokenType.COMMA_TOKEN) {
            match(Token.TokenType.COMMA_TOKEN);
            params.push(parseParameter());
        }
        match(Token.TokenType.CLOSEPAREN_TOKEN);
        CompoundStatement compoundStmt = parseCompoundStatement();
        return new FunctionDeclaration(params, compoundStmt, new Identifier(s));
    }
    
}
