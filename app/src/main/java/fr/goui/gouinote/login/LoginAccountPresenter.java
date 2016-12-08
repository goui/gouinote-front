package fr.goui.gouinote.login;

/**
 * Created by Goui-MSI on 08/12/2016.
 */

public class LoginAccountPresenter implements ILoginAccountPresenter {

    private ILoginAccountView mView;

    @Override
    public void attachView(ILoginAccountView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
