package fr.goui.gouinote.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Cache model containing all the notes.
 * Is a singleton.
 */
public class NoteModel extends Observable {

    public static final int ADD_ONE_REQUEST_CODE = 0;

    public static final int REPLACE_ALL_REQUEST_CODE = 1;

    private static NoteModel instance;

    private List<Note> notes;

    private NoteModel() {
        notes = new ArrayList<>();
    }

    public static NoteModel getInstance() {
        if (instance == null) {
            instance = new NoteModel();
        }
        return instance;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes_p) {
        notes = notes_p;
        setChanged();
        notifyObservers(REPLACE_ALL_REQUEST_CODE);
    }

    public void addNote(Note note) {
        notes.add(note);
        setChanged();
        notifyObservers(ADD_ONE_REQUEST_CODE);
    }
}
