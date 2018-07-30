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
 * Does not broadcast message with logged-in users to new user
 * TODO (Done) Check static logs with dynamic workers.
 * TODO (In progress) Multiplexing I/O.
 * TODO Convert project to Maven.
 * version 0.3
 * 
 * Based on YouTube video by Jim Liao - JimOnDemand
 * (https://www.youtube.com/watch?v=cRfsUrU3RjE)
 * Part 1, Done
 * Part 2, 7:18
 * @author Damian Duda <damian.duda@gmail.com>
 */
public class ServerMain {
    
    // Logger as in skill training
        private static final Logger LOGGER = Logger.getLogger("com.muc.servermain");
        // The logger reads config from file logging.properties
        // set this using: -Djava.util.logging.config.file
        
    
    public static void main(String[] args){
        int port = 8818;
        
        // We need access to all current connections, so new instance is needed
        Server server = new Server(port);
        
        server.start();  // this starts thread and also "run" method
        
        // We can set logging outside the logging.properties
        LOGGER.setLevel(Level.INFO);
        // And logging at last
        LOGGER.log(Level.INFO, "MUChat started...");
        
        // the try loop was moved to Server.java
    }
}
