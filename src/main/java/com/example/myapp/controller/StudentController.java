package com.example.myapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.myapp.model.Student;
import com.example.myapp.service.StudentService;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentService.findAll();
        model.addAttribute("students", students);
        return "students"; // Nazwa szablonu bez rozszerzenia .html
    }

    @GetMapping("/students/new")
    public String showStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student"; // Nazwa szablonu do dodawania studenta
    }
    
    @PostMapping("/students")
    public String addStudent(@ModelAttribute("student") @Validated Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-student"; // jeżeli są błędy, powróć do formularza
        }
        studentService.save(student);
        return "redirect:/students"; // po pomyślnym dodaniu przekieruj na listę studentów
    }
    
    @GetMapping("/students/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "update-student"; // nazwa szablonu do aktualizacji studenta
    }
    
    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable("id") long id, @Validated Student student, 
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            student.setId(id);
            return "update-student"; // jeżeli są błędy, powróć do formularza
        }
        studentService.save(student);
        return "redirect:/students"; // po pomyślnym dodaniu przekieruj na listę studentów
    }
    
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        studentService.delete(id);
        model.addAttribute("students", studentService.findAll());
        return "students"; // nazwa szablonu do wyświetlania studentów
    }
}