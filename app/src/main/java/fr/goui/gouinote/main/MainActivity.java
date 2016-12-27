package fr.goui.gouinote.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.gouinote.GouinoteApplication;
import fr.goui.gouinote.R;
import fr.goui.gouinote.login.LoginActivity;
import fr.goui.gouinote.main.note.NotesFragment;
import fr.goui.gouinote.main.note.WriteNoteActivity;
import fr.goui.gouinote.main.user.UserActivity;
import fr.goui.gouinote.main.user.UserClickEvent;
import fr.goui.gouinote.main.user.UsersFragment;
import fr.goui.gouinote.model.NoteModel;

public class MainActivity extends AppCompatActivity implements IMainView, Observer {

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

    @BindView(R.id.progress_bar_layout)
    FrameLayout mProgressBarLayout;

    private IMainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolBar);

        mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        mFab.setVisibility(getIntent().getBooleanExtra(IS_A_GUEST, true) ? View.GONE : View.VISIBLE);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(WriteNoteActivity.getStartingIntent(MainActivity.this), WriteNoteActivity.REQUEST_CODE);
            }
        });

        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == WriteNoteActivity.REQUEST_CODE) {
            String content = data.getStringExtra(WriteNoteActivity.INTENT_DATA);
            mPresenter.load(content);
        }
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        NoteModel.getInstance().deleteObserver(MainActivity.this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        NoteModel.getInstance().addObserver(MainActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserClickEvent(UserClickEvent event) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, event.getSharedElement(), "user_avatar");
        startActivity(UserActivity.getStartingIntent(this, event.getUser().getNickname()), options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            startActivity(LoginActivity.getStartingIntent(this));
            eraseLoginData();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void eraseLoginData() {
        GouinoteApplication application = GouinoteApplication.get(this);
        application.setConnectedUser(null);
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        sharedPreferences.edit()
                .clear()
                .apply();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgressBar() {
        mProgressBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof NoteModel && (int) data == NoteModel.ADD_ONE_REQUEST_CODE) {
            Toast.makeText(this, getString(R.string.Note_has_been_added), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
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
