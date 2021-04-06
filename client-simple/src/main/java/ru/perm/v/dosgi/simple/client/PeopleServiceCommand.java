package ru.perm.v.dosgi.simple.client;

import lombok.extern.slf4j.Slf4j;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import ru.perm.v.dosgi.simple.api.People;
import ru.perm.v.dosgi.simple.api.PeopleService;

import java.util.stream.Stream;


@Component//
        (//
                service = PeopleServiceCommand.class,
                property = //
                        {
                                "osgi.command.scope=people", //
                                "osgi.command.function=test", //
                                "osgi.command.function=echo", //
                                "osgi.command.function=get", //
                                "osgi.command.function=list", //
                                "osgi.command.function=add", //
                                "osgi.command.function=delete"
                        }//
        )
@Slf4j
public class PeopleServiceCommand {

    PeopleService peopleService;

    public PeopleServiceCommand() {
    }

    public PeopleServiceCommand(PeopleService peopleService) {
        this.peopleService = peopleService;
        System.out.println(peopleService.echo("Started"));
    }

    public void test(String mes) {
        System.out.println("test:" + mes);
    }

    public void get(Integer id) {
        System.out.println(peopleService.getById(id));
    }

    public void echo(String mes) {
        System.out.println(peopleService.echo(mes));
    }

    public void list() {
        log.info("People list");
        Stream.of(peopleService.getAll()).forEach(System.out::println);
    }

    public void add(Integer id, String name) {
        peopleService.add(new People(id, name));
    }

    public void delete(Integer id) {
        peopleService.delete(id);
    }

    @Reference
    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
        log.info(peopleService.echo("setPeopleService"));
    }
}
