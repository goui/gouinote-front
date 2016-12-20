package fr.goui.gouinote.launcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import fr.goui.gouinote.GouinoteApplication;
import fr.goui.gouinote.R;
import fr.goui.gouinote.exception.ExceptionHandler;
import fr.goui.gouinote.model.User;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter of the launcher. Will try to retrieve login information.
 * Attempts to sign in if login info is found.
 */
class LauncherPresenter implements ILauncherPresenter {

    private ILauncherView mView;

    private Subscription mSubscription;

    private User mUser;

    @Override
    public void attachView(ILauncherView view) {
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
    public void load() {
        mView.showProgressBar();

        Context context = mView.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context
                .getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String nickname = sharedPreferences.getString(context.getString(R.string.user_nickname), "");
        String password = sharedPreferences.getString(context.getString(R.string.user_password), "");
        // if nothing go to login activity
        if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(password)) {
            mView.startLoginActivity();
        }
        // else attempt to login
        else {
            load(nickname, password);
        }
    }

    private void load(String nickname, String password) {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
        final GouinoteApplication application = GouinoteApplication.get(mView.getContext());
        mSubscription = application.getNetworkService().signIn(nickname, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<User>() {

                    @Override
                    public void onCompleted() {
                        application.setConnectedUser(mUser);
                        mView.startMainActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        String errorMessage = e.getMessage();
                        if (e instanceof HttpException) {
                            errorMessage = ExceptionHandler.getMessage(e);
                        }
                        mView.showError(errorMessage);
                        mView.startLoginActivity();
                    }

                    @Override
                    public void onNext(User user) {
                        mUser = user;
                    }
                });
    }
}
