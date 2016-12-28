package fr.goui.gouinote.main.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.gouinote.R;
import fr.goui.gouinote.adapter.NoteListAdapter;
import fr.goui.gouinote.adapter.SimpleDividerItemDecoration;
import fr.goui.gouinote.model.User;

public class UserActivity extends AppCompatActivity implements IUserView {

    public static final String USER_NICKNAME = "user_nickname";

    @BindView(R.id.user_nickname_text_view)
    TextView mNicknameTextView;

    @BindView(R.id.user_nb_of_notes_text_view)
    TextView mNbOfNotesTextView;

    @BindView(R.id.user_nb_of_likes_text_view)
    TextView mNbOfLikesTextView;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private NoteListAdapter mAdapter;

    private IUserPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        setupActionbar();

        final String nickname = getIntent().getStringExtra(USER_NICKNAME);
        mNicknameTextView.setText(nickname);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mRecyclerView.setAdapter(null);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.load(nickname);
            }
        });

        mPresenter = new UserPresenter();
        mPresenter.attachView(this);
        mPresenter.load(nickname);
    }

    private void setupActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // if we press the up button, going back
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResult(User user) {
        if (mAdapter == null) {
            mAdapter = new NoteListAdapter(this);
            mRecyclerView.setAdapter(mAdapter);
        }
        mAdapter.setListOfNotes(user.getNotes());
        mNbOfNotesTextView.setText(getString(R.string.Nb_notes, user.getNotes().size()));
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
     * @param nickname       the user's nickname
     * @return new Intent
     */
    public static Intent getStartingIntent(Context callingContext, String nickname) {
        Intent intent = new Intent(callingContext, UserActivity.class);
        intent.putExtra(USER_NICKNAME, nickname);
        return intent;
    }
}
