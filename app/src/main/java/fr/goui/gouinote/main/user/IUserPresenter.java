package fr.goui.gouinote.main.user;

import fr.goui.gouinote.IPresenter;

/**
 * Presenter interface for a specific user.
 */
interface IUserPresenter extends IPresenter<IUserView> {

    void load(String nickname);
}
