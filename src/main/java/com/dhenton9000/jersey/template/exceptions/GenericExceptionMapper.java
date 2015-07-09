package com.dhenton9000.jersey.template.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dhenton9000.jersey.template.resources.DemoResource.ERROR_TYPE;

/**
 * Generic error handler. This will not work in this setup because it will pick
 * up the jersey error of not finding the Swagger index.jsp page. this is a 404
 * error to jersey and so it would come here.
 *
 *
 *
 * http://www.codingpedia.org/ama/error-handling-in-rest-api-with-jersey/
 *
 * @author dhenton
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOG
            = LoggerFactory.getLogger(GenericExceptionMapper.class);

    @Override
    public Response toResponse(Throwable ex) {

        ErrorMessage errorMessage = new ErrorMessage();
        setHttpStatus(ex, errorMessage);
        errorMessage.setCode(AppConstants.GENERIC_APP_ERROR_CODE);
        errorMessage.setMessage(ex.getMessage());
        StringWriter errorStackTrace = new StringWriter();
        ex.printStackTrace(new PrintWriter(errorStackTrace));
        errorMessage.setDeveloperMessage(errorStackTrace.toString());
        errorMessage.setLink(AppConstants.GENERIC_APP_URL);
        LOG.debug("in generic " + ex.getMessage());
        return Response.status(errorMessage.getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private void setHttpStatus(Throwable ex, ErrorMessage errorMessage) {
        ERROR_TYPE t = null;
        String exMessage = ex.getMessage();
        try {
            t = ERROR_TYPE.valueOf(exMessage);

        } catch (Exception err) {
        }
        if (t != null && (t.compareTo(ERROR_TYPE.logger_error) == 0)) {
            throw new RuntimeException("Error of type "
                    + t.toString() + " @@@@@@@@@@@@@@@@@@@@@@@");
        }

        if (ex instanceof WebApplicationException) { //NICE way to combine both of methods, say it in the blog 
            errorMessage.setStatus(((WebApplicationException) ex).getResponse().getStatus());
            LOG.debug("hit webapp except");
        } else {
            LOG.debug("hit general");
            errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); //defaults to internal server error 500
        }
    }
}
