/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author david
 */
public class Consulta {
    String codigo;
    String tabla;
    String columna;
    String[] palabras;
    String orden;
    
    public Consulta() {
    }

    public Consulta(String codigo, String tabla, String columna, String[] palabras, String orden) {
        this.codigo = codigo;
        this.tabla = tabla;
        this.columna = columna;
        this.palabras = palabras;
        this.orden = orden;
    }

    public Consulta(String codigo, String tabla, String columna, String[] palabras) {
        this.codigo = codigo;
        this.tabla = tabla;
        this.columna = columna;
        this.palabras = palabras;
    }

    public Consulta(String codigo, String tabla, String columna) {
        this.codigo = codigo;
        this.tabla = tabla;
        this.columna = columna;
    }

    public Consulta(String codigo, String tabla) {
        this.codigo = codigo;
        this.tabla = tabla;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String[] getPalabras() {
        return palabras;
    }

    public void setPalabras(String[] palabras) {
        this.palabras = palabras;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }




    
    
    
    
    
}
