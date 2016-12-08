package fr.goui.gouinote.login;

/**
 * Presenter of the login account. Will try to sign in or create account depending on flag.
 */
class LoginAccountPresenter implements ILoginAccountPresenter {

    private ILoginAccountView mView;

    @Override
    public void attachView(ILoginAccountView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void load(String nickname, String password, boolean isCreation) {
        mView.showProgressBar();
        mView.hideButton();
        mView.lockControls();
    }
}
