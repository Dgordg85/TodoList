package ru.geekbrains.todolist.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.todolist.repr.ToDoRepr;
import ru.geekbrains.todolist.service.ToDoService;
import ru.geekbrains.todolist.service.UserService;

import java.util.List;

@Controller
public class TodoController {

    private ToDoService toDoService;

    private UserService userService;

    @Autowired
    public TodoController(ToDoService toDoService, UserService userService) {
        this.toDoService = toDoService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String indexPage(Model model){
        List<ToDoRepr> todos = toDoService.findToDosByUserId(userService.getCurrentUserId().orElseThrow(ResourceNotFoundException::new));
        model.addAttribute("todos", todos);
        return "index";
    }

    @GetMapping("/todo/{id}")
    public String todoPage(@PathVariable("id") Long id, Model model){
        ToDoRepr todoRepr = toDoService.findById(id).orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("todo", todoRepr);
        return "todo";
    }

    @PostMapping("/todo/create")
    public String createToDoPost(@ModelAttribute("todo") ToDoRepr todoRepr){
        toDoService.save(todoRepr);
        return "redirect:/";
    }

    @GetMapping("todo/delete/{id}")
    public String deleteToDoPost(Model model, @PathVariable Long id){
        toDoService.delete(id);
        return "redirect:/";
    }
}
