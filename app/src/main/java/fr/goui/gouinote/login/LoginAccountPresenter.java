package fr.goui.gouinote.login;

import fr.goui.gouinote.GouinoteApplication;
import fr.goui.gouinote.model.User;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter of the login account. Will try to sign in or create account depending on flag.
 */
class LoginAccountPresenter implements ILoginAccountPresenter {

    private ILoginAccountView mView;

    private Subscription mSubscription;

    private User mUser;

    @Override
    public void attachView(ILoginAccountView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }

    @Override
    public void load(String nickname, String password, boolean isCreation) {
        mView.showProgressBar();
        mView.hideButton();
        mView.lockControls();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        final GouinoteApplication application = GouinoteApplication.get(mView.getContext());
        User user = new User();
        user.setNickname(nickname);
        user.setPassword(password);

        if (isCreation) {
            createUser(user, application);
        } else {
            signInUser(user, application);
        }
    }

    private void createUser(User user, final GouinoteApplication application) {
        mSubscription = application.getNetworkService().createUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        application.setConnectedUser(mUser);
                        mView.showResult(mUser);
                        mView.startMainActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        mView.showButton();
                        mView.hideProgressBar();
                        mView.unlockControls();
                    }

                    @Override
                    public void onNext(User user) {
                        mUser = user;
                    }
                });
    }

    private void signInUser(User user, final GouinoteApplication application) {
        mSubscription = application.getNetworkService().signIn(user.getNickname(), user.getPassword())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        application.setConnectedUser(mUser);
                        mView.showResult(mUser);
                        mView.startMainActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        mView.showButton();
                        mView.hideProgressBar();
                        mView.unlockControls();
                    }

                    @Override
                    public void onNext(User user) {
                        mUser = user;
                    }
                });
    }
}
