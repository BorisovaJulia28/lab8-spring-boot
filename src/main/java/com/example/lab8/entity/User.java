package com.example.lab8.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    /** Идентификатор пользователя. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Имя пользователя. */
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    /** Фамилия пользователя. */
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    /** Электронная почта для входа. */
    @Column(name = "email", nullable = false, unique = true, length = 254)
    private String email;

    /** Хеш пароля. */
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    /** Роли пользователя. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}