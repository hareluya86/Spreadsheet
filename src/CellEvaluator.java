/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Stack;

/**
 * 
 * @author LeeKiatHaw
 */
public class CellEvaluator {
    
    public static enum ELEM_TYPE{
        OPERAND,
        OPERATOR,
        CELL_LOCATION
        
    }
    
    public static String evaluate(SpreadsheetEntity spreadsheet, int row, int col,Stack<String> dependencyChain) throws CyclicalDependencyException{
        
        String rowChar = String.valueOf((char)(row + 'A'));
        col++;
        String cellLoc = rowChar+col;
        return evaluate(spreadsheet,cellLoc,dependencyChain);
    }
    
    public static String evaluate(SpreadsheetEntity spreadsheet, String cellLoc, Stack<String> dependencyChain) throws CyclicalDependencyException{
        
        //Check for cyclical dependency
        if(dependencyChain!=null && dependencyChain.contains(cellLoc)){
            dependencyChain.push(cellLoc);
            throw new CyclicalDependencyException(dependencyChain);
        }
            
        
        SpreadsheetCell cell = spreadsheet.getCell(cellLoc);
        
        //If the result was already computed, skip and return the existing result
        if(cell.getResult() != null && !cell.getResult().isEmpty())
            return cell.getResult();
        
        //Clean up and build the computational stack for the duplicated operators side by side
        String[] elements = cell.getOriginalContent().trim().split(" ");
        Stack<String> compStack = new Stack<>();
        
        for(int i=0; i<elements.length; i++){
            //If it is an operator, check if the previous element was also an operator
            if(isOperator(elements[i])){
                if(compStack.isEmpty())
                    throw new RuntimeException("Computational stack is empty!");
                if(isOperator(compStack.peek()))
                    compStack.pop();//remove the previous operator
            }
            
            compStack.push(elements[i]);
                
        }
        
        //Traverse the stack and start computing
        //Check if the cell element is a
        // 1) Operand
        // 2) Operator
        // 3) Cell location
        // If Cell location is found, do not evaluate at all, leave the expression
        Stack<String> resultStack = new Stack<>();
        while(!compStack.isEmpty()){
            String next = compStack.remove(0);
            switch(checkCellElement(next)){
                case OPERAND    :   {
                    //If there is only 1 operand, format before storing
                    resultStack.push(String.format("%.5f",Double.parseDouble(next)));
                    break;
                }
                                    
                case OPERATOR   :   {
                    String result = evaluate(resultStack,next);
                    resultStack.push(result);
                    break;
                }
                case CELL_LOCATION: {
                    if(dependencyChain == null)
                        dependencyChain = new Stack<String>();
                    dependencyChain.push(cellLoc);
                    String result = evaluate(spreadsheet,next,dependencyChain); //recursive call
                    resultStack.push(result);
                    break;
                }
                default :   {
                    break;
                }
            }
        }
        String result = "0.0";
        if(!resultStack.isEmpty())
            result = resultStack.elementAt(0);
        
        //Store the value in the cell so that we only compute once for each cell
        cell.setResult(result);
        
        return result;
    }
    
    //Return String so we can test
    public static String evaluate(Stack<String> stack, String operator){
        double result = 0.0;
        //Compute addition
        if(operator.compareTo("+") == 0){
            if(stack == null)
                return "0.0";
            //double result = 0.0;
            while(!stack.isEmpty()){
                String next = stack.pop();
                double operand = Double.parseDouble(next);
                result += operand;
            }
            return String.format("%.5f",result);
        }
        
        //Compute subtraction
        if(operator.compareTo("-") == 0){
            if(stack == null)
                return "0.0";
            //double result = 0.0;
            boolean first = true;
            while(!stack.isEmpty()){
                String next = stack.remove(0);
                double operand = Double.parseDouble(next);
                if(first) {
                    result += operand;
                    first = false;
                }
                    
                else
                    result -= operand;
            }
            return String.format("%.5f",result);
        }
        
        //Compute subtraction
        if(operator.compareTo("*") == 0){
            if(stack == null)
                return "0.0";
            //double result = 0.0;
            boolean first = true;
            while(!stack.isEmpty()){
                String next = stack.pop();
                double operand = Double.parseDouble(next);
                if(first){
                    result = operand;
                    first = false;
                }
                else
                    result *= operand;
            }
            return String.format("%.5f",result);
        }
        
        //Compute division
        if(operator.compareTo("/") == 0){
            if(stack == null)
                return "0.0";
            //double result = 0.0;
            boolean first = true;
            while(!stack.isEmpty()){
                String next = stack.remove(0);
                double operand = Double.parseDouble(next);
                if(first){
                    result = operand;
                    first = false;
                }
                else
                    result /= operand;
            }
            return String.format("%.5f",result);
        }
        
        //Compute increment
        if(operator.compareTo("++") == 0){
            if(stack == null)
                return "0.0";
            //double result = 0.0;
            while(!stack.isEmpty()){
                String next = stack.pop();
                double operand = Double.parseDouble(next);
                result += operand;
            }
            result += 1; //If there are more than 1 numbers, then their sum will be incremented
            return String.format("%.5f",result);
        }
        
        //Compute subtraction
        if(operator.compareTo("--") == 0){
            if(stack == null)
                return "0.0";
            //double result = 0.0;
            boolean first = true;
            while(!stack.isEmpty()){
                String next = stack.remove(0);
                double operand = Double.parseDouble(next);
                if(first) {
                    result += operand;
                    first = false;
                }
                    
                else
                    result -= operand;
            }
            result -= 1; //If there are more than 1 numbers, their difference will be decremented.
            return String.format("%.5f",result);
        }
        
        throw new RuntimeException("Unrecognized operator: "+operator);
    }
    
    public static ELEM_TYPE checkCellElement(String exp){
        if(isOperator(exp))
            return ELEM_TYPE.OPERATOR;
        if(isOperand(exp))
            return ELEM_TYPE.OPERAND;
        if(isCell(exp))
            return ELEM_TYPE.CELL_LOCATION;
        
        throw new RuntimeException("Expression "+exp+" is invalid");
    }
    
    public static boolean isOperator(String exp){
        if(exp.compareTo("+") == 0)
            return true;
        if(exp.compareTo("-") == 0)
            return true;
        if(exp.compareTo("*") == 0)
            return true;
        if(exp.compareTo("/") == 0)
            return true;
        if(exp.compareTo("++") == 0)
            return true;
        if(exp.compareTo("--") == 0)
            return true;
        return false;
    }
    
    public static boolean isOperand(String exp){
        //It is a pure double
        try{
            Double.parseDouble(exp);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
        
    }
    
    public static boolean isCell(String exp){
        if(!Character.isLetter(exp.charAt(0))) return false;
        try{
            Integer.parseInt(exp.substring(1));
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }
    
    public static void evaluateAll(SpreadsheetEntity spreadsheet) throws CyclicalDependencyException{
        for(int y=0; y<spreadsheet.getRows(); y++){
            for(int x=0; x<spreadsheet.getColumns(); x++){
                System.out.println(evaluate(spreadsheet,y,x,null));
            }
        }
    }
}
