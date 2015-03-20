/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.client.velocity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.StringWriter;
import org.apache.log4j.LogManager;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
 
 
 

/**
 *
 * @author dhenton
 */
public class VelocityTests {

    private Logger LOG = LogManager.getLogger(VelocityTests.class);
    
    @Test
    public void testVelocity() throws Exception {
        VelocityEngineFactory vFactory = new VelocityEngineFactory();
        VelocityEngine ve = vFactory.getEngine();

        VelocityContext context = new VelocityContext();
        context.put("alpha", "alpha");
        context.put("beta", 55);
        

        StringWriter writer = new StringWriter();

        ve.mergeTemplate("/velocity_templates/test.vm", "UTF-8", context, writer);
        writer.flush();
        writer.close();
        String tString = writer.toString();
        LOG.debug("tString "+tString);
         
        ObjectMapper mapper = new ObjectMapper();
        JsonNode sampleTree = mapper.readTree(tString);
        
        
        assertEquals(55,sampleTree.get("beta").asInt());

    }
}
