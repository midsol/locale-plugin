package org.tasks.locale.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Looper;

import java.lang.reflect.Field;

/**
 * Utility class to facilitate Activity testing.
 */
public final class ActivityTestUtil {

    /**
     * Helper to get the Activity result code.    This must only be called on the main thread.
     *
     * @param activity Activity whose result code is to be obtained.
     * @return Result code of the Activity.
     */
    public static int getActivityResultCode(final Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("activity cannot be null"); //$NON-NLS-1$
        }

        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new AssertionError("Must only be called on the main thread");
        }

        /*
         * This is a hack to obtain the Activity result code. There is no official way to check this using the Android
         * testing frameworks, so accessing the internals of the Activity object is the only way. This could
         * break on newer versions of Android.
         */

        try {
            final Field resultCodeField = Activity.class
                    .getDeclaredField("mResultCode"); //$NON-NLS-1$
            resultCodeField.setAccessible(true);
            return ((Integer) resultCodeField.get(activity)).intValue();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper to get the Activity result Intent.  This must only be called on the main thread.
     *
     * @param activity Activity whose result Intent is to be obtained.
     * @return Result Intent of the Activity.
     */
    public static Intent getActivityResultData(final Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("activity cannot be null"); //$NON-NLS-1$
        }

        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new AssertionError("Must only be called on the main thread");
        }

        /*
         * This is a hack to obtain the Activity result data. There is no official way to check this using the Android
         * testing frameworks, so accessing the internals of the Activity object is the only way. This could
         * break on newer versions of Android.
         */

        try {
            final Field resultIntentField = Activity.class
                    .getDeclaredField("mResultData"); //$NON-NLS-1$
            resultIntentField.setAccessible(true);
            return ((Intent) resultIntentField.get(activity));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Private constructor prevents instantiation.
     *
     * @throws UnsupportedOperationException because this class cannot be instantiated.
     */
    private ActivityTestUtil() {
        throw new UnsupportedOperationException("This class is non-instantiable"); //$NON-NLS-1$
    }
}