/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Stack;

/**
 *
 * @author LeeKiatHaw
 */
public class CyclicalDependencyException extends Exception{
    
    private Stack<String> dependency;

    public CyclicalDependencyException(Stack<String> dependency) {
        super();
        this.dependency = dependency;
    }

    @Override
    public String getMessage() {
        String message = "There is a cyclical dependency detected in cells ";
        boolean first = true;
        for(String s : dependency){
            if(first){
                message += s;
                first = false;
            }
            else{
                message += "->"+s;
            }
        }
        return message;
    }
    
    
    
}
