package fr.goui.gouinote.main.user;

import android.view.View;

import fr.goui.gouinote.model.User;

/**
 * Custom event triggered when clicking on a user.
 */
public class UserClickEvent {

    private User user;

    private View sharedElement;

    public UserClickEvent(User user_p, View sharedElement_p) {
        user = user_p;
        sharedElement = sharedElement_p;
    }

    public User getUser() {
        return user;
    }

    public View getSharedElement() {
        return sharedElement;
    }
}
