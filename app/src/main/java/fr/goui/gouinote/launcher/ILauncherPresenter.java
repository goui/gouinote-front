package fr.goui.gouinote.launcher;

import fr.goui.gouinote.IPresenter;

/**
 * Presenter interface for the launcher.
 */
interface ILauncherPresenter extends IPresenter<ILauncherView> {

    void load();
}
