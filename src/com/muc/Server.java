/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.muc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dudadam1
 */
public class Server extends Thread {

    private static final Logger LOGGER = Logger.getLogger("com.muc.server");
        // The logger reads config from file logging.properties
        // set this using: -Djava.util.logging.config.file
    
    private final int serverPort;

    Server(int serverPort) {
        this.serverPort = serverPort;
    }
    
    @Override
    public void run(){
         try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while (true) {
                System.out.println("About to accept client connection...");
                /**
                 * Listens for a connection to be made to this socket and
                 * accepts it.
                 *
                 * accept() zwraca połączenie do klienta i wraca do nasłuchu w
                 * pętli. My pobieramy to połaczenie do zmiennej Declaration:
                 *
                 * public Socket accept() throws IOException
                 *
                 * The method blocks until a connection is made. A new Socket s
                 * is created and, if there is a security manager, the security
                 * manager's checkAccept method is called with
                 * s.getInetAddress().getHostAddress() and s.getPort() as its
                 * arguments to ensure the operation is allowed. This could
                 * result in a SecurityException. Throws: IOException - if an
                 * I/O error occurs when waiting for a connection.
                 * SecurityException - if a security manager exists and its
                 * checkAccept method doesn't allow the operation.
                 * SocketTimeoutException - if a timeout was previously set with
                 * setSoTimeout and the timeout has been reached.
                 * IllegalBlockingModeException - if this socket has an
                 * associated channel, the channel is in non-blocking mode, and
                 * there is no connection ready to be accepted.
                 */
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from : " + clientSocket);
                // Logger.getLogger(ServerMain.class.getName()).log(Level.INFO, "Accepted connection from : {0}", clientSocket.toString);
                if (LOGGER.isLoggable(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Accepted connection from : {0}", clientSocket.toString());
                }

                // New threads are handled by ServerWorker now
                // Leave the main thread free to accept consecutive calls.
                ServerWorker worker = new ServerWorker(clientSocket);
                // it can be started as it is a child of the Thread:
                worker.start();
            }
        } catch (IOException ex) {
            System.out.println("IO Error: " + ex.toString());
            // Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
