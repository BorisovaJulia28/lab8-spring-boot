package com.example.lab8.controller;

import com.example.lab8.entity.Student;
import com.example.lab8.repository.StudentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Controller
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /** Список студентов. */
    @GetMapping("/list")
    public String listStudents(Model model) {
        model.addAttribute(
                "students",
                studentRepository.findAll(Sort.by("id"))
        );
        return "list-students";
    }

    /** Форма добавления студента. */
    @GetMapping("/addStudentForm")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student-form";
    }

    /** Сохранение нового или изменённого студента. */
    @PostMapping("/saveStudent")
    public String saveStudent(
            @Valid @ModelAttribute("student") Student student,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "add-student-form";
        }

        if (student.getId() != null) {
            findStudent(student.getId());
        }

        studentRepository.save(student);
        return "redirect:/list";
    }

    /** Форма изменения студента. */
    @GetMapping("/showUpdateForm")
    public String showUpdateForm(
            @RequestParam("studentId") Integer studentId,
            Model model) {

        model.addAttribute("student", findStudent(studentId));
        return "add-student-form";
    }

    /** Удаление студента. */
    @PostMapping("/deleteStudent")
    public String deleteStudent(
            @RequestParam("studentId") Integer studentId) {

        studentRepository.delete(findStudent(studentId));
        return "redirect:/list";
    }

    /** Поиск студента с проверкой существования. */
    private Student findStudent(Integer id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Студент не найден"
                ));
    }
}