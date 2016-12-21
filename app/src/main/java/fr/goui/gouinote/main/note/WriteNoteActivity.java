package fr.goui.gouinote.main.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import fr.goui.gouinote.R;

public class WriteNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        ButterKnife.bind(this);

        setResult(RESULT_CANCELED);
        setupActionbar();
    }

    private void setupActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_write_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // if we press the up button, going back
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        if (id == R.id.action_send_note) {
            // TODO send note to backend and return to main activity
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates the intent needed to call this activity.
     *
     * @param callingContext origin context
     * @return new Intent
     */
    public static Intent getStartingIntent(Context callingContext) {
        return new Intent(callingContext, WriteNoteActivity.class);
    }
}
