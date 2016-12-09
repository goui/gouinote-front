package fr.goui.gouinote.model;

import java.util.List;

/**
 * POJO representing a user.
 */
public class User {

    private String nickname;

    private String password;

    private List<Note> notes;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname_p) {
        nickname = nickname_p;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password_p) {
        password = password_p;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes_p) {
        notes = notes_p;
    }
}
