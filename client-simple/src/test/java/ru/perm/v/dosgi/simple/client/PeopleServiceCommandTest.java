package ru.perm.v.dosgi.simple.client;


import org.junit.Before;
import org.junit.Test;
import ru.perm.v.dosgi.simple.api.People;
import ru.perm.v.dosgi.simple.api.PeopleService;

import java.util.HashMap;
import java.util.Map;

public class PeopleServiceCommandTest {

    PeopleService peopleService;

    @Before
    public void setUp() {
        peopleService = new PeopleServiceStub();
    }

    @Test
    public void list() {
        peopleService.add(new People(1, "NAME_1"));
        PeopleServiceCommand command = new PeopleServiceCommand();
        command.setPeopleService(peopleService);
        command.list();
    }

    class PeopleServiceStub implements PeopleService {
        Map<Integer, People> peoples = new HashMap<>();

        @Override
        public String echo(String s) {
            return null;
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
    }
}