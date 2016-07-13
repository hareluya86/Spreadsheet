/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author LeeKiatHaw
 */
public class Spreadsheet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CyclicalDependencyException, IOException {
        //Read file from System.in
        //FileReader fr = new FileReader(new File("spreadsheet.txt"));
        SpreadsheetEntity spreadsheet = 
            SpreadsheetReader.buildSpreadsheet(new BufferedReader(new InputStreamReader(System.in)));
                
        //SpreadsheetReader.buildSpreadsheet(new BufferedReader(fr));
        CellEvaluator.evaluateAll(spreadsheet);
    }
    
}
