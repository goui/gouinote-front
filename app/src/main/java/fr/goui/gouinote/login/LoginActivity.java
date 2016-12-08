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
public class LoginActivity extends AppCompatActivity {

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
