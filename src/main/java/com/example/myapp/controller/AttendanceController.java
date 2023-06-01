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

import com.example.myapp.model.Attendance;
import com.example.myapp.service.AttendanceService;

@Controller
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/attendances")
    public String listAttendances(Model model) {
        List<Attendance> attendances = attendanceService.findAll();
        model.addAttribute("attendances", attendances);
        return "attendances";
    }

    @GetMapping("/attendances/new")
    public String showAttendanceForm(Model model) {
        model.addAttribute("attendance", new Attendance());
        return "add-attendance";
    }

    @PostMapping("/attendances")
    public String addAttendance(@ModelAttribute("attendance") @Validated Attendance attendance, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-attendance";
        }
        attendanceService.save(attendance);
        return "redirect:/attendances";
    }

    @GetMapping("/attendances/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Attendance attendance = attendanceService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attendance Id:" + id));
        model.addAttribute("attendance", attendance);
        return "update-attendance"; // nazwa szablonu do aktualizacji obecności
    }

    @PostMapping("/attendances/update/{id}")
    public String updateAttendance(@PathVariable("id") long id, @Validated Attendance attendance,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            attendance.setId(id);
            return "update-attendance";
        }
        attendanceService.save(attendance);
        return "redirect:/attendances";
    }

    @GetMapping("/attendances/delete/{id}")
    public String deleteAttendance(@PathVariable("id") long id, Model model) {
        Attendance attendance = attendanceService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attendance Id:" + id));
        attendanceService.delete(id);
        model.addAttribute("attendances", attendanceService.findAll());
        return "attendances";
    }
}