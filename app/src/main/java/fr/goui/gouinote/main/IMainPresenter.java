package fr.goui.gouinote.main;

import fr.goui.gouinote.IPresenter;

/**
 * Presenter interface for the main screen.
 */
interface IMainPresenter extends IPresenter<IMainView> {

    void load(String noteContent);
}
