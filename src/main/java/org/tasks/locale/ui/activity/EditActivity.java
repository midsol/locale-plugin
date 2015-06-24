package org.tasks.locale.ui.activity;

import org.tasks.locale.R;
import org.tasks.locale.bundle.PluginBundleValues;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public final class EditActivity extends AbstractFragmentPluginAppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
            supportActionBar.setTitle(R.string.app_name);
        }
    }

    @Override
    public void onPostCreateWithPreviousResult(final Bundle previousBundle, final String previousBlurb) {
        final String message = PluginBundleValues.getMessage(previousBundle);
        ((EditText) findViewById(android.R.id.text1)).setText(message);
    }

    @Override
    public boolean isBundleValid(final Bundle bundle) {
        return PluginBundleValues.isBundleValid(bundle);
    }

    @Override
    public Bundle getResultBundle() {
        Bundle result = null;

        final String message = ((EditText) findViewById(android.R.id.text1)).getText().toString();
        if (message.length() > 0) {
            result = PluginBundleValues.generateBundle(getApplicationContext(), message);
        }

        return result;
    }

    @Override
    public String getResultBlurb(final Bundle bundle) {
        return PluginBundleValues.getMessage(bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.tasks_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save:
                finish();
                break;
            case android.R.id.home:
                mIsCancelled = true;
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
