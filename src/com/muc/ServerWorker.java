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
