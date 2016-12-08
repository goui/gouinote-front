package fr.goui.gouinote.login;

import fr.goui.gouinote.IView;

/**
 * View interface for the login account.
 */
public interface ILoginAccountView extends IView {

    void hideButton();

    void showButton();

    void lockControls();

    void unlockControls();
}
