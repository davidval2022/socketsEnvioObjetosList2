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
import model.Usuarios;
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
                //System.out.println(mensajeServer);//vemos el código
                if(mensajeServer.equalsIgnoreCase("-1")){
                    System.out.println("Codigo = -1 .El login es erroneo");//vemos el código
                    salir = true;
                    lector.close();
                    escriptor.close();
                    socket.close();
                    
                }else{
                    codigo = mensajeServer;
                    while(!salir){
            
                        System.out.println(mensajeServer);//vemos el código
                        //a partir de ahora ya enviamos el nombre del Empleado que queremos
                        System.out.println("El server a respondido ok y nos a asignado el código mostrado arriba)");//vemos el código
                        System.out.println("Habrá que enviar a partir de ahora una serie de codigos separados por ':')");//vemos el código
                        System.out.println("codigoDadoPorElServer|||nombreTabla|||nombreColumna|||Filtro|||Orden)");//vemos el código
                        System.out.println("nombreColumna,Filtro y Orden son optativos, pero hay que poner al menos un 0 en cada uno.");//vemos el código
                        if(codigo.charAt(0) == 'A'){
                            System.out.println("El server indica que eres administrador");
                        }else if(codigo.charAt(0) == 'U'){
                            System.out.println("El server indica que eres usuario normal");
                        }
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
                            String[] frase = new String[5];
                            frase = palabra.split(":");
                            String codigoUserRecibido = frase[0]; //el codigo recibido tiene que ser el mismo que le hemos asignado
                            String nombreTabla = frase[1]; //Será el numero de tabla. (ej: 1->empleados 2->users 3-jornada 4-usertipe 5->empresa)
                            String columna = frase[2]; //sera la palabra que busquemos(ej: juan,1234567D), si ponemos 0 sera todos los de la tabla
                            String palabraAbuscar = frase[3];// si es el caso será la columna (,dni,nom,etc), si no hay ponemos 0
                            String orden = frase[4];// si es el caso el orden, si no hay ponemos 0
                            System.out.println("codigoUserRecibido: "+codigoUserRecibido);
                            System.out.println("nombreTabla: "+nombreTabla); 
                            System.out.println("columna: "+columna); 
                            System.out.println("palabraAbuscar: "+palabraAbuscar); 
                            System.out.println("orden: "+orden); 
                            if(nombreTabla.equals("empleados")){
                                List<Empleado> listaPersonas = new ArrayList<>();
                                ObjectInputStream perEnt = new ObjectInputStream(socket.getInputStream());
                                listaPersonas = (ArrayList) perEnt.readObject();;
                                //recibo objeto
                                for(int i =0; i< listaPersonas.size();i++){
                                    System.out.println("Nombre: " + listaPersonas.get(i).getNom() + " Apellidos: " + listaPersonas.get(i).getApellidos() + " DNI: "+listaPersonas.get(i).getDni());                   

                                } 
                                
                            }else if(nombreTabla.equals("users")){
                                List<Usuarios> listaPersonas = new ArrayList<>();
                                ObjectInputStream perEnt = new ObjectInputStream(socket.getInputStream());
                                listaPersonas = (ArrayList) perEnt.readObject();;
                                //recibo objeto
                                for(int i =0; i< listaPersonas.size();i++){
                                    System.out.println("Nombre: " + listaPersonas.get(i).getLogin() + " Tipo de user: " + listaPersonas.get(i).getNumtipe() + " DNI: "+listaPersonas.get(i).getDni());                   

                                }                                 
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