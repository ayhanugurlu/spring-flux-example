package com.au.example.springfluxexample.repositories;

import com.au.example.springfluxexample.mongo.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Created by Ayhan.Ugurlu on 08/01/2018
 */
public interface PersonRepository  extends ReactiveCrudRepository<Person,String> {
}
