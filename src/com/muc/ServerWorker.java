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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class takes the handling of client requests off the main server class.
 * 
 * The main thread is thus free to accept new requests.
 * 
 * @author Damian Duda <damian.duda@gmail.com>
 */
public class ServerWorker extends Thread {

    private final Socket clientSocket;
    
    public ServerWorker(Socket clientSocket){
        this.clientSocket = clientSocket;
    }
    
    /**
     * Every thread has a run method...
     */
    @Override
    public void run(){
        try {
            handleClientSocket();
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
           /**
     * Do something with connected client.
     * 
     * It doesn't be static.
     * 
     * @param clientSocket Reference to socket given by accept()
     * @throws IOException
     * @throws InterruptedException 
     */
    private void handleClientSocket() throws IOException, InterruptedException {
        
        // We have socket here, so can talk with client bi-directionally
        /* public InputStream getInputStream() throws IOException
         * Returns an input stream for this socket.
         * public OutputStream getOutputStream() throws IOException
         * Returns an output stream for this socket.
         */
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        
        // BufferedReader for reading line-by-line
        // And InpuStreamReader which creates an InputStreamReader that uses
        // the default charset.
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        
        String line;
        // read line and check the result of statement
        while((line = reader.readLine()) != null){
            if("quit".equalsIgnoreCase(line)){
                break;
            }
            // send "echo"
            String msg = "You typed: " + line + "\n";
            outputStream.write(msg.getBytes());
        }
        
        clientSocket.close();
    }
    
}
