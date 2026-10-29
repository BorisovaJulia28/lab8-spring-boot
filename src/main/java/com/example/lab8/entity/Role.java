package com.example.lab8.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ROLES")
public class Role {

    /** Идентификатор роли. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название роли, например ROLE_USER. */
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
}