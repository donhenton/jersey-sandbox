package com.dhenton9000.jersey.client.velocity;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
 
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
 
/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

/**
 *
 * possible properties for a file based loader resource.loader = file
 * file.resource.loader.class =
 * org.apache.velocity.runtime.resource.loader.FileResourceLoader
 * file.resource.loader.path = c:/tomcat/webapps/velocity/WEB-INF/templates
 * file.resource.loader.cache = true
 * file.resource.loader.modificationCheckInterval = 2
 *
 * @author dhenton
 */
public class VelocityEngineFactory {

    private static final Logger logger = LogManager.getLogger(VelocityEngineFactory.class);

    public VelocityEngine getEngine() throws Exception {

        VelocityEngine ve = new VelocityEngine();

       // ve.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM, this);
        ve.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
//        ve.setProperty("file.resource.loader.path","templates") ;
//        ve.setProperty("file.resource.loader.class","org.apache.velocity.runtime.resource.loader.FileResourceLoader") ;
//        ve.setProperty("file.resource.loader.cache","true") ;
//        ve.setProperty("file.resource.loader.modificationCheckInterval","2") ;

        return ve;
    }
}



/*
 
 * 
 * use to read from a jar file
 * 
 * 
 * 
      Engine ve = new VelocityEngine();
            ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

            ve.init();

            final String templatePath = "templates/" + templateName + ".vm";
            InputStream input = this.getClass().getClassLoader().getResourceAsStream(templatePath);
            if (input == null) {
                throw new IOException("Template file doesn't exist");
            }

            InputStreamReader reader = new InputStreamReader(input);

            VelocityContext context = new VelocityContext();

            if (properties != null) {
                stringfyNulls(properties);
                for (Map.Entry<String, Object> property : properties.entrySet()) {
                    context.put(property.getKey(), property.getValue());
                }
            }

            Template template = ve.getTemplate(templatePath, "UTF-8");
            String outFileName = File.createTempFile("report", ".html").getAbsolutePath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFileName)));

            if (!ve.evaluate(context, writer, templatePath, reader)) {
                throw new Exception("Failed to convert the template into html.");
            }

            template.merge(context, writer);

            writer.flush();
            writer.close();

 
 */