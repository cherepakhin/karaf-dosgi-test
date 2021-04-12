package ru.perm.v.dosgi.client1;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ManagedAsync;
import ru.perm.v.dosgi.simple.api.People;
import ru.perm.v.dosgi.simple.api.PeopleService;

import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;

@Slf4j
@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PeopleClientController {

    PeopleService peopleService;

    public PeopleClientController() {
    }

    public PeopleClientController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }


    @GET
    @Path("/info")
    public String info() {
        String mes = String.format("peopleService:%s", peopleService);
        log.info(mes);
        return mes;
    }

    @GET
    @Path("/echo/{mes}")
    public String echo(@PathParam("mes") String mes) {
        return mes;
    }

    @GET
    public People[] list() {
        log.info("People list");
        for (People p : peopleService.getAll()) {
            log.info(p.toString());
        }
        return peopleService.getAll();
    }

    @POST
    public void add(Integer id, String name) {
        peopleService.add(new People(id, name));
    }

    @DELETE
    @Path("/{id}")
    public void delete(Integer id) {
        peopleService.delete(id);
    }

    @GET
    @Path("/{id}")
    public People getById(@PathParam("id") Integer id) {
        return peopleService.getById(id);
    }

    public PeopleService getPeopleService() {
        return peopleService;
    }

    //    @Reference
    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @ManagedAsync
    @GET
    @Path("/jersey/{sleep}")
    public void asyncGet(@PathParam("sleep") Integer sleep,
                         @Suspended final AsyncResponse asyncResponse) {
        if (sleep == 0) {
            try {
                throw new Exception("EXCEPTION");
            } catch (Exception e) {
                asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build());
            }
        }
        try {
            TimeUnit.SECONDS.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        asyncResponse.resume(sleep);
    }

    @GET
    @Path("/async/{sleep}/{timeout}")
    public void runAsync(@PathParam("sleep") Integer sleep,
                         @PathParam("timeout") Integer timeout,
                         @Suspended final AsyncResponse asyncResponse) {
        CompletableFuture
                .supplyAsync(
                        () -> {
                            if (timeout == 1) {
                                throw new RuntimeException();
                            }
                            if (timeout == 0) {
                                try {
                                    throw new Exception("EXCEPTION");
                                } catch (Exception e) {
                                    asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).entity(e).build());
                                }
                            }
                            try {
                                TimeUnit.SECONDS.sleep(sleep);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return String.format("sleep=%d,timeout=%d", sleep, timeout);
                        }
                )
                .thenApply(answer -> asyncResponse.resume(Response.ok(answer).build()))
                .exceptionally(e -> asyncResponse.resume(Response.status(INTERNAL_SERVER_ERROR).build()));
        asyncResponse.setTimeout(timeout, TimeUnit.SECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(
                Response.status(SERVICE_UNAVAILABLE).entity("Operation timed out").build()));
    }

    ;


}
