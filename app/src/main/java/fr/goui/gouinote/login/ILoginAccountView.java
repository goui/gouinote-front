package fr.goui.gouinote.login;

import fr.goui.gouinote.IView;
import fr.goui.gouinote.model.User;

/**
 * View interface for the login account.
 */
interface ILoginAccountView extends IView {

    void hideButton();

    void showButton();

    void lockControls();

    void unlockControls();

    void showResult(User user);

    void startMainActivity();
}
