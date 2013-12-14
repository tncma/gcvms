package com.gcvms.services.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hi")
public class Helloworld {

    @GET
    @Path("/helloworld")
    @Consumes({ MediaType.TEXT_PLAIN })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response helloWorld(@PathParam("msg") String msg){
    
    		String status = "hello world! " + msg;
    		
        return Response.status(200).entity(status).build();
    }
}