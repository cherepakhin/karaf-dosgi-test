package ru.perm.v.dosgi.simple.service;

import lombok.extern.slf4j.Slf4j;
import ru.perm.v.dosgi.simple.api.People;
import ru.perm.v.dosgi.simple.api.PeopleService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PeopleServiceImpl implements PeopleService {
    Map<Integer, People> peoples = new HashMap<>();

    public PeopleServiceImpl() {
        People first = new People(100, "NAME_100");
        peoples.put(first.getId(), first);
    }

    @Override
    public String echo(String message) {
        return "echo:" + message;
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
