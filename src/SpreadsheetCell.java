/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;

/**
 *
 * @author LeeKiatHaw
 */
public class SpreadsheetCell {
    
    private String originalContent;
    
    private String result;
    
    public SpreadsheetCell(){
        this("");
    }
    
    public SpreadsheetCell(String originalContent) {
        this.originalContent = originalContent;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }
    
    
}
