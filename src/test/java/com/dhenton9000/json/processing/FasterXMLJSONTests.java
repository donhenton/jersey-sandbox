/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.json.processing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
 

/**
 * These tests illustrate the FasterXML JSON model without marshalling.
 * @author dhenton
 */
public class FasterXMLJSONTests {

    private static final String TEST_STRING = "{\"alpha\":35,\"beta\":\"get a job\"}";
    private static Logger LOG = LogManager.getLogger(FasterXMLJSONTests.class);

    @Test
    public void testModifyTree() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode sampleTree = mapper.readTree(TEST_STRING);
        JsonNode jsonNodeValue = sampleTree.get("alpha");
        ObjectNode sampleObj = (ObjectNode) sampleTree;
        sampleObj.put("alpha", 99);
        

        assertEquals(sampleTree.get("alpha").intValue(), 99);

        // ob.put("alpha", 555); // or int, long, boolean etc
    }
    
    
    @Test
    public void testModifiedTreeToString() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode sampleTree = mapper.readTree(TEST_STRING); 
        ObjectNode sampleObj = (ObjectNode) sampleTree;
        sampleObj.put("alpha", 99);
        assertEquals(sampleTree.toString(),"{\"alpha\":99,\"beta\":\"get a job\"}") ;

      
    }

    @Test
    public void testUsingStandardJSR() {
        
        
        StringReader sr = new StringReader(TEST_STRING);
        JsonObject restaurantObject;
        try (JsonReader myreader = Json.createReader(sr)) {
            restaurantObject = myreader.readObject();
        }
        
        LOG.debug("class is "+ restaurantObject.getClass().getName());

    }
}
