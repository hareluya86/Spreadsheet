/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author LeeKiatHaw
 */
public class SpreadsheetReader {

    public static SpreadsheetEntity buildSpreadsheet(BufferedReader file) throws IOException {
        String firstLine = file.readLine();
        String[] size = firstLine.trim().split(" ");
        
        if(size.length != 2) 
            throw new RuntimeException("Size of spreadsheet is not specified: "+size);
        
        SpreadsheetEntity newSpreadsheet = new SpreadsheetEntity(Integer.parseInt(size[0]),Integer.parseInt(size[1]));
        
        //The spreadsheet goes by A1, A2, A3, B1, B2....
        /*for(int y=0; y < newSpreadsheet.getRows(); y++){
            for(int x=0; x < newSpreadsheet.getColumns(); x++){
                String line = file.readLine();
            }
        }*/
        int row = 0;
        int col = 0;
        String line;
        while((line = file.readLine()) != null){
            newSpreadsheet.setCellContent(line, row, col++);
            
            if(col >= newSpreadsheet.getColumns()){
                row++;
                col = 0;
            }
        }
        return newSpreadsheet;
    }
}
