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

import com.example.myapp.model.Group;
import com.example.myapp.service.GroupService;

@Controller
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public String listGroups(Model model) {
        List<Group> groups = groupService.findAll();
        model.addAttribute("groups", groups);
        return "groups";
    }

    @GetMapping("/groups/new")
    public String showGroupForm(Model model) {
        model.addAttribute("group", new Group());
        return "add-group";
    }

    @PostMapping("/groups")
    public String addGroup(@ModelAttribute("group") @Validated Group group, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-group";
        }
        groupService.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/groups/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Group group = groupService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
        model.addAttribute("group", group);
        return "update-group"; // nazwa szablonu do aktualizacji grupy
    }

    @PostMapping("/groups/update/{id}")
    public String updateGroup(@PathVariable("id") long id, @Validated Group group,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            group.setId(id);
            return "update-group";
        }
        groupService.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/groups/delete/{id}")
    public String deleteGroup(@PathVariable("id") long id, Model model) {
        Group group = groupService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
        groupService.delete(id);
        model.addAttribute("groups", groupService.findAll());
        return "groups";
    }
}