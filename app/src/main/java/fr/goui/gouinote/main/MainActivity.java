package fr.goui.gouinote.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.gouinote.R;
import fr.goui.gouinote.main.note.NotesFragment;
import fr.goui.gouinote.main.user.UserClickEvent;
import fr.goui.gouinote.main.user.UsersFragment;

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

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);

        mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        mFab.setVisibility(getIntent().getBooleanExtra(IS_A_GUEST, true) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserClickEvent(UserClickEvent event) {
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? new NotesFragment() : new UsersFragment();
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? NotesFragment.NOTES : UsersFragment.USERS;
        }
    }
}
