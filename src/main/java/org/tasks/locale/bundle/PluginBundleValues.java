package org.tasks.locale.bundle;

import android.content.Context;
import android.os.Bundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tasks.locale.BuildConfig;

public final class PluginBundleValues {

    private static final Logger log = LoggerFactory.getLogger(PluginBundleValues.class);

    public static final String BUNDLE_EXTRA_STRING_MESSAGE
            = "com.twofortyfouram.locale.example.setting.toast.extra.STRING_MESSAGE"; //$NON-NLS-1$

    public static final String BUNDLE_EXTRA_INT_VERSION_CODE
            = "com.twofortyfouram.locale.example.setting.toast.extra.INT_VERSION_CODE"; //$NON-NLS-1$

    public static boolean isBundleValid(final Bundle bundle) {
        if (null == bundle) {
            return false;
        }

        try {
            String message = bundle.getString(BUNDLE_EXTRA_STRING_MESSAGE);
            if (message == null || message.trim().length() == 0) {
                return false;
            }
            Integer version = bundle.getInt(BUNDLE_EXTRA_INT_VERSION_CODE, -1);
            if (version == -1) {
                return false;
            }
        } catch (final AssertionError e) {
            log.error("Bundle failed verification%s", e); //$NON-NLS-1$
            return false;
        }

        return true;
    }

    public static Bundle generateBundle(final Context context, final String message) {
        final Bundle result = new Bundle();
        result.putInt(BUNDLE_EXTRA_INT_VERSION_CODE, BuildConfig.VERSION_CODE);
        result.putString(BUNDLE_EXTRA_STRING_MESSAGE, message);

        return result;
    }

    public static String getMessage(final Bundle bundle) {
        return bundle.getString(BUNDLE_EXTRA_STRING_MESSAGE);
    }

    private PluginBundleValues() {
    }
}
