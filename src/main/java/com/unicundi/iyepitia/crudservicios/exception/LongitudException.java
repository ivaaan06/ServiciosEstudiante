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
public class LongitudException extends Exception{
    private String url;
    public LongitudException(String string) {
        super(string);
    }

    public LongitudException(String url, String string) {
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
