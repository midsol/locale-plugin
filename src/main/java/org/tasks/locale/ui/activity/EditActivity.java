package org.tasks.locale.ui.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tasks.locale.R;
import org.tasks.locale.bundle.PluginBundleValues;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public final class EditActivity extends AbstractFragmentPluginAppCompatActivity {

    private static final Logger log = LoggerFactory.getLogger(EditActivity.class);

    private static final int REQUEST_SELECT_FILTER = 10124;

    private String title;
    private String query;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        if (savedInstanceState != null) {
            title = savedInstanceState.getString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE);
            query = savedInstanceState.getString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY);
            updateActivity();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
            supportActionBar.setTitle(R.string.app_name);
        }

        findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent() {{
                    setComponent(new ComponentName("org.tasks", "org.tasks.activities.FilterSelectionActivity"));
                }}, REQUEST_SELECT_FILTER);
            }
        });
    }

    @Override
    public void onPostCreateWithPreviousResult(final Bundle previousBundle, final String previousBlurb) {
        title = PluginBundleValues.getTitle(previousBundle);
        query = PluginBundleValues.getQuery(previousBundle);
        updateActivity();
    }

    @Override
    public boolean isBundleValid(final Bundle bundle) {
        return PluginBundleValues.isBundleValid(bundle);
    }

    @Override
    public Bundle getResultBundle() {
        return PluginBundleValues.generateBundle(title, query);
    }

    @Override
    public String getResultBlurb(final Bundle bundle) {
        return PluginBundleValues.getTitle(bundle);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_FILTER) {
            if (resultCode == RESULT_OK) {
                title = data.getStringExtra("extra_filter_name");
                query = data.getStringExtra("extra_filter_query");
                updateActivity();
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, title);
        outState.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, query);
    }

    private void updateActivity() {
        ((TextView) findViewById(R.id.text_view)).setText(title);
    }
}
