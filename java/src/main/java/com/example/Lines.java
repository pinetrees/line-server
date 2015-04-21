package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("lines")
public class Lines {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("{index}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response lineIndex(@PathParam("index") String index) {
        int i = Integer.parseInt(index);
        String output;
        int status;
        if (i >= Main.file_length) {
            output = "Out of range";
            status = 413;
        } else {
            output = Main.lines[i];
            status = 200;
        }
        return Response.status(status).entity(output).build();
    }

}
