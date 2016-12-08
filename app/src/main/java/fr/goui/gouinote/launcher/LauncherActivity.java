package fr.goui.gouinote.launcher;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.goui.gouinote.R;
import fr.goui.gouinote.login.LoginActivity;

/**
 * Splash screen waiting for the user to tap.
 * Will either go to the login screen or to the main screen.
 */
public class LauncherActivity extends AppCompatActivity implements ILauncherView {

    @BindView(R.id.launcher_progress_bar)
    ProgressBar mProgressBar;

    private boolean mIsLoading;

    private ILauncherPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ButterKnife.bind(this);

        // creating the presenter
        mPresenter = new LauncherPresenter();
        mPresenter.attachView(this);
    }

    @OnClick(R.id.launcher_layout)
    public void onClick() {
        if (!mIsLoading) {
            mPresenter.load();
        }
    }

    @Override
    public void startLoginActivity() {
        startActivity(LoginActivity.getStartingIntent(this));
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
        mIsLoading = true;
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mIsLoading = false;
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
    }
}
