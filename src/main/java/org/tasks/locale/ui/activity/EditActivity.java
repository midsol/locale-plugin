package org.tasks.locale.ui.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tasks.locale.R;
import org.tasks.locale.bundle.PluginBundleValues;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Set;

public final class EditActivity extends AbstractFragmentPluginAppCompatActivity {

    private static final Logger log = LoggerFactory.getLogger(EditActivity.class);

    private static final int REQUEST_SELECT_FILTER = 10124;

    private Bundle previousBundle;
    private String title;
    private String query;
    private String values;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        if (savedInstanceState != null) {
            previousBundle = savedInstanceState.getParcelable(PluginBundleValues.BUNDLE_EXTRA_PREVIOUS_BUNDLE);
            title = savedInstanceState.getString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE);
            query = savedInstanceState.getString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY);
            values = savedInstanceState.getString(PluginBundleValues.BUNDLE_EXTRA_STRING_VALUES);
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

        findViewById(R.id.filter_selection).setOnClickListener(new View.OnClickListener() {
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
        this.previousBundle = previousBundle;
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
        return PluginBundleValues.generateBundle(title, query, values);
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
                if (equalBundles(getResultBundle(), previousBundle)) {
                    cancel();
                } else {
                    new AlertDialog.Builder(this, R.style.TasksDialog)
                            .setMessage(R.string.discard_changes)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cancel();
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void cancel() {
        mIsCancelled = true;
        finish();
    }

    private boolean equalBundles(Bundle one, Bundle two) {
        if (one == null) {
            return two == null;
        }
        if (two == null) {
            return false;
        }

        if(one.size() != two.size())
            return false;

        Set<String> setOne = one.keySet();
        Object valueOne;
        Object valueTwo;

        for(String key : setOne) {
            valueOne = one.get(key);
            valueTwo = two.get(key);
            if(valueOne instanceof Bundle && valueTwo instanceof Bundle &&
                    !equalBundles((Bundle) valueOne, (Bundle) valueTwo)) {
                return false;
            }
            else if(valueOne == null) {
                if(valueTwo != null || !two.containsKey(key))
                    return false;
            }
            else if(!valueOne.equals(valueTwo))
                return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_FILTER) {
            if (resultCode == RESULT_OK) {
                title = data.getStringExtra("extra_filter_name");
                query = data.getStringExtra("extra_filter_query");
                values = data.getStringExtra("extra_filter_values");
                updateActivity();
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PluginBundleValues.BUNDLE_EXTRA_PREVIOUS_BUNDLE, previousBundle);
        outState.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, title);
        outState.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, query);
        outState.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_VALUES, values);
    }

    private void updateActivity() {
        ((TextView) findViewById(R.id.text_view)).setText(title);
    }
}
