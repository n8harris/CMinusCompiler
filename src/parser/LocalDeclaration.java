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
public class LocalDeclaration {
    
    private ArrayList<VarDeclaration> varList;
    
    public LocalDeclaration(ArrayList<VarDeclaration> v){
        varList = v;
    }
    
}
