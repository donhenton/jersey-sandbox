/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.config;

import com.wordnik.swagger.jersey.config.JerseyJaxrsConfig;
import com.wordnik.swagger.models.Contact;
import com.wordnik.swagger.models.Info;
import com.wordnik.swagger.models.License;
import com.wordnik.swagger.models.Swagger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author dhenton
 */

public class SwaggerConfigServlet extends JerseyJaxrsConfig {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        Info info = new Info()
                .title("Swagger Sample App")
                .description("This is a sample server Petstore server.  You can find out more about Swagger "
                        + "at <a href=\"http://swagger.io\">http://swagger.io</a> or on irc.freenode.net, #swagger.  For this sample, "
                        + "you can use the api key \"special-key\" to test the authorization filters")
                .termsOfService("http://helloreverb.com/terms/")
                .contact(new Contact()
                        .email("apiteam@swagger.io"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

        ServletContext context = servletConfig.getServletContext();
        Swagger swagger = new Swagger().info(info);
//        swagger.securityDefinition("api_key", new ApiKeyAuthDefinition("api_key", In.HEADER));
//        swagger.securityDefinition("petstore_auth",
//                new OAuth2Definition()
//                .implicit("http://petstore.swagger.io/api/oauth/dialog")
//                .scope("read:pets", "read your pets")
//                .scope("write:pets", "modify pets in your account"));
        context.setAttribute("swagger", swagger);

    }

}
