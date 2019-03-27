package nl.oose.blackpool.Controllers;


import nl.oose.blackpool.DTO.AllPermissionsDTO;
import nl.oose.blackpool.DTO.PermissionsDTO;
import nl.oose.blackpool.Exceptions.DataException;
import org.apache.commons.httpclient.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Permissions")
public class PermissionsController {
    @Inject
    private IPermissionsService permissionsService;

    @Path("/get/{id}")
    @GET
    public Response getPermissionsById(@PathParam("id") int id) {
        try {
            return Response.ok(permissionsService.getPermission(id)).build();
        } catch (DataException e) {
            e.printStackTrace();
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

    @Path("/getAll")
    @GET
    public Response getAllPermissions() {
        try {
            return Response.ok(new AllPermissionsDTO(permissionsService.getAllPermissions())).build();
        } catch (DataException e) {
            e.printStackTrace();
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/addPermissions")
    public Response addPermission(PermissionsDTO permissionsDTO) {
        try {
            permissionsService.addPermission(permissionsDTO);
            return Response.status(HttpStatus.SC_CREATED).build();
        } catch (DataException e) {
            e.printStackTrace();
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }


    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    @Path("/updatePermissions")
    public Response updatePermission(PermissionsDTO permissionsDTO) {
        try {
            permissionsService.updatePermission(permissionsDTO);
            return Response.ok().build();
        } catch (DataException e) {
            e.printStackTrace();
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    @Path("/deletePermission/{id}")
    public Response deletePermission(@PathParam("id") int id) {
        try {
            permissionsService.deletePermission(id);
            return Response.ok().build();
        } catch (DataException e) {
            e.printStackTrace();
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

}
