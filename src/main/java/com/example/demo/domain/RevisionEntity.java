package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "revision")
public class RevisionEntity {

    @Id
    @GeneratedValue
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Setter
    private String context;

}
