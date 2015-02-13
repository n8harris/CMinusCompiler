

package cminuscompiler;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

 
%%
 
%public 
%class JFlexScanner 
%implements Scanner
%unicode

%init{
	this.zzReader = in;
	nextToken = yylex();
%init}

%eofval{
	return new Token(Token.TokenType.EOF_TOKEN);
%eofval}

%eofclose

%{

	private Token nextToken;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //First command line argument is input, second is output
        try{
            java.io.Reader reader = new FileReader(args[0]);
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
            JFlexScanner scanner = new JFlexScanner(reader);
            
            //Check to make sure we are not at the end of the file
            while(scanner.viewNextToken().getType() != Token.TokenType.EOF_TOKEN){
                //Get the next token and check the type
                Token currentToken = scanner.getNextToken();
                Token.TokenType type = currentToken.getType();
                //If token is identifier or num, print out the value too
                if(type == Token.TokenType.ID_TOKEN || type == Token.TokenType.NUM_TOKEN){
                    String data = currentToken.getData().toString();
                    writer.write(type + ": " + data);
                    writer.newLine();
                } else {
                    //Don't print out the comment tokens
                    if(type != Token.TokenType.OPENCOMMENT_TOKEN && type != Token.TokenType.CLOSECOMMENT_TOKEN){
                        writer.write(type.toString());
                        writer.newLine();
                    }
                }
            }
        
            writer.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error: " + e.getMessage());
        }
        
        
        
    }
    
    public Token getNextToken () {
        Token returnToken = nextToken;
        if (nextToken.getType() != Token.TokenType.EOF_TOKEN){
            try {
                nextToken = yylex();
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        return returnToken;
    }
    public Token viewNextToken () {
        return nextToken;
    }
	
%}
	
	LineTerminator = \r|\n|\r\n
	InputCharacter = [^\r\n]
	WhiteSpace     = {LineTerminator} | [ \t\f]
	Comment 	   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
	Identifier = [:jletter:] [:jletter:]*

	DecIntegerLiteral = 0 | [1-9][0-9]*
	
%%
	
 /* keywords */
<YYINITIAL> "else"           { return new Token(Token.TokenType.ELSE_TOKEN); }
<YYINITIAL> "if"            { return new Token(Token.TokenType.IF_TOKEN); }
<YYINITIAL> "int"              { return new Token(Token.TokenType.INT_TOKEN); }
<YYINITIAL> "return"              { return new Token(Token.TokenType.RETURN_TOKEN); }
<YYINITIAL> "void"              { return new Token(Token.TokenType.VOID_TOKEN); }
<YYINITIAL> "while"              { return new Token(Token.TokenType.WHILE_TOKEN); }
 <YYINITIAL> {
  /* identifiers */ 
  {Identifier}                   { return new Token(Token.TokenType.ID_TOKEN, yytext()); }
 
  /* literals */
  {DecIntegerLiteral}            { return new Token(Token.TokenType.NUM_TOKEN, yytext()); }

  /* operators */
  "+"                            { return new Token(Token.TokenType.PLUS_TOKEN); }
  "-"                            { return new Token(Token.TokenType.MINUS_TOKEN); }
  "*"                            { return new Token(Token.TokenType.MULT_TOKEN); }
  "/"                            { return new Token(Token.TokenType.DIV_TOKEN); }
  "<"                            { return new Token(Token.TokenType.LT_TOKEN); }
  "<="                            { return new Token(Token.TokenType.LTEQ_TOKEN); }
  ">"                            { return new Token(Token.TokenType.GT_TOKEN); }
  ">="                            { return new Token(Token.TokenType.GTEQ_TOKEN); }
  "=="                            { return new Token(Token.TokenType.EQ_TOKEN); }
  "!="                            { return new Token(Token.TokenType.NOTEQ_TOKEN); }
  "="                            { return new Token(Token.TokenType.ASSIGN_TOKEN); }
  ";"                            { return new Token(Token.TokenType.SEMICOLON_TOKEN); }
  ","                            { return new Token(Token.TokenType.COMMA_TOKEN); }
  "("                            { return new Token(Token.TokenType.OPENPAREN_TOKEN); }
  ")"                            { return new Token(Token.TokenType.CLOSEPAREN_TOKEN); }
  "["                            { return new Token(Token.TokenType.OPENBRACKET_TOKEN); }
  "]"                            { return new Token(Token.TokenType.CLOSEBRACKET_TOKEN); }
  "{"                            { return new Token(Token.TokenType.OPENCURLY_TOKEN); }
  "}"                            { return new Token(Token.TokenType.CLOSECURLY_TOKEN); }

  /* comments */
  {Comment}                      { /* ignore */ }
 
  /* whitespace */
  {WhiteSpace}                   { /* ignore */ }
}

/* error fallback */
[^]                              { throw new IOException("Illegal character <"+
                                                    yytext()+">"); }
													

