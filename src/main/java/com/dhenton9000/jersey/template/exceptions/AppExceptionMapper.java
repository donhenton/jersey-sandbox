package com.dhenton9000.jersey.template.exceptions;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException> {

     private static final Logger LOG = 
                LoggerFactory.getLogger(AppExceptionMapper.class);
    
        @Override
	public Response toResponse(AppException ex) {
                LOG.debug("app exception "+ex.getMessage());
		return Response.status(ex.getStatus())
				.entity(new ErrorMessage(ex))
				.type(MediaType.APPLICATION_JSON).
				build();
	}

        

}
