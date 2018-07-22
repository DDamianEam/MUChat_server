/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.util.Duration.millis;

/**
 * Serwer prostego systemu "czat".
 * 
 * Wersja jednoprocesowa (tzw. serwer inkrementacyjny
 * 
 * version 0.1
 * @author Damian Duda <damian.duda@gmail.com>
 */
public class ServerMain {
    
    public static void main(String[] args){
        int port = 8818;
        try {
            ServerSocket serverSocket = new ServerSocket(port);            
            while(true) { 
                System.out.println("About to accept client connection...");
            /**
             * Listens for a connection to be made to this socket and accepts it.
             * 
             * accept() zwraca połączenie do klienta i wraca do nasłuchu w pętli.
             * My pobieramy to połaczenie do zmiennej
             * Declaration:
             * 
             * public Socket accept() throws IOException
             * 
             * The method blocks until a connection is made. 
             * A new Socket s is created and, if there is a security manager,
             * the security manager's checkAccept method is called with
             * s.getInetAddress().getHostAddress() and s.getPort()
             * as its arguments to ensure the operation is allowed. This could
             * result in a SecurityException.
             * Throws: 
             * IOException - if an I/O error occurs when waiting for
             * a connection.
             * SecurityException - if a security manager exists
             * and its checkAccept method doesn't allow the operation.
             * SocketTimeoutException - if a timeout was previously set with
             * setSoTimeout and the timeout has been reached.
             * IllegalBlockingModeException - if this socket has an associated
             * channel, the channel is in non-blocking mode, and there is no
             * connection ready to be accepted.
             */
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from : " + clientSocket);
            // teraz trzeba pobrać Output Stream
            OutputStream outputStream = clientSocket.getOutputStream();
            // możemy coś napisać do out stream:
            outputStream.write("Hello World\n".getBytes());
            // Żeby zobaczyć problem z "accept", tj. jednozadaniowość
            // wysyłamy przez 10 sek. aktualną datę.
            // Wtedy każdy nowy klient będzie oczekiwał na koniec obsługi
            for (int i=0; i<10; i++) {
                outputStream.write(("Time is now "+ new Date() + "\n").getBytes());
                    Thread.sleep(1000);
                        }
            clientSocket.close();
            }
        } catch (IOException ex) {
            System.out.println("IO Error: " + ex.toString());
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}