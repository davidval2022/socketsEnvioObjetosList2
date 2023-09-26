/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Empleado;
import model.Usuarios;

/**
 *
 * @author cgsen
 */
public class Conexion {
    Connection conn = null;
    private String user = "admin";
    private String password = "admin";
    private String url = "jdbc:postgresql://localhost:5432/hrentrada";
    
    

    public  Connection getconexion() throws SQLException {
        String user = "admin";
        String password = "admin";
        String url = "jdbc:postgresql://localhost:5432/hrentrada";      
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }
    
    
    public Conexion(){
        try {      
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public Conexion(String user,String password,String  url) {
        try {      
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
    //metodo 1 obtener a los Usuarios con acceso al programa
    public ArrayList<Usuarios> getUsuarios() throws SQLException {

 
        Statement stmt;
        ResultSet rs;
        ArrayList<Usuarios> listaUsuarios = new ArrayList<>();

        stmt = conn.createStatement();
        rs = stmt.executeQuery("select * from users");

        while (rs.next()){
            Usuarios user = new Usuarios();
            user.setLogin(rs.getString("login"));
            user.setPass(rs.getString("pass"));
            user.setNumtipe(rs.getInt("numtipe"));
            user.setDni(rs.getString("dni"));
            listaUsuarios.add(user);
            
        }
        return listaUsuarios;
    }

    public ArrayList<Usuarios> getUsuarioLogin(String login) throws SQLException {

 
        ArrayList<Usuarios> listaUsuarios = new ArrayList<>();
        String consulta = "SELECT * FROM users where login = ?" ;
        PreparedStatement preparedStatement = conn.prepareStatement(consulta);
        //paso 4 asignar valores a los parámetros de la consulta
        preparedStatement.setString(1, login);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()){
            Usuarios user = new Usuarios();
            user.setLogin(rs.getString("login"));
            user.setPass(rs.getString("pass"));
            user.setNumtipe(rs.getInt("numtipe"));
            user.setDni(rs.getString("dni"));
            listaUsuarios.add(user);
            
        }
        return listaUsuarios;
    }
    
    public Usuarios comprobarCredencialesBD(String nombre, String pass){//devuelve el dni
        Usuarios user = null;
        String dni="-1";
        String numtipe = "-1";
        try {
         
            String consulta = "SELECT * FROM users where login = ? and  pass =?" ;
            PreparedStatement preparedStatement = conn.prepareStatement(consulta);
            //paso 4 asignar valores a los parámetros de la consulta
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, pass);
            //paso 5 ejecutar la consulta
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
                dni = resultSet.getString("dni"); 
                numtipe = resultSet.getString("numtipe");
                user = new Usuarios(nombre,pass,Integer.parseInt(numtipe),dni);
            }          
            preparedStatement.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;                 
    }
    
    //metodo 1 obtener todos los Usuarios con acceso al programa
    public ArrayList<Empleado> getEmpleados() throws SQLException {

 
        Statement stmt;
        ResultSet rs;
        ArrayList<Empleado> listaUsuarios = new ArrayList<>();

        stmt = conn.createStatement();
        rs = stmt.executeQuery("select * from empleados");

        while (rs.next()){
            Empleado user = new Empleado();
            user.setDni(rs.getString("dni"));
            user.setNom(rs.getString("nom"));
            user.setApellidos(rs.getString("apellido"));
            user.setNomEmpresa(rs.getString("nomempresa"));
            user.setApellidos(rs.getString("apellido"));
            user.setNomEmpresa(rs.getString("nomempresa"));
            user.setDepartament(rs.getString("departament"));
            user.setCodiCard(rs.getInt("codicard"));
            user.setMail(rs.getString("mail"));
            user.setTelefono(rs.getInt("telephon"));
            
            listaUsuarios.add(user);
            
        }
        return listaUsuarios;
    } 
    
    public ArrayList<Empleado> getEmpleadosNombre(String nombre) throws SQLException {

        ArrayList<Empleado> listaUsuarios = new ArrayList<>();
        String consulta = "SELECT * FROM empleados where nom = ?" ;
        PreparedStatement preparedStatement = conn.prepareStatement(consulta);
        //paso 4 asignar valores a los parámetros de la consulta
        preparedStatement.setString(1, nombre);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()){
            Empleado user = new Empleado();
            user.setDni(rs.getString("dni"));
            user.setNom(rs.getString("nom"));
            user.setApellidos(rs.getString("apellido"));
            user.setNomEmpresa(rs.getString("nomempresa"));
            user.setApellidos(rs.getString("apellido"));
            user.setNomEmpresa(rs.getString("nomempresa"));
            user.setDepartament(rs.getString("departament"));
            user.setCodiCard(rs.getInt("codicard"));
            user.setMail(rs.getString("mail"));
            user.setTelefono(rs.getInt("telephon"));
            
            listaUsuarios.add(user);
            
        }
        return listaUsuarios;
    } 
    public ArrayList<Empleado> getEmpleadosDNI(String dni) throws SQLException {

        ArrayList<Empleado> listaUsuarios = new ArrayList<>();
        String consulta = "SELECT * FROM empleados where dni = ?" ;
        PreparedStatement preparedStatement = conn.prepareStatement(consulta);
        //paso 4 asignar valores a los parámetros de la consulta
        preparedStatement.setString(1, dni);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()){
            Empleado user = new Empleado();
            user.setDni(rs.getString("dni"));
            user.setNom(rs.getString("nom"));
            user.setApellidos(rs.getString("apellido"));
            user.setNomEmpresa(rs.getString("nomempresa"));
            user.setApellidos(rs.getString("apellido"));
            user.setNomEmpresa(rs.getString("nomempresa"));
            user.setDepartament(rs.getString("departament"));
            user.setCodiCard(rs.getInt("codicard"));
            user.setMail(rs.getString("mail"));
            user.setTelefono(rs.getInt("telephon"));
            
            listaUsuarios.add(user);
            
        }
        return listaUsuarios;
    } 
    
}
