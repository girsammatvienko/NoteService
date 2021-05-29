package com.rest.api.service;

import com.rest.api.entity.Note;
import com.rest.api.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private NoteRepository repository;

    public Service() throws SQLException {
        this.repository = new NoteRepository();
    }

    public Note createNote(String name, String text) {
        var note = new Note(name, text);
        repository.addNote(note);
        return note;
    }

    public String getNoteById(Long id) {
    return repository.getNoteById(id).toString() + "";
    }

    public String getAllNotes() {
        String resultString = "";
        List<Note> notes = repository.getAllNotes();
        for(Note note:notes) {
            resultString += note.toString();
        }
        return resultString;
    }

    public void deleteNotes() {
        repository.deleteAllNotes();
    }

    public int getNotesAmount() {
        return repository.getNotesAmount();
    }

}
