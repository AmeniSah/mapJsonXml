/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gesis.dataverse.mapjsonxml;

import org.jdom2.Element;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ameni
 */
public class ResourceTypeTest {
    
      
     @Test
    public void TestResourceTypeTest() throws ParseException {
        
        System.out.println("Test Resource Type");
        String entryfilename = "description.txt";
        
        TestResourceType instance = new TestResourceType();
        
        String expResult = "Text";
        //String expResult = "Other";
        String result = instance.resourceType(entryfilename);
        System.out.println("The expected result is: "+expResult);
        System.out.println("The actual result is: "+result);
        //assertEquals(expResult, result);
        Assert.assertEquals(expResult.toLowerCase(), result.toLowerCase());
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
        
        
    }
    
    
   
    
}

