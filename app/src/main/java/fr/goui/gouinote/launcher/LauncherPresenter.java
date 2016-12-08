package fr.goui.gouinote.launcher;

/**
 * Presenter of the launcher.
 */
public class LauncherPresenter implements ILauncherPresenter {

    private ILauncherView mView;

    @Override
    public void attachView(ILauncherView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
