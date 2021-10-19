/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicundi.iyepitia.crudservicios.controller;

import com.unicundi.iyepitia.crudservicios.dto.EstudianteDto;
import com.unicundi.iyepitia.crudservicios.exception.BussinessException;
import com.unicundi.iyepitia.crudservicios.exception.ExceptionWrraper;
import com.unicundi.iyepitia.crudservicios.exception.LongitudException;
import com.unicundi.iyepitia.crudservicios.exception.ObjectException;
import com.unicundi.iyepitia.crudservicios.service.Requerimientos;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.faces.validator.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;


/**
 * Clase que reliza las operacion de CRUD con el fichero y crea los servicios
 * REST
 *
 * @author Ivan Espitia
 * @author Caren rodriguez
 * @version 1.0
 * @since 18/09/2021
 */
@Stateless
@Path("/estudiantes")
@WebServlet("/estudiantes")
public class EstudianteController {

    private Requerimientos requerimientos = new Requerimientos();

    /**
     * /**
     * Metodo que consulta toda la lista de estudiantes
     *
     * @param request
     * @return Response status
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Path("/consultarEstudiante")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response consultarEstudiantes(@Context HttpServletRequest request)throws  IOException{
        
            System.out.println(request.getRequestURI());
            List<EstudianteDto> listaEstudiantes = new ArrayList<EstudianteDto>();
            listaEstudiantes = requerimientos.leerArchivo();
            return Response.status(Response.Status.OK).entity(listaEstudiantes).build();
        
          
    }

/**
     * Metodo que busca al estudiante atravez de su numero de cedula
     *
     * @param cedula
     * @return Response status
     */
    @Path("/buscarEstudiante/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response buscarEstudiante(
                    @PathParam("cedula") String cedula)
            throws IOException, BussinessException, Exception,LongitudException {
        
       
        requerimientos.ValidarLongitudCedula(cedula,"/buscarEstudiante/{cedula}");
        List<EstudianteDto> listaEstudiantes = new ArrayList<EstudianteDto>();
        listaEstudiantes = requerimientos.leerArchivo();
        EstudianteDto buscar = requerimientos.ValidarCaracteresCedula(cedula, listaEstudiantes,"/buscarEstudiante/{cedula}");
        if (cedula.toString().equals(buscar.getCedula())) {
            return Response.status(Response.Status.OK).entity(buscar).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).entity("Cedula no encontrado, por favor verifique").build();
        }

    }

    /**
     * Metodo que inserta un objeto estudiante en el fichero
     *
     * @param estudiante
     * @return Response status
     * @throws IOException
     */
    @Path("/insertarEstudiante")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertarEstudiantes(EstudianteDto estudiante) throws IOException, ObjectException {
        requerimientos.validarEstudiante(estudiante,"/insertarEstudiante");
        
        try {
            List<EstudianteDto> lista = requerimientos.leerArchivo();
            for (EstudianteDto est : lista) {
                if (est.getCedula().equals(estudiante.getCedula())) {
                    return Response.status(Response.Status.CONFLICT).entity("Cedula ya registrada : " + estudiante.getCedula()).build();
                }
            }
            requerimientos.guardarArchivo(estudiante);
            return Response.status(Response.Status.CREATED).entity(estudiante).build();

        } catch (IOException e) {
            e.printStackTrace();
            ExceptionWrraper wrraper = new ExceptionWrraper("500", "INTERNAL_SERVER_ERROR", "No se guardo correctamente el estudiante en el archivo", "/insertarEstudiante");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(wrraper).build();
        }
    }

    /**
     * Metodo que elimina a un estudiante del fichero atravez de su numero de
     * cedula
     *
     * @param cedula
     * @return Response status
     */
    @Path("/elminarEstudiante/{cedula}")
    @DELETE
    public Response eliminarEstudiante(
            @PathParam("cedula") String cedula) 
            throws Exception, BussinessException {
       
        List<EstudianteDto> listaEstudiantes = new ArrayList<EstudianteDto>();
        boolean eliminar = false;
        boolean encontrado = false;
        try {
            requerimientos.ValidarCaractEliminnar(cedula,"/elminarEstudiante/{cedula}");
            listaEstudiantes = requerimientos.leerArchivo();
            eliminar = requerimientos.reescribirArchivo(listaEstudiantes, cedula);
            if (eliminar == true) {
                return Response.status(Response.Status.OK).entity("Eliminado Correctamente").build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).entity("No encontrado").build();
            }
        } catch (IOException ex) {
            ExceptionWrraper wrraper = new ExceptionWrraper("500", "INTERNAL_SERVER_ERROR", "", "/elminarEstudiante/{cedula}");
                return Response.status(Response.Status.NO_CONTENT).entity(wrraper).build();
        }

    }

    /**
     * Metodo que edita a un estudiante atravez de su numero de cedula
     *
     * @return Response status
     * @param estudiante
     */
    @Path("/editarEstudiante")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editarEstudiante(EstudianteDto estudiante) throws ObjectException {
        boolean editado = false;
        requerimientos.validarEstudiante(estudiante,"/editarEstudiante");
        try {
            editado = requerimientos.editarArchivo(estudiante);
            if (editado == true) {
                return Response.status(Response.Status.OK).entity("Editado Correctamente").build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).entity("No encontrado").build();
            }
        } catch (IOException ex) {

            ExceptionWrraper wrraper = new ExceptionWrraper("500", "INTERNAL_SERVER_ERROR", "", "/editarEstudiante");
                return Response.status(Response.Status.NO_CONTENT).entity(wrraper).build();
        }

    }

}
