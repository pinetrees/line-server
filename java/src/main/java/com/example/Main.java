package com.example;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URI;

import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Arrays;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";

    public static String file_name = "file.txt";

    public static int file_length = 0;

    public static String[] lines;

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example package
        final ResourceConfig rc = new ResourceConfig().packages("com.example");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static String[] readArray(String file) {
        file_length = 0;
        try {
            Scanner s1 = new Scanner(new File(file));
            while(s1.hasNext()) {
                file_length = file_length + 1;
                s1.nextLine();
            }

            String[] lines = new String[file_length];

            Scanner s2 = new Scanner(new File(file));
            for (int i = 0; i < file_length; i = i + 1) {
                lines[i] = s2.nextLine();
            }

            return lines;
        }
        catch (FileNotFoundException e){
        }

        return null;

    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        lines = readArray(file_name);
        //ReadFile file = new ReadFile(file_name);
        //int numberOfLines = readLines();
        //System.out.println(numberOfLines);
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

