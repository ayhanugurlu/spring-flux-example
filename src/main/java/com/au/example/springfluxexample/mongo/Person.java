package com.au.example.springfluxexample.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Ayhan.Ugurlu on 08/01/2018
 */
@Document(collection = "person")
@Setter
@Getter
@NoArgsConstructor
public class Person {


    @Id
    private String id;

    private String username;

    private String password;
}
