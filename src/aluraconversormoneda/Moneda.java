/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aluraconversormoneda;

/**
 *
 * @author Paul
 */
public class Moneda {
    private String simbolo;
    private String descripción;
    
    public void setSimbolo(String simbolo){
        this.simbolo = simbolo;
    }
    
    public String getSimbolo(){
        return this.simbolo;
    }
       
    public void setDescripcion(String descripcion){
        this.descripción = descripcion;
    }
    
    public String getDescripcion(){
        return this.descripción;
    }
    
}
