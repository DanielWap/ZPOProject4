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

import com.example.myapp.model.Schedule;
import com.example.myapp.service.ScheduleService;

@Controller
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedules")
    public String listSchedules(Model model) {
        List<Schedule> schedules = scheduleService.findAll();
        model.addAttribute("schedules", schedules);
        return "schedules";
    }

    @GetMapping("/schedules/new")
    public String showScheduleForm(Model model) {
        model.addAttribute("schedule", new Schedule());
        return "add-schedule";
    }

    @PostMapping("/schedules")
    public String addSchedule(@ModelAttribute("schedule") @Validated Schedule schedule, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-schedule";
        }
        scheduleService.save(schedule);
        return "redirect:/schedules";
    }

    @GetMapping("/schedules/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Schedule schedule = scheduleService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + id));
        model.addAttribute("schedule", schedule);
        return "update-schedule"; // nazwa szablonu do aktualizacji harmonogramu
    }

    @PostMapping("/schedules/update/{id}")
    public String updateSchedule(@PathVariable("id") long id, @Validated Schedule schedule,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            schedule.setId(id);
            return "update-schedule";
        }
        scheduleService.save(schedule);
        return "redirect:/schedules";
    }

    @GetMapping("/schedules/delete/{id}")
    public String deleteSchedule(@PathVariable("id") long id, Model model) {
        Schedule schedule = scheduleService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + id));
        scheduleService.deleteById(id);
        model.addAttribute("schedules", scheduleService.findAll());
        return "schedules";
    }
}