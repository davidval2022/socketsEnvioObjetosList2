/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 *
 * @author tomas
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Empleado;
//import model.Persona;


public class MainClient {
    public static void main(String[] args) throws ClassNotFoundException {
        boolean salir = false;
        try {
            //IMPLEMENTA
            Socket socket = new Socket("localhost", 8888);
            Scanner lectorPalabra = new Scanner(System.in);
            BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));//flujo lectura del server
            BufferedWriter escriptor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));//flujo envio al server
            String codigo = "0";
            //proceso de login
            String mensajeServer = lector.readLine();   //leemos el mensaje de bienvenidoa del server        
            System.out.println(mensajeServer);//en el mensaje nos pide el login y pass
            ///escribimos el login y pass///
            //lo escribimos primero el login separmos con : y luego el pass (luego, en los clientes gráficos los enviaremos igual)
            String palabra = lectorPalabra.next();
            lectorPalabra.nextLine();
            //ahora escribimos en servidor , enviandole la palabra a buscar 
            escriptor.write(palabra);
            escriptor.newLine();
            escriptor.flush();
            if(palabra.equalsIgnoreCase("exit")){ 
                salir = true;
                lector.close();
                escriptor.close();
                socket.close();
            }else{
                //leemos la respuesta, nos enviará un codigo 
                mensajeServer = lector.readLine();   //leemos ya la respuesta del server,    nos envia un código     
                System.out.println(mensajeServer);//vemos el código
                if(mensajeServer.equalsIgnoreCase("00 - Hay un error en el login.. desconectado")){
                    System.out.println("El login es erroneo");//vemos el código
                    salir = true;
                    lector.close();
                    escriptor.close();
                    socket.close();
                    
                }else{
                    codigo = mensajeServer;
                    while(!salir){
                        //mensajeServer = lector.readLine();   //leemos ya la respuesta del server,    nos envia un código     
                        //System.out.println(mensajeServer);//vemos el código
                        //a partir de ahora ya enviamos el nombre del Empleado que queremos
                        System.out.println("Ahora escribe el nombre del Empleado a buscar (NOTA: este mensaje es del cliente, no esta escrito por el server.. es por diferenciar.\n"
                                + "El último mensaje del server ha sido el código.. pero eso significa que todo está correcto )");//vemos el código
                        palabra = lectorPalabra.next();
                        lectorPalabra.nextLine();
                        //ahora escribimos en servidor , enviandole la palabra a buscar 
                        escriptor.write(palabra);
                        escriptor.newLine();
                        escriptor.flush();
                        if(palabra.equalsIgnoreCase("exit")){ 
                            salir = true;
                            lector.close();
                            escriptor.close();
                            socket.close();
                        }else{
                            List<Empleado> listaPersonas = new ArrayList<>();
                            ObjectInputStream perEnt = new ObjectInputStream(socket.getInputStream());
                            listaPersonas = (ArrayList) perEnt.readObject();;
                            //recibo objeto
                            for(int i =0; i< listaPersonas.size();i++){
                                System.out.println("Nombre: " + listaPersonas.get(i).getNom() + " Apellidos: " + listaPersonas.get(i).getApellidos() + " DNI: "+listaPersonas.get(i).getDni());                   

                            }                                     
                        }
                    }  
                }
                     
            }            
            
            socket.close();

            
        } catch (UnknownHostException ex) {
            Logger.getLogger(MainClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //creo este metodo para formatear la frase y que no esté en una sola linea
    public static String formatearMensaje(String mensaje) {
        /*ej de frase:
        "Català: poma - fruit del tipus pom de la pomera Espanyol: manzana - fruto comestible del manzano ...etc
        y la idea es que la frase quede así, con uns salto de linea por frase:
        Català: poma - fruit del tipus pom de la pomera
        Espanyol: manzana - fruto comestible del manzano
        ..etc
        */
        String m = "";//cadena de retorno
        String[] cadena = mensaje.split(":");//creo el array con la division
        String inicioFrase="\n";
        
        for(int i=0;i<cadena.length;i++){
            cadena[i]=cadena[i]+":";//como le hemos quitado antes los : se los volvemos a poner
            String[] div =cadena[i].split(" "); //ahora dividemos cada divison anterior por espacios           
            for(int j=0;j<div.length;j++){               
                if(div[j].endsWith(":")){//si acaba en : es que es el principio de una frase
                   //System.out.println(inicioFrase);
                   m = m+inicioFrase+"\n";//añadimos la frase al string de retorno.. esto ha de venir aquí.. justo aquí
                   inicioFrase = div[j];//es el comienzo de la frase
                }else{
                    inicioFrase = inicioFrase+" " +div[j];//al comienzo de la frase le sumamos los siguiente hasta el siguiente comienzo
                }                              
            }          
        }  
        return m;
    }
}