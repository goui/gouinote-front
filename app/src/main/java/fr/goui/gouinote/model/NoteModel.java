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
        Note note0 = new Note();
        note0.setDate(System.currentTimeMillis());
        note0.setNickname("Goui");
        note0.setContent("Je vais à l'iga, quelqu'un a besoin de quelque chose ?");
        notes.add(note0);
        Note note1 = new Note();
        note1.setDate(System.currentTimeMillis());
        note1.setNickname("Erika");
        note1.setContent("Je vais à l'école !");
        notes.add(note1);
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
