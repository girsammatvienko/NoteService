package com.rest.api.repository;

import com.rest.api.entity.Note;
import org.hibernate.annotations.ResultCheckStyle;
import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteRepository {
    private final String URL = "jdbc:sqlite:database.db";

    private static NoteRepository instance = null;

    public static synchronized NoteRepository getInstance() throws SQLException {
        if(instance == null) {
            instance = new NoteRepository();
        }
        return instance;
    }

    private Connection connection;


    public NoteRepository() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(URL);
    }

    public void addNote(Note note) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO notes(`name`, `text`) " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, note.getName());
            statement.setObject(2, note.getText());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Note> getNoteById(Long id) {
        Optional<Note> resultNote = Optional.empty();
        try (PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM notes WHERE id = ?;")) {
            statement.setObject(1, id);
            ResultSet result = statement.executeQuery();

            resultNote = Optional.of(extractNote(result));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultNote;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM notes;")) {
            ResultSet result = statement.executeQuery();

            notes = extractNotes(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public int getNotesAmount() {
        int resultAmount = 0;
        try (PreparedStatement statement = this.connection.prepareStatement(
                "SELECT * FROM notes;")) {
            ResultSet result = statement.executeQuery();

            resultAmount = extractNotes(result).size();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultAmount;
    }

    public void deleteAllNotes() {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM notes;")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Note extractNote(ResultSet set) throws SQLException {
        return new Note(set.getLong("id"), set.getString("name"),
                set.getString("text"));
    }
    private List<Note> extractNotes(ResultSet set) throws SQLException {
        List<Note> result = new ArrayList<>();
        while (set.next()) {
            result.add(new Note(set.getLong("id"), set.getString("name"),
                    set.getString("text")));
        }
        return result;
    }

}
