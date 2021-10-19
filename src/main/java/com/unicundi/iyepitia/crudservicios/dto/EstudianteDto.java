/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicundi.iyepitia.crudservicios.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.faces.validator.Validator;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Validation;
import javax.validation.constraints.*;
import javax.validation.Valid;


/**
 * Dto para informacion de estudiante
 * @author Ivan Espitia and Caren Rodriguez
 * @version 1.0
 * @since 12/08/2021
 */
public class EstudianteDto implements Serializable {
    
    @Id
    @NotEmpty (message = "La cedula no puede estar vacia ")
    @Size(min=5, max=10, message =  "Minimo 5 digitos maximo 15")
    private String cedula;
    
    @NotEmpty (message = "El nombre no puede estar vacia ")
    @Size(min=5, max=15, message ="Minimo 5 digitos maximo 15")
    private String nombre;
    
    @NotEmpty (message = "El apellido no puede estar vacia ")
    @Size(min=5, max=15, message ="Minimo 5 digitos maximo 15")
    private String apellido;
    
    @NotNull (message = "La edad no puede estar vacia ")
    @Min(18) @Max(100) 
    private Integer edad;
    
    @NotEmpty (message = "El correo no puede estar vacia ")
    @Size(min=5, max=35, message= "Minimo 5 digitos maximo 35")
    @Email
    private String correo;
    
    @NotNull
    private List<String> listaMateria;
    
    @NotNull
    private int[] numero;

    public EstudianteDto() {
    }

    public EstudianteDto(String cedula, String nombre, String apellido, Integer edad, String correo, List<String> listaMateria, int[] numero) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.listaMateria = listaMateria;
        this.numero = numero;
    }
    
    /**
     * Metodo que retorna la cedula del estudiante
     * @return cedula
     */
    public String getCedula() {
        return cedula;
    }
    /**
     * Metodo que retorna el numero de materias
     * @return [] numero
     */
     public int[] getNumero() {
        return numero;
    }
     /**
     * Metodo que retorna nombre del estudiante
     * @return nombre
     */
    
    public String getNombre() {
        return nombre;
    }
   /**
     * Metodo que retorna apellido del estudiante
     * @return apellido
     */
    public String getApellido() {
        return apellido;
    }
    /**
     * Metodo que retorna edad del estudiante
     * @return edad
     */
    public Integer getEdad() {
        return edad;
    }  
    /**
     * Metodo que retorna correo del estudiante
     * @return correo
     */
    public String getCorreo() {
        return correo;
    }  
    /**
     * Metodo que retorna lista del estudiante
     * @return lista
     */
    public List<String> getListaMateria() {
        return listaMateria;
    }
    /**
     * Metodo que asigna el nombre del estudiante
     * @paramt nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Metodo que asigna la cedula del estudiante
     * @paramt cedula
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    /**
     * Metodo que asigna la lista de materias del estudiante
     * @paramt listaMaterias
     */
    public void setListaMateria(List<String> listaMateria) {
        this.listaMateria = listaMateria;
    }
    /**
     * Metodo que asigna el apellido del estudiante
     * @paramt apelido
     */
     public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    /**
     * Metodo que asigna el numero de materias del estudiante
     * @paramt numeroDeMaterias
     */
    public void setNumero(int[] numero) {
        this.numero = numero;
    }
    /**
     * Metodo que asigna email del estudiante
     * @paramt email
     */
      public void setCorreo(String correo) {
        this.correo = correo;
    }
      /**
     * Metodo que asigna la edad del estudiante
     * @paramt edad
     */
      public void setEdad(Integer edad) {
        this.edad = edad; 
    }
  /* public Set<ConstraintViolation<EstudianteDto>> validarRestriccion(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
   }*/
 
}
