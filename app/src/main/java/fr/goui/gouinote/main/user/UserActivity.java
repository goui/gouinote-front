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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.gouinote.R;
import fr.goui.gouinote.adapter.NoteListAdapter;
import fr.goui.gouinote.adapter.SimpleDividerItemDecoration;

public class UserActivity extends AppCompatActivity {

    public static final String USER_NICKNAME = "user_nickname";

    @BindView(R.id.user_nickname_text_view)
    TextView mNicknameTextView;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        setupActionbar();

        mNicknameTextView.setText(getIntent().getStringExtra(USER_NICKNAME));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mRecyclerView.setAdapter(new NoteListAdapter(this));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO refresh content
            }
        });
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
