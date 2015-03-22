/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;

/**
 *
 * @author Nate H
 */
public class CallExpression extends Expression {
    
    private Identifier id;
    private ArrayList<Expression> arg;
    
    public CallExpression(Identifier i, ArrayList<Expression> a){
        id = i;
        arg = a;
    }
    @Override
    public void printExpression(String offset){
        System.out.println(offset + "CallExpression");
        id.printIdentifier(offset + "    ");
        for (Expression expr : arg) {
            expr.printExpression(offset + "    ");
        }
    }
}
