/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatortest.utilities;

import java.util.Stack;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author LeeKiatHaw
 */
public class CellEvaluatorTest {
    
    Stack<String> s1;
    
    SpreadsheetCell c1 = new SpreadsheetCell("4 5 * ");
    SpreadsheetCell c2 = new SpreadsheetCell("10 5 2 - ");
    SpreadsheetCell c3 = new SpreadsheetCell("20 3 / 2 + ");
    
    SpreadsheetEntity spreadsheet = new SpreadsheetEntity(3,3);
    
    
    public CellEvaluatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        //Test case 1
        s1 = new Stack<String>();
        s1.push("3");
        s1.push("7");
        s1.push("5");
        s1.push("-");
        
        //Test case 2
        spreadsheet.setCellContent("A2", "A1");
        spreadsheet.setCellContent("4 5 *", "A2");
        spreadsheet.setCellContent("A1", "A3");
        spreadsheet.setCellContent("A1 B2 / 2 +", "B1");
        spreadsheet.setCellContent("3", "B2");
        spreadsheet.setCellContent(" 39 B1 B2 * / ", "B3");
        
        //Test case 3
        spreadsheet.setCellContent("C2 1 +", "C1");
        spreadsheet.setCellContent("C1", "C2");
        spreadsheet.setCellContent("C1 C2 *", "C3");
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of evaluate method, of class CellEvaluator.
     */
    @Test
    public void testEvaluate_Spreadsheet_cellLoc() {
        try {
            System.out.println("evaluate");
            
            assertEquals(CellEvaluator.evaluate(spreadsheet, "A1", null),"20.00000");
            assertEquals(CellEvaluator.evaluate(spreadsheet, "A2", null),"20.00000");
            assertEquals(CellEvaluator.evaluate(spreadsheet, "A3", null),"20.00000");
            assertEquals(CellEvaluator.evaluate(spreadsheet, "B1", null),"8.66667");
            assertEquals(CellEvaluator.evaluate(spreadsheet, "B2", null),"3.00000");
            assertEquals(CellEvaluator.evaluate(spreadsheet, "B3", null),"1.50000");
        } catch (CyclicalDependencyException ex) {
            fail("Cyclical dependency found!");
        }
    }

    @Test(expected = CyclicalDependencyException.class)
    public void testEvaluate_Cyclical1() throws CyclicalDependencyException {
        System.out.println("cyclical1");
        
        CellEvaluator.evaluate(spreadsheet, "C1", null);
    }
    
    @Test(expected = CyclicalDependencyException.class)
    public void testEvaluate_Cyclical2() throws CyclicalDependencyException {
        System.out.println("cyclical2");
        
        CellEvaluator.evaluate(spreadsheet, "C2", null);
    }
    
    @Test(expected = CyclicalDependencyException.class)
    public void testEvaluate_Cyclical3() throws CyclicalDependencyException {
        System.out.println("cyclical3");
        
        CellEvaluator.evaluate(spreadsheet, "C3", null);
    }
}
