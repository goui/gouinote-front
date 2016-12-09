package fr.goui.gouinote.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Cache model containing all the users.
 * Is a singleton.
 */
public class UserModel extends Observable {

    private static UserModel instance;

    private List<User> users;

    private UserModel() {
        users = new ArrayList<>();
        User user0 = new User();
        user0.setNickname("Goui");
        users.add(user0);
        User user1 = new User();
        user1.setNickname("Erika");
        users.add(user1);
    }

    public static UserModel getInstance() {
        if (instance == null) {
            instance = new UserModel();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users_p) {
        users = users_p;
        setChanged();
        notifyObservers();
    }
}
