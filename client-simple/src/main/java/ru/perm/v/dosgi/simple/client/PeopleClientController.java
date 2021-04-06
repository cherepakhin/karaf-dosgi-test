package ru.perm.v.dosgi.simple.client;

import lombok.extern.slf4j.Slf4j;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import ru.perm.v.dosgi.simple.api.People;
import ru.perm.v.dosgi.simple.api.PeopleService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//@Component//
//        (//
//                immediate = true, //
//                name = "PeopleClientController", //
//                property = //
//                        { //
//                                "service.exported.interfaces=*", //
//                                "service.exported.configs=org.apache.cxf.rs", //
//                                "org.apache.cxf.rs.address=/client", //
////                                "org.apache.cxf.rs.httpservice.context=/peoples", //
////                                "org.apache.cxf.rs.provider=true",
////                                "org.apache.cxf.rs.provider=ru.perm.v.dosgi.simple.api.PeopleService"
//                                // By default CXF will favor the default json provider
////                                "cxf.bus.prop.skip.default.json.provider.registration=true"
//                        } //
//
//        )

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
    public void info() {
        log.info("peopleService: {}",peopleService);
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
}
