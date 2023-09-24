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
import java.io.Serializable;

@SuppressWarnings("serial")


public class User implements Serializable,Comparable<User>{
    private String login;
    private String pass;
    private int numTipe;
    private String dni;

    public User(String login, String pass, int numTipe, String dni) {
        this.login = login;
        this.pass = pass;
        this.numTipe = numTipe;
        this.dni = dni;
    }
    
    public User(){
        
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getNumTipe() {
        return numTipe;
    }

    public void setNumTipe(int numTipe) {
        this.numTipe = numTipe;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public int compareTo(User t) {
        String a = new String(String.valueOf(this.getLogin()));
        String b = new String(String.valueOf(t.getLogin()));
        return a.toLowerCase().compareTo(b.toLowerCase());
    }
    
    
    
    
}
