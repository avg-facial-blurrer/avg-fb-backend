package nl.oose.blackpool.Controllers;


import nl.oose.blackpool.DTO.AllChildrenDTO;
import nl.oose.blackpool.DTO.ChildDTO;
import nl.oose.blackpool.Exceptions.DataException;
import org.apache.commons.httpclient.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Child")
public class ChildController {
    @Inject
    IChildService childService;

    @Path("/get/{id}")
    @GET
    public Response getChild(@PathParam("id") int id) {
        try {
            return Response.ok(childService.getChild(id)).build();
        } catch (DataException dataException) {
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

    @Path("/getAll")
    @GET
    public Response getAllChildren(){
        try {
            return Response.ok(new AllChildrenDTO(childService.getAllChildren())).build();
        } catch (DataException e) {
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addChild")
    @POST
    public Response addChild(ChildDTO childToAdd) {
        try {
            childService.addChild(childToAdd);
            return Response.status(HttpStatus.SC_CREATED).build();
        } catch (DataException e) {
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateChild")
    @PUT
    public Response updateChild(ChildDTO childToUpdate) {
        try {
            childService.updateChild(childToUpdate);
            return Response.status(HttpStatus.SC_OK).build();
        } catch (DataException e) {
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/DeleteChild/{id}")
    @DELETE
    public Response deleteChild(@PathParam("id") int id) {
        try {
            childService.deleteChild(id);
            return Response.status(HttpStatus.SC_OK).build();
        } catch (DataException e) {
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }
}
