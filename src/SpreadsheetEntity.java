/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LeeKiatHaw
 */
public class SpreadsheetEntity {
    
    private int columns;
    private int rows;
    
    //2 dimensional table of cells
    private List<List<SpreadsheetCell>> cells;
    
    public SpreadsheetEntity(int columns, int rows){
        
        this.columns = columns;
        this.rows = rows;
        
        //Instantiate the 2D List
        //The location of a cell is specified by [ROW,COL]
        this.cells = new ArrayList<>();
        for(int y=0; y<this.rows; y++){
            List<SpreadsheetCell> row = new ArrayList<SpreadsheetCell>();
            cells.add(row);
            for(int x=0; x<this.columns; x++){
                row.add(new SpreadsheetCell());
            }
        }
    }
    
    public SpreadsheetCell getCell(String cellLoc){
        //Parse the location as a 2 char string
        //If > 2 char, just take the first 2 char
        char rowChar = cellLoc.charAt(0);
        
        
        //Check if 1st char is a Letter, assuming that there can only be a maximum of 26 rows
        if(!Character.isLetter(rowChar))
            throw new RuntimeException("Cell location "+cellLoc+" is invalid");
        
        //Check if 2nd char is a number, it can be any number n
        //parseInt will throw an exception if the substring is not a number
        int colNum = Integer.parseInt(cellLoc.substring(1))-1;
        
        //Convert rowChar to num
        rowChar = Character.toUpperCase(rowChar);
        int rowNum = rowChar - 'A';
        
        return this.getCell(rowNum, colNum);
    }
    
    public void setCellContent(String content, String cellLoc){
        this.getCell(cellLoc).setOriginalContent(content);
    }
    
    public void setCellContent(String content, int row, int col){
        this.getCell(row,col).setOriginalContent(content);
    }
    
    public SpreadsheetCell getCell(int row, int col){
        return this.cells.get(row).get(col);
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }
    
    
}
