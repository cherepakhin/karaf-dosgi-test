package ru.perm.v.dosgi.simple.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PeopleService {

    @GET
    @Path("/echo/{message}")
    String echo(@PathParam("message") String message);

    @POST
    void add(People people);

    @GET
    @Path("/{id}")
    People getById(@PathParam("id") Integer id);

    @DELETE
    @Path("/{id}")
    void delete(@PathParam("id") Integer id);

    @GET
    People[] getAll();
}
