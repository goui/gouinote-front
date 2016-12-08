package fr.goui.gouinote.launcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import fr.goui.gouinote.R;

/**
 * Presenter of the launcher. Will try to retrieve login information.
 * Attempts to sign in if login info is found.
 */
class LauncherPresenter implements ILauncherPresenter {

    private ILauncherView mView;

    @Override
    public void attachView(ILauncherView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
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

        // if something is found attempt to sign in
        // TODO backend sign in call
    }
}
