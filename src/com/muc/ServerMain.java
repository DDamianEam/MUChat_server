/*
 * Copyright (c) 2018, Damian Duda <damian.duda@gmail.com>
 *
 * Permission to use, copy, modify, and distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package com.muc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
 

/**
 * Simple chat server.
 * 
 * Multithread concurrent server version.
 * The client's requests are handled using worker class/object ext Thread.
 * Very simplistic.
 * NOT for production use - this is only a proof o concept.
 * 
 * TODO Single line logs
 * TODO Check static logs with dynamic workers.
 * TODO Multiplexing I/O.
 * version 0.3
 * 
 * Based on YouTube video by Jim Liao - JimOnDemand
 * (https://www.youtube.com/watch?v=cRfsUrU3RjE)
 * @author Damian Duda <damian.duda@gmail.com>
 */
public class ServerMain {
    
    public static void main(String[] args){
        int port = 8818;
        
        // Logger as in Oracle training
        // private static final Logger logger = Logger.getLogger("com.oracle.logdemo");
        // The logger reads config from file logging.properties
        // We can set logging outside the logging.properties
        //                logger.setLevel(Level.INFO);
        // And logging at last
        //        logger.log(Level.INFO, "Hello logger!");
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
            Logger.getLogger(ServerMain.class.getName()).log(Level.INFO, "Accepted connection from : {0}", clientSocket.toString());
            
            // New threads are handled by ServerWorker now
            // Leave the main thread free to accept consecutive calls.
            ServerWorker worker = new ServerWorker(clientSocket);
            // it can be started as it is a child of the Thread:
            worker.start();
            }
        } catch (IOException ex) {
            System.out.println("IO Error: " + ex.toString());
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
