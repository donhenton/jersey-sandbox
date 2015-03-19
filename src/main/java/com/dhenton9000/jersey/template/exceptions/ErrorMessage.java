package com.dhenton9000.jersey.template.exceptions;

import java.lang.reflect.InvocationTargetException;
import javax.ws.rs.ClientErrorException;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement
public class ErrorMessage {

    private final static Logger LOG = LoggerFactory.getLogger(ErrorMessage.class);
    /**
     * contains the same HTTP Status code returned by the server
     */
    @XmlElement(name = "status")
    int status;

    /**
     * application specific error code
     */
    @XmlElement(name = "code")
    int code;

    /**
     * message describing the error
     */
    @XmlElement(name = "message")
    String message;

    /**
     * link point to page where the error message is documented
     */
    @XmlElement(name = "link")
    String link;

    /**
     * extra information that might useful for developers
     */
    @XmlElement(name = "developerMessage")
    String developerMessage;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ErrorMessage(AppException ex) {

        try {
            BeanUtils.copyProperties(this, ex);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOG.error("bean util copy problem in error "+e.getMessage());
        }

    }

    public ErrorMessage(ClientErrorException ex) {
        this.status = ex.getResponse().getStatus();
        this.message = ex.getMessage();
        this.link = "https://jersey.java.net/apidocs/2.8/jersey/javax/ws/rs/ClientErrorException.html";
    }

    public ErrorMessage() {
    }
}
