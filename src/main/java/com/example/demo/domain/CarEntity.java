package com.example.demo.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreatedDate
    @Column(columnDefinition = "timestamp")
    private ZonedDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "timestamp")
    private ZonedDateTime lastModified;

    @Column(unique = true)
    private String name;

    @Version
    private Integer version;

}
