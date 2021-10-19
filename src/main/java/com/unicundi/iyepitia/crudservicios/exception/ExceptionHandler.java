package com.unicundi.iyepitia.crudservicios.exception;



import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;




/**
 *
 * @author Ivan Espitia
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception ex) {
        ExceptionWrraper wrraper;

        if (ex instanceof BussinessException) {
            wrraper = new ExceptionWrraper("400", "BAD_REQUEST", ex.getMessage(), ((BussinessException) ex).getUrl());
            return Response.status(Response.Status.BAD_REQUEST).entity(wrraper).build();
        } else if (ex instanceof NotAllowedException) {
            System.out.println("Entro a la peticion");
            wrraper = new ExceptionWrraper("405", "METHOD_NOT_ALLOWED", "El tipo de metodo no es compatible con esta url", "CrudServicios/estudiantes");
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(wrraper).build();
        } else if (ex instanceof LongitudException) {
            wrraper = new ExceptionWrraper("404", "NOT FOUND", ex.getMessage(), ((LongitudException) ex).getUrl());
            return Response.status(Response.Status.NOT_FOUND).entity(wrraper).build();
        } else if (ex instanceof ObjectException) {
            wrraper = new ExceptionWrraper("400", "BAD_REQUEST", ex.getMessage(), ((ObjectException) ex).getUrl());
            return Response.status(Response.Status.BAD_REQUEST).entity(wrraper).build();
        } else {
            wrraper = new ExceptionWrraper("500", "INTERNAL_SERVER_ERROR", "", "/buscarEstudiante/{cedula}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(wrraper).build();
        }
     
    }

  

}
