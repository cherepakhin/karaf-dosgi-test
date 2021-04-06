package ru.perm.v.dosgi.simple.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.dosgi.common.api.IntentsProvider;
import org.osgi.service.component.annotations.Component;
import ru.perm.v.dosgi.simple.api.People;
import ru.perm.v.dosgi.simple.api.PeopleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Slf4j
@Component//
        (//
                immediate = true, //
                name = "PeopleService", //
                property = //
                        { //
                                "service.exported.interfaces=ru.perm.v.dosgi.simple.api.PeopleService", //
                                "service.exported.configs=org.apache.cxf.rs", //
                                "org.apache.cxf.rs.address=/peoples", //
                                "service.exported.intents=jackson",
//                                "org.apache.cxf.rs.provider=true",
//                                "org.apache.cxf.rs.provider=com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider",
                                // By default CXF will favor the default json provider
                                "cxf.bus.prop.skip.default.json.provider.registration=true"
                        } //

        )
public class PeopleServiceImpl implements PeopleService, IntentsProvider {
    //public class PeopleServiceImpl implements PeopleService {
    Map<Integer, People> peoples = new HashMap<>();

    public PeopleServiceImpl() {
        People first = new People(100, "NAME_100");
        peoples.put(first.getId(), first);
    }

    @Override
    public String echo(String message) {
        return "echo:"+message;
    }

    @Override
    public void add(People people) {
        peoples.put(people.getId(), people);
    }

    @Override
    public People getById(Integer id) {
        return peoples.getOrDefault(id, new People(-1, ""));
    }

    @Override
    public void delete(Integer id) {
        peoples.remove(id);
    }

    @Override
    public People[] getAll() {
        return peoples.values().toArray(new People[0]);
    }

    @Override
    public List<?> getIntents() {
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        log.info("getIntents {} {}", this.getClass().getName(), provider.getClass().getName());
        return asList(provider);
    }
}
