/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicundi.iyepitia.crudservicios.exception;

/**
 *
 * @author Ivan Espitia
 */
public class ObjectException extends Exception{
    
    private String url;
    public ObjectException(String string) {
        super(string);
    }

    public ObjectException(String string,String url) {
        super(string);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

   
    

    
    
    
}
