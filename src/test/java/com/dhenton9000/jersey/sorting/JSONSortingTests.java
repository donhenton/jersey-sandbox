/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.sorting;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * Tests for sorting JSON objects in Java. This will be the basis for
 * stabilizing gold file samples which have arrays which may have random sorts
 * due to nondeterministic fill of arrays
 *
 * http://docs.oracle.com/javase/7/docs/technotes/guides/scripting/programmer_guide/
 * http://jonstefansson.blogspot.com/2010/08/processing-json-with.html
 * http://www.obsidianscheduler.com/blog/integrating-java-with-scripting-languages/
 * http://stackoverflow.com/questions/979256/sorting-an-array-of-javascript-objects
 *
 * @author dhenton
 */
public class JSONSortingTests {

    private final String sampleRestaurants;
    private org.slf4j.Logger LOG = LoggerFactory.getLogger(JSONSortingTests.class);

    public JSONSortingTests() {
        String sampleClassPath = "/test-samples/sample-restaurants.json";
        try {
            this.sampleRestaurants = readGoldFile(sampleClassPath);
        } catch (IOException ex) {
            throw new RuntimeException("nothing at " + sampleClassPath);
        }
    }

    @Test
    public void testSampleSort() throws IOException, ScriptException, NoSuchMethodException {
        assertTrue(true);
        assertNotNull(sampleRestaurants);
        int size = 3;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode sampleTree = null;
        try {
            sampleTree = mapper.readTree(sampleRestaurants);
        } catch (com.fasterxml.jackson.core.JsonParseException ex) {
            fail(ex.getMessage());
        }

        assertNotNull(sampleTree);
        ArrayNode arrayData = (ArrayNode) sampleTree.get("sample");

        assertEquals(arrayData.size(), size);
        assertEquals("Arby's Roast Beef Restaurant",arrayData.iterator().next().get("name").asText());

        ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        ScriptEngine engine = factory.getEngineByName("JavaScript");

        engine.eval(new FileReader(getFileFromClassPath("/jsfiles/json3.js", this.getClass())));
        engine.eval(new FileReader(getFileFromClassPath("/jsfiles/sortUtils.js", this.getClass())));

        Invocable inv = (Invocable) engine;
        String sortedString = (String) inv.invokeFunction("sortRestaurantsByState", sampleRestaurants);
        assertNotNull(sortedString);

        mapper = new ObjectMapper();
        sampleTree = null;
        try {
            sampleTree = mapper.readTree(sortedString);
        } catch (com.fasterxml.jackson.core.JsonParseException ex) {
            //LOG.error("parse error:\n" + sortedString);
        }
        assertNotNull(sampleTree);
        arrayData = (ArrayNode) sampleTree.get("sample");
        assertEquals(arrayData.size(), size);
        assertEquals("Bellefleur Winery & Restaurant",arrayData.iterator().next().get("name").asText());
        
    }

    protected final String readGoldFile(String classpathToGoldFile) throws IOException {
        File goldFile
                = getFileFromClassPath(classpathToGoldFile, this.getClass());

        return FileUtils.readFileToString(goldFile, "UTF-8");
    }

    protected static File getFileFromClassPath(String path, Class clazz) {
        URL u = clazz.getResource(path);
        File t = new File(FileUtils.toFile(u).getAbsolutePath());

        if (!t.exists()) {
            throw new RuntimeException("nothing at " + path);
        }
        return t;
    }
}
