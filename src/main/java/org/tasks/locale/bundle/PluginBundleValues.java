package org.tasks.locale.bundle;

import android.os.Bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tasks.locale.BuildConfig;

public final class PluginBundleValues {

    private static final Logger log = LoggerFactory.getLogger(PluginBundleValues.class);

    public static final String BUNDLE_EXTRA_STRING_TITLE = "org.tasks.locale.STRING_TITLE";
    public static final String BUNDLE_EXTRA_STRING_QUERY = "org.tasks.locale.STRING_QUERY";
    public static final String BUNDLE_EXTRA_STRING_VALUES = "org.tasks.locale.STRING_VALUES";
    public static final String BUNDLE_EXTRA_PREVIOUS_BUNDLE = "org.tasks.locale.PREVIOUS_BUNDLE";
    public static final String BUNDLE_EXTRA_INT_VERSION_CODE = "org.tasks.locale.INT_VERSION_CODE";

    public static boolean isBundleValid(final Bundle bundle) {
        if (null == bundle) {
            log.error("bundle is null");
            return false;
        }

        if (isNullOrEmpty(bundle, BUNDLE_EXTRA_STRING_TITLE)) {
            return false;
        }

        if (isNullOrEmpty(bundle, BUNDLE_EXTRA_STRING_QUERY)) {
            return false;
        }

        if (bundle.containsKey(BUNDLE_EXTRA_STRING_VALUES) && bundle.getString(BUNDLE_EXTRA_STRING_VALUES).trim().length() == 0) {
            log.error("Empty {}", BUNDLE_EXTRA_STRING_VALUES);
            return false;
        }

        Integer version = bundle.getInt(BUNDLE_EXTRA_INT_VERSION_CODE, -1);
        if (version == -1) {
            log.error("invalid version code: {}", version);
            return false;
        }

        return true;
    }

    private static boolean isNullOrEmpty(Bundle bundle, String key) {
        String value = bundle.getString(key);
        boolean isNullOrEmpty = value == null || value.trim().length() == 0;
        if (isNullOrEmpty) {
            log.error("Invalid {}", key);
        }
        return isNullOrEmpty;
    }

    public static Bundle generateBundle(String title, String query, String values) {
        Bundle result = new Bundle();
        result.putInt(BUNDLE_EXTRA_INT_VERSION_CODE, BuildConfig.VERSION_CODE);
        result.putString(BUNDLE_EXTRA_STRING_TITLE, title);
        result.putString(BUNDLE_EXTRA_STRING_QUERY, query);
        if (values != null) {
            result.putString(BUNDLE_EXTRA_STRING_VALUES, values);
        }
        return result;
    }

    public static String getTitle(Bundle bundle) {
        return bundle.getString(BUNDLE_EXTRA_STRING_TITLE);
    }

    public static String getQuery(Bundle bundle) {
        return bundle.getString(BUNDLE_EXTRA_STRING_QUERY);
    }

    public static String getValuesForNewTasks(Bundle bundle) {
        return bundle.getString(BUNDLE_EXTRA_STRING_VALUES);
    }

    private PluginBundleValues() {
    }
}
