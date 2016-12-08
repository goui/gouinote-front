package fr.goui.gouinote.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import fr.goui.gouinote.R;

/**
 * Activity switching between the login menu and the credentials typing screens.
 * Will go to the main screen.
 */
public class LoginActivity extends AppCompatActivity implements LoginMenuListener {

    /**
     * Handles fragment transactions.
     */
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // showing the login menu screen
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_login_content, new LoginMenuFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onSignInClick() {
        // TODO go to the sign in screen
    }

    @Override
    public void onCreateAccountClick() {
        // TODO go to the create account screen
    }

    @Override
    public void onBeAGuestClick() {
        // TODO go to the main activity as a guest
    }

    /**
     * Creates the intent needed to call this activity.
     *
     * @param callingContext origin context
     * @return new Intent
     */
    public static Intent getStartingIntent(Context callingContext) {
        return new Intent(callingContext, LoginActivity.class);
    }
}
