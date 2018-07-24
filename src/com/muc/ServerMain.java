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
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
 

/**
 * Serwer prostego systemu "czat".
 * 
 * Wersja wielowątkowa (multithread concurrent server).
 * 
 * The client's requests are handled using static method.
 * Very simplistic.
 * 
 * version 0.2
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
            
            // Create new thread and pass it the connection to client.
            // Leave the main thread free to accept consecutive calls.
            // This is example of extending the object from Thread class
            Thread t = new Thread() {
                
                /**
                 * Here we cover the run method from Thread class.
                 * 
                 * The thread lives completely in the run method.
                 * When handleClientSocket ends then thread also.
                 */
                @Override
                public void run() {
                    try {
                        handleClientSocket(clientSocket);
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            // Ponieważ to jest tak naprawdę deklaracja to ";" musi być
            };
            // tu dopiero startujemy nowy wątek
            t.start();
            }
        } catch (IOException ex) {
            System.out.println("IO Error: " + ex.toString());
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Do something with connected client.
     * 
     * For simplicity the static method.
     * 
     * @param clientSocket
     * Metoda do obsługi kolejnych żądań od klientów.
     * 
     * static na razie załatwia sprawę wątków. Wygląda na funkcję tread-safe
     * i re-entrant - nie korzysta ze zmiennych zewnętrznych i wywołuje Thread.sleep
     * który jest wątkowo-bezpieczny.
     * 
     * @param clientSocket Referencja do gniazda zwróconego przez accept()
     * @throws IOException
     * @throws InterruptedException 
     */
    private static void handleClientSocket(Socket clientSocket ) throws IOException, InterruptedException {
    OutputStream  outputStream = clientSocket.getOutputStream();
            // możemy coś napisać do out stream:
            // outputStream.write("Hello World\n".getBytes());
            // Żeby zobaczyć problem z "accept", tj. jednozadaniowość
            // wysyłamy przez 10 sek. aktualną datę.
            // Wtedy każdy nowy klient będzie oczekiwał na koniec obsługi
            for (int i=0; i<10; i++) {
                //FIXME W Windows CR+LF przesuwa kolejne linię o długość poprzedniej
                outputStream.write(("Time is now "+ new Date() + "\n").getBytes());
                    Thread.sleep(1000);
                        }
            clientSocket.close();
            }
    
}
