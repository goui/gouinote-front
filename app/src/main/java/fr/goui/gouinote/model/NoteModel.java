package fr.goui.gouinote.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Cache model containing all the notes.
 * Is a singleton.
 */
public class NoteModel extends Observable {

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
        notifyObservers();
    }
}
