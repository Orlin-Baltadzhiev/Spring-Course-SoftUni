package com.mappingexercise.softunigame.domain.entities;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,updatable = false)
    private Long id;
}
