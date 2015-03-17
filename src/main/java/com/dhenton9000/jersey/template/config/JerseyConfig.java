/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.config;

import com.dhenton9000.jersey.template.resources.DemoResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.dhenton9000.jersey.template.util.CORSResponseFilter;
import com.dhenton9000.jersey.template.util.LoggingResponseFilter;
import com.fasterxml.jackson.databind.SerializationFeature;
//import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.config.SwaggerConfig;
import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;
import com.wordnik.swagger.model.ApiInfo;
import org.glassfish.jersey.server.spring.SpringComponentProvider;

/**
 *
 * @author dhenton
 */
public class JerseyConfig extends ResourceConfig {

    /**
     * Register JAX-RS application components.
     */
    public JerseyConfig() {
        register(RequestContextFilter.class);
        register(new JsonProvider());
        packages("com.dhenton9000.jersey.template.resources");

        //register(JacksonFeature.class); //replaced by jsonprovider 		
        register(LoggingResponseFilter.class);
        register(CORSResponseFilter.class);
        register(SpringComponentProvider.class);

        SwaggerConfig config = ConfigFactory.config();
        ApiInfo info = new ApiInfo(
                "Jersey Simple API",
                "Simple Resources",
                "https://www.foobar.com/eula",
                "support@mycompany.com",
                "MIT",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );
        ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();

        config.setApiInfo(info);
        config.setApiVersion("2.3.4");
        config.setApiPath("/api/api-docs");
        config.setBasePath("/"); // only need relative path
        packages("com.wordnik.swagger.jaxrs.json");
        packages("com.wordnik.swagger.jersey.listing");
        scanner.setResourcePackage(JerseyConfig.class.getPackage().getName());
        ScannerFactory.setScanner(scanner);

    }

    public static class JsonProvider extends JacksonJaxbJsonProvider {

        public JsonProvider() {
            super();
            ObjectMapper mapper = new ObjectMapper();
            //set properties on the mapper here
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            setMapper(mapper);
        }
    }
}
