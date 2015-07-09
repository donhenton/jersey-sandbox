package com.dhenton9000.jersey.client;

import com.dhenton9000.jersey.template.exceptions.AppException;
import com.dhenton9000.restaurant.model.Restaurant;
import com.dhenton9000.restaurant.model.Reviews;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 http://www.oracle.com/technetwork/articles/java/json-1973242.html
 https://jersey.java.net/documentation/latest/user-guide.html#client

 */
/**
 *
 * @author dhenton
 */
public class JerseyClientExplorer {

    private static Logger LOG = LogManager.getLogger(JerseyClientExplorer.class);

  

    public void doTimeout() throws IOException {

         
            //"http://localhost:8090/jersey-sandbox/webapi/restaurant/review/1/reviewid/0"
                    Socket s = new Socket(InetAddress.getByName("localhost"), 8090);
        PrintWriter pw = new PrintWriter(s.getOutputStream());
        pw.println("GET /jersey-sandbox/webapi/restaurant/review/1/reviewid/1 HTTP/1.1");
        pw.println("Host: localhost");
        pw.println("");
        pw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String t;
        while ((t = br.readLine()) != null) {
            LOG.debug(t);
           
        }
        br.close();
        s.close();
        
//        You should use println instead of print to print your entries to separate lines.
//        HTTP request should end in a blank line (link). So add pw.println("");

    
       

    }

    public void doErrorThing() throws AppException, IOException {
        //"/restaurant/review/4/reviewid/333";
        ClientConfig config = new ClientConfig();

        Client client = ClientBuilder.newClient(config);

        client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
        client.property(ClientProperties.READ_TIMEOUT, 1000);
        client.property(ClientProperties.OUTBOUND_CONTENT_LENGTH_BUFFER, 1000);

        WebTarget target = client.target(getBaseURI());

        Response response
                = target.path("restaurant").path("review").path("1").path("reviewid").path("-500").request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);

        int resStat = response.getStatus();
        String res = response.readEntity(String.class);
        LOG.debug(res);
        ObjectMapper myreader = new ObjectMapper();
        if (resStat != 200) {
            LOG.error("status problem for request ");
            AppException aa = myreader.readValue(res, AppException.class);

            throw aa;
        } else {
            LOG.debug("response  was 200");
            Reviews r = myreader.readValue(res, Reviews.class);
            LOG.debug(r.getReviewlisting());

        }

    }

    public void doSimpleExample() throws IOException {

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());

        Response response = target.path("restaurant").path("4").request()
                .accept(MediaType.APPLICATION_JSON).get(Response.class);

        if (response.getStatus() != 200) {
            LOG.error("status problem for request");
        } else {
            LOG.debug("response " + response.getEntity().getClass().getName());
        }
        String res = response.readEntity(String.class);
        LOG.debug(res);

        ObjectMapper myreader = new ObjectMapper();

        JsonNode restaurantObject = myreader.readTree(res);
        LOG.debug(restaurantObject.get("name"));

        Restaurant r = myreader.readValue(res, Restaurant.class);

        LOG.debug(r.getReviewCollection().size());

    }

    private static URI getBaseURI() {

        // return UriBuilder.fromUri("http://donhenton-springmvc3.herokuapp.com/app/backbone").build();
        return UriBuilder.fromUri("http://localhost:8090/jersey-sandbox/webapi").build();
    }

    public static void main(String[] args) {
         try {
        (new JerseyClientExplorer()).doTimeout();
         } catch (Exception err) {

             LOG.error("main error " + err.getClass().getName() + " " + err.getMessage());
         }
    }

}
