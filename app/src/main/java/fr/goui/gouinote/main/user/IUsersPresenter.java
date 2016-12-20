package fr.goui.gouinote.main.user;

import fr.goui.gouinote.IPresenter;

/**
 * Presenter interface for the list of all users.
 */
interface IUsersPresenter extends IPresenter<IUsersView> {

    void load();
}
