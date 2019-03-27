package nl.oose.blackpool.Controllers;

import nl.oose.blackpool.DTO.AllGroupsDTO;
import nl.oose.blackpool.DTO.CreateChildAndAddToGroupRequest;
import nl.oose.blackpool.DTO.GroupDTO;
import nl.oose.blackpool.Exceptions.DataException;
import org.apache.commons.httpclient.HttpStatus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Group")
public class GroupController {
    @Inject
    private IGroupService groupService;

    @Path("/get/{id}")
    @GET
    public Response getGroup(@PathParam("id") int id) {
        try {
            return Response.ok(groupService.getGroup(id)).build();
        } catch (DataException e) {
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

    @Path("/getAll")
    @GET
    public Response getAllGroups() {
        try {
            return Response.ok(new AllGroupsDTO(groupService.getAllGroups())).build();
        } catch (DataException e) {
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addGroup")
    @POST
    public Response addGroup(GroupDTO groupDTO) {
        try {
            groupService.addGroup(groupDTO);
            return Response.status(HttpStatus.SC_CREATED).build();
        } catch (DataException e) {
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createChild")
    @POST
    public Response createChildAndAddToGroup(CreateChildAndAddToGroupRequest createChildAndAddToGroupRequest) {
        try {
            groupService.createChild(createChildAndAddToGroupRequest);
            return Response.status(HttpStatus.SC_CREATED).build();
        } catch (DataException e) {
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateGroup")
    @PUT
    public Response updateGroup(GroupDTO groupDTO) {
        try {
            groupService.updateGroup(groupDTO);
            return Response.ok().build();
        } catch (DataException e) {
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteGroup/{id}")
    @DELETE
    public Response deleteGroup(@PathParam("id") int id) {
        try {
            groupService.deleteGroup(id);
            return Response.ok().build();
            } catch (DataException e) {
            return Response.status(HttpStatus.SC_EXPECTATION_FAILED).build();
        }
    }
}
