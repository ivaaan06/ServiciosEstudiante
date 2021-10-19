/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicundi.iyepitia.crudservicios.service;

import com.unicundi.iyepitia.crudservicios.controller.EstudianteController;
import com.unicundi.iyepitia.crudservicios.dto.EstudianteDto;
import com.unicundi.iyepitia.crudservicios.exception.BussinessException;
import com.unicundi.iyepitia.crudservicios.exception.ExceptionWrraper;
import com.unicundi.iyepitia.crudservicios.exception.LongitudException;
import com.unicundi.iyepitia.crudservicios.exception.ObjectException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.IIOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author Ivan Espitia
 */
public class Requerimientos {
        
    
    private static final String archivo="C:\\\\Users\\\\Ivan espitia\\\\Desktop\\\\UDEC\\\\Noveno Semestre II\\\\Linea II\\\\Trabajos\\\\CrudServicios\\\\estudiantes2.txt";
    
    
    
    
    public EstudianteDto ValidarCaracteresCedula(String cedula,List<EstudianteDto> listaEstudiantes,String url) throws Exception,BussinessException{
       
        boolean encontrado = false;
        EstudianteDto buscar = new EstudianteDto();
        try {
            boolean isNum = cedula.matches("[+-]?\\d*(\\.\\d+)?");
            if (isNum == true) {
                for (EstudianteDto est : listaEstudiantes) {
                    if (est.getCedula().equals(cedula)) {
                        buscar = est;
                        encontrado = true;
                    }
                }
            } else {
                throw new BussinessException("La cedula no puede contener caracteres solo numeros",url);
                       
            }
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

        
       
        return  buscar;
    }
    public void validarEstudiante(EstudianteDto estudiante,String url) throws ObjectException {
        boolean isNum = estudiante.getCedula().matches("[+-]?\\d*(\\.\\d+)?");
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(estudiante.getCorreo());
        try {
            //cedula
            if (isNum == false) {
                throw new ObjectException("La cedula no puede contener caracteres solo numeros",url);
            }
            if (estudiante.getCedula().length() < 5) {
                throw new ObjectException("Minimo 5 digitos para cedula",url);
            } else if (estudiante.getCedula().length() > 10) {
                throw new ObjectException("Maximo 10 digitos para la cedula",url);
            } else if (estudiante.getCedula().isEmpty()) {
                throw new ObjectException("La cedula no puede estar vacia",url);
            }
            //nombre
            if (estudiante.getNombre().length() < 1) {
                throw new ObjectException("Minimo 1 letra para el nombre",url);
            } else if (estudiante.getNombre().length() > 15) {
                throw new ObjectException("Maximo 15 letras para el nombre",url);
            } else if (estudiante.getNombre().isEmpty()) {
                throw new ObjectException("El nombre no puede estar vacia",url);
            }
            //apellido
            if (estudiante.getApellido().length() < 1) {
                throw new ObjectException("Minimo 1 letra para el apellido",url);
            } else if (estudiante.getApellido().length() > 15) {
                throw new ObjectException("Maximo 15 letras para el apellido",url);
            } else if (estudiante.getApellido().isEmpty()) {
                throw new ObjectException("El apellido no puede estar vacia",url);
            }
            //edad
            if (estudiante.getEdad() < 18 || estudiante.getEdad() > 100) {
                throw new ObjectException("La edad minima es de 18 y no se aceptan edades por encima de los 100",url);
            }
            //email
            if (mather.find() != true) {
                throw new ObjectException("El correo ingresado no es valido por favor verifique",url);
            } 
            //lista materias
            if(estudiante.getListaMateria().size()==0){
                throw new ObjectException("La lista de materias no puede estar vacia",url);
            }
            //numero
            if (estudiante.getNumero() == null) {
                throw new ObjectException("El numero de materias no puede estar vacia",url);
            }
            
        } catch (ObjectException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    public void ValidarCaractEliminnar(String cedula,String url) throws Exception,BussinessException{
       
        boolean encontrado = false;
        EstudianteDto buscar = new EstudianteDto();
        try {
            boolean isNum = cedula.matches("[+-]?\\d*(\\.\\d+)?");
            if (isNum == true) {
              encontrado=true;
            } else {
                throw new BussinessException("La cedula no puede contener caracteres solo numeros",url);
            }
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
    public void ValidarLongitudCedula(String cedula,String url) throws LongitudException, Exception{
        try {
            if (cedula.length() < 5) {
                throw new LongitudException("Minimo 5 digitos para cedula",url);
            } else if (cedula.length() > 10) {
                throw new LongitudException("Maximo 10 digitos para la cedula",url);
            } else if(cedula.isEmpty()){
                throw new LongitudException("La cedula no puede estar vacia",url);
            }
        } catch (LongitudException e) {
            e.printStackTrace();
            throw e;
        } catch(Exception e){
            e.printStackTrace();
            throw new Exception("");
        }
    }
    
    /**
     * Metodo que nos permite leer el contenido del fichero de estudiante.txt
     * @return listaEstudiante
     */        
    public List<EstudianteDto> leerArchivo() throws IOException, ClassNotFoundException{
        List<EstudianteDto> listaEstudiantes = new ArrayList<EstudianteDto>();
         try {
             FileInputStream fis = new FileInputStream(archivo);
             ObjectInputStream ois = new ObjectInputStream(fis);
             while(true){
                 EstudianteDto est=(EstudianteDto) ois.readObject();
                 System.out.println(est.getNombre());
                 listaEstudiantes.add(est);
             }
         } catch (IOException io) {
             io.getStackTrace();              
         } catch (ClassNotFoundException ex) {
             ex.getStackTrace();
            throw ex;
         }
        return listaEstudiantes;
    }
    
    
     /**
     * Metodo que nos permite guarda contenido en el fichero de estudiante.txt
     * @param estudiante 
     * @throws IOException 
     */  
    public void guardarArchivo(EstudianteDto estudiante) throws IOException, ClassNotFoundException{
         List<EstudianteDto> listaEstudiantes = new ArrayList<EstudianteDto>();
        FileOutputStream file = null;
        listaEstudiantes = leerArchivo();
        try {
            file = new FileOutputStream(archivo);
            ObjectOutputStream mensajero = new ObjectOutputStream(file);

            if (listaEstudiantes == null) {
                mensajero.writeObject(estudiante);
            } else {
                listaEstudiantes.add(estudiante);
                for (EstudianteDto est : listaEstudiantes) {
                    mensajero.writeObject(est);

                }
            }

           
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException("");
        } finally {
           
                file.close();
            
        }
    }
    
     /**
     * Metodo que nos permite editar el contenido del fichero de estudiante.txt
     * @param estudinateedit
     * @return boolean
     */  
    public boolean editarArchivo(EstudianteDto estudinateedit) throws IOException, ClassNotFoundException{
        List<EstudianteDto> lista = new ArrayList<EstudianteDto>();
        lista = leerArchivo();
        boolean editado=false;
        FileOutputStream file = null;

        try {
            file = new FileOutputStream(archivo);
            ObjectOutputStream mensajero = new ObjectOutputStream(file);
            for (EstudianteDto est : lista) {
                if (est.getCedula().equals(estudinateedit.getCedula())) {
                    
                    est.setApellido(estudinateedit.getApellido());
                    est.setCorreo(estudinateedit.getCorreo());
                    est.setEdad(estudinateedit.getEdad());
                    est.setNombre(estudinateedit.getNombre());
                    est.setNumero(estudinateedit.getNumero());
                    est.setListaMateria(estudinateedit.getListaMateria());
                    editado=true;
                     mensajero.writeObject(est);
                } else {
                    mensajero.writeObject(est);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                throw ex;
            }
        }

        return editado;
    }
    
     /**
     * Metodo que nos permite reescribir el contenido del fichero de estudiante.txt
     * @param lista 
     * @param cedula
     * @return boolean
     */  
    public boolean reescribirArchivo(List<EstudianteDto> lista, String cedula) throws IOException{
     
        FileOutputStream file = null;
        boolean encontrado=false;
         try {
            file = new FileOutputStream(archivo);
            ObjectOutputStream mensajero = new ObjectOutputStream(file);
            for (EstudianteDto est : lista) {
                if (est.getCedula().equals(cedula)) {
                  System.out.println("Encontrado");
                  encontrado=true;
                }else{
                  mensajero.writeObject(est);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
               throw ex;
            }
        }

         
         return encontrado;

    }
    
    
    
  
                  
      
   }
   

