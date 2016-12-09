package fr.goui.gouinote.model;

/**
 * POJO representing a note.
 */
public class Note {

    private String content;

    private String nickname;

    private long date;

    public String getContent() {
        return content;
    }

    public void setContent(String content_p) {
        content = content_p;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname_p) {
        nickname = nickname_p;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date_p) {
        date = date_p;
    }
}
