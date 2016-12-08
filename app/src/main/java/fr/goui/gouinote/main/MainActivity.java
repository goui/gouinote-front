package fr.goui.gouinote.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.gouinote.R;

import static fr.goui.gouinote.R.id.fab;

public class MainActivity extends AppCompatActivity {

    /**
     * Flag key.
     */
    private static final String IS_A_GUEST = "is_a_guest";

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.container)
    ViewPager mViewPager;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);

        mFab.setVisibility(getIntent().getBooleanExtra(IS_A_GUEST, true) ? View.GONE : View.VISIBLE);
    }

    /**
     * Creates the intent needed to call this activity.
     *
     * @param callingContext origin context
     * @param isAGuest       flag to know if it is a guest
     * @return new Intent
     */
    public static Intent getStartingIntent(Context callingContext, boolean isAGuest) {
        Intent intent = new Intent(callingContext, MainActivity.class);
        intent.putExtra(IS_A_GUEST, isAGuest);
        return intent;
    }
}
