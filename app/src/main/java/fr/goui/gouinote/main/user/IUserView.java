package fr.goui.gouinote.main.user;

import fr.goui.gouinote.IView;
import fr.goui.gouinote.model.User;

/**
 * View interface for a specific user.
 */
interface IUserView extends IView {

    void showResult(User user);
}
