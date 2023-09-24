/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author david
 */
//import main.java.ioc.dam.m9.uf3.eac2.b2.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Empleado;
import model.Persona;
import model.Personal;
import model.User;

/**
 *
 * 
 */
public class ThreadClient extends Thread {
    private Socket client;
    private Scanner in;
    private PrintWriter out;
    
    

    public ThreadClient(Socket client) {
        try {
            this.client = client;
            this.in = new Scanner(client.getInputStream());
            this.out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }
    

    
    @Override
    public void run() {
        String msg;
        Personal personal = new Personal();
        List<Empleado> listaPersonas = new ArrayList<Empleado>();
        List<User> listaUsers = new ArrayList<User>();
        String codigo = "0";
        
        boolean salir = false;
        try {
            
            //IMPLEMENTA
            BufferedWriter escriptor = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader lector = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            //NUEVO OBJETO
            // Se prepara un flujo de salida para objetos
            //ObjectOutputStream outObjeto = new ObjectOutputStream( client2.getOutputStream());
            ObjectOutputStream outObjeto;
            
            //enviamos al cliente la pregunta y el mensaje de bienvenida
            msg = "Bienvenido, inicia sesion! Finaliza con Exit";
            escriptor.write(msg);//enviamos
            escriptor.newLine();
            escriptor.flush();
            //leemos la respuesta con el login y pass con este formato login:pass
            String palabra = lector.readLine(); //recibimos
            if(palabra.equalsIgnoreCase("exit")){
                System.out.println("Ciente desconectado");
                salir = true;
                escriptor.close();
                //outObjeto.close();
                lector.close();
                client.close();

            }else{
                System.out.println("Datos enviados: "+palabra);  
                String[] datos = new String[2];
                String login = "";
                String pass = "";
                datos = palabra.split(":");
                login = datos[0];
                pass = datos[1];
                User u = new User();
                codigo = personal.InicioSesion(login, pass);
                if(codigo.equalsIgnoreCase("0")){
                    msg = codigo+ "0 - Hay un error en el login.. desconectado";
                    System.out.println("Ciente desconectado, error en el login");
                    escriptor.write(msg);//enviamos
                    escriptor.newLine();
                    escriptor.flush();                    
                    lector.close();
                    client.close();
                }else{        
                    //enviamos al cliente el codigo y luego ya vamos con que nos envie que quiere buscar (Empleados)
                    msg = codigo;
                    escriptor.write(msg);//enviamos
                    escriptor.newLine();
                    escriptor.flush();
                    while(!salir){

                        //leemos la respuesta con la palabra a buscar
                        palabra = lector.readLine(); //recibimos
                        if(palabra.equalsIgnoreCase("exit")){
                            System.out.println("Ciente desconectado");
                            salir = true;
                            escriptor.close();
                            //outObjeto.close();
                            lector.close();
                            client.close();

                        }else{
                            System.out.println("Empleado  a enviar: "+palabra);   
                            if(palabra.equalsIgnoreCase("todos")){
                                listaPersonas = personal.todo();
                            }else{
                                listaPersonas = personal.buscar(palabra);
                            }


                            //devolvemos la respueta
                            outObjeto = new ObjectOutputStream( client.getOutputStream());
                            outObjeto.writeObject(listaPersonas);
                            //escriptor.write(msg);
                            //escriptor.newLine();
                            //escriptor.flush();
                        }
                    }
                }
                
                
               
            }           
            
            
            
            
            
            

            
            
            
        } catch (IOException ex) {
            Logger.getLogger(ThreadClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}