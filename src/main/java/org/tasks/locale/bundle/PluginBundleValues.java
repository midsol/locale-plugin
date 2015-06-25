package org.tasks.locale.bundle;

import android.os.Bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tasks.locale.BuildConfig;

public final class PluginBundleValues {

    private static final Logger log = LoggerFactory.getLogger(PluginBundleValues.class);

    public static final String BUNDLE_EXTRA_STRING_TITLE = "org.tasks.locale.STRING_TITLE";
    public static final String BUNDLE_EXTRA_STRING_QUERY = "org.tasks.locale.STRING_QUERY";
    public static final String BUNDLE_EXTRA_PREVIOUS_BUNDLE = "org.tasks.locale.PREVIOUS_BUNDLE";
    public static final String BUNDLE_EXTRA_INT_VERSION_CODE = "org.tasks.locale.INT_VERSION_CODE";

    public static boolean isBundleValid(final Bundle bundle) {
        if (null == bundle) {
            log.error("bundle is null");
            return false;
        }

        String title = bundle.getString(BUNDLE_EXTRA_STRING_TITLE);
        if (title == null || title.trim().length() == 0) {
            log.error("invalid title: {}", title);
            return false;
        }
        String query = bundle.getString(BUNDLE_EXTRA_STRING_QUERY);
        if (query == null || query.trim().length() == 0) {
            log.error("invalid query: {}", query);
            return false;
        }
        Integer version = bundle.getInt(BUNDLE_EXTRA_INT_VERSION_CODE, -1);
        if (version == -1) {
            log.error("invalid version code: {}", version);
            return false;
        }

        return true;
    }

    public static Bundle generateBundle(String title, String query) {
        Bundle result = new Bundle();
        result.putInt(BUNDLE_EXTRA_INT_VERSION_CODE, BuildConfig.VERSION_CODE);
        result.putString(BUNDLE_EXTRA_STRING_TITLE, title);
        result.putString(BUNDLE_EXTRA_STRING_QUERY, query);
        return result;
    }

    public static String getTitle(Bundle bundle) {
        return bundle.getString(BUNDLE_EXTRA_STRING_TITLE);
    }

    public static String getQuery(Bundle bundle) {
        return bundle.getString(BUNDLE_EXTRA_STRING_QUERY);
    }

    private PluginBundleValues() {
    }
}
