package com.example.lab8.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "STUDENTS")
public class Student {

    /** Идентификатор студента. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Имя студента. */
    @NotBlank(message = "Введите имя")
    @Size(max = 15, message = "Имя не длиннее 15 символов")
    @Column(name = "name", length = 15)
    private String name;

    /** Фамилия студента. */
    @NotBlank(message = "Введите фамилию")
    @Size(max = 25, message = "Фамилия не длиннее 25 символов")
    @Column(name = "surname", length = 25)
    private String surname;

    /** Факультет студента. */
    @NotBlank(message = "Введите факультет")
    @Size(max = 20, message = "Факультет не длиннее 20 символов")
    @Column(name = "faculty", length = 20)
    private String faculty;

    /** Возраст студента. */
    @NotNull(message = "Введите возраст")
    @Min(value = 1, message = "Возраст должен быть положительным")
    @Column(name = "age")
    private Integer age;
}