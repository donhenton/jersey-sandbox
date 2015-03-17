package com.dhenton9000.jersey.template.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingResponseFilter
		implements ContainerResponseFilter {

	private static final Logger logger = LoggerFactory.getLogger(LoggingResponseFilter.class);

        @Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		String method = requestContext.getMethod();
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
                
		logger.debug("Requesting " + method + " for path " + requestContext.getUriInfo().getPath());
		Object entity = responseContext.getEntity();
		if (entity != null) {
			logger.debug("Response " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entity));
		}
                else
                {
                    logger.debug("null entity in logging filter");
                }

	}


}
