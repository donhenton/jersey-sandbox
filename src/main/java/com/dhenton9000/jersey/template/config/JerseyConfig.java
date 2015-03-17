/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.config;

import com.dhenton9000.jersey.template.resources.DemoResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
//import com.dhenton9000.jersey.template.util.CORSResponseFilter;
//import com.dhenton9000.jersey.template.util.LoggingResponseFilter;
//import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
 

/**
 *
 * @author dhenton
 */
public class JerseyConfig extends ResourceConfig {

    /**
	* Register JAX-RS application components.
	*/	
	public JerseyConfig(){
		//register(RequestContextFilter.class);
                register(new JsonProvider());
		register(DemoResource.class);
		//register(JacksonFeature.class);		
		//register(LoggingResponseFilter.class);
		//register(CORSResponseFilter.class);
	}
 
    public static class JsonProvider extends JacksonJaxbJsonProvider {
		public JsonProvider() {
			super();
			ObjectMapper mapper = new ObjectMapper();
			 
			setMapper(mapper);
		}
	}
}
