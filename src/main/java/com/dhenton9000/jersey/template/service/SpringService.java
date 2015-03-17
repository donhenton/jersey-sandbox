/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhenton9000.jersey.template.service;

import com.dhenton9000.jersey.template.model.TemplateModel;

/**
 *
 * @author dhenton
 */
public class SpringService {
    
    
    public TemplateModel getTemplateModel()
    {
        TemplateModel t = new TemplateModel();
        t.setAge(333);
        t.setName("bonzo");
         return t;
    }
    
}
