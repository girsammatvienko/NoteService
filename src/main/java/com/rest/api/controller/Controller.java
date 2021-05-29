package com.rest.api.controller;

import com.rest.api.repository.NoteRepository;
import com.rest.api.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api")
public class Controller {

    private final Service service = new Service();

    public Controller() throws SQLException {
    }


    @GetMapping("/get")
    public String getFirstNote() {
        return "hello";
    }
    // localhost/api/add?name=MyNote&text=FirstText
    @PostMapping("/add")
    public String createNote(@RequestParam String name, @RequestParam String text) {
        service.createNote(name, text);
        return "Note created successfully";
    }
    
    @GetMapping("getAll")
    public String getAllNotes() {
        return service.getAllNotes();
    }

    @GetMapping("getById")
    public String getNoteById(@RequestParam Long id) {
        return service.getNoteById(id);
    }

    @GetMapping("getAmount")
    public int getAmountOfNotes() {
        return service.getNotesAmount();
    }

    @GetMapping("deleteAll")
    public String deleteAllNotes() {
        service.deleteNotes();
        return "All the notes has been successfully deleted";
    }

}
