package fr.goui.gouinote.login;

import fr.goui.gouinote.IPresenter;

/**
 * Presenter interface for the login account.
 */
interface ILoginAccountPresenter extends IPresenter<ILoginAccountView> {

    void load(String nickname, String password, boolean isCreation);
}
