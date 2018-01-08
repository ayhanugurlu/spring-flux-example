package com.au.example.springfluxexample.controller;

import com.au.example.springfluxexample.mongo.Person;
import com.au.example.springfluxexample.repositories.PersonRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;

import java.time.Duration;

/**
 * Created by Ayhan.Ugurlu on 08/01/2018
 */


@RestController
public class PersonController {

    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/person")
    Mono<Void> create(@RequestBody Publisher<Person> personStream) {
        return this.repository.saveAll(personStream).then();
    }

    @GetMapping(value = "/person")
    Flux<Person> list() {
        return this.repository.findAll();
    }

    @GetMapping(value = "/person/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Person> listStream() {
        return this.repository.findAll().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/person/{id}")
    Mono<Person> findById(@PathVariable String id) {
        return this.repository.findById(id);
    }

    @GetMapping(value = "/generate", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Person> generatePerson() {
        System.out.println("this.hashCode() = " + this.hashCode());
        Flux<Person> f2 = Flux.generate(
                () -> 0,
                (integer, personSynchronousSink) -> {
                    Person p = new Person();
                    p.setUsername("username" + integer);
                    p.setPassword("pass");

                    personSynchronousSink.next(p);
                    if (integer == 100) {
                        personSynchronousSink.complete();
                    }
                    return integer + 1;
                });
        ParallelFlux<Person> personFlux = f2.delayElements(Duration.ofSeconds(0)).parallel(3, 5);

        return this.repository.saveAll(personFlux);
    }
}