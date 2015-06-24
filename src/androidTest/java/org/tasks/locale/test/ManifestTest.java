package org.tasks.locale.test;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.LinkedList;
import java.util.List;

public final class ManifestTest extends AndroidTestCase {

    @SmallTest
    public void testApplicationEnabled() {
        assertTrue(getContext().getApplicationInfo().enabled);
    }

    @SmallTest
    public void testPluginActivityPresent() {
        final PackageManager packageManager = getContext().getPackageManager();

        final List<ResolveInfo> activities = getPluginActivities(getContext());
        assertFalse(activities.isEmpty());

        for (final ResolveInfo x : activities) {
            assertTrue(x.activityInfo.enabled);
            assertTrue(x.activityInfo.exported);

            /*
             * Verify that the plug-in doesn't request permissions not available to the host
             */
            assertNull(x.activityInfo.permission);

            /*
             * Verify that the plug-in has a label attribute in the AndroidManifest
             */
            assertFalse(0 == x.activityInfo.labelRes);

            /*
             * Verify that the plug-in has a icon attribute in the AndroidManifest
             */
            assertFalse(0 == x.activityInfo.icon);
        }
    }

    @SmallTest
    public void testPluginReceiver() {
        final PackageManager packageManager = getContext().getPackageManager();

        final List<ResolveInfo> receivers = getPluginReceivers(getContext());

        assertEquals(1, receivers.size());

        for (final ResolveInfo x : receivers) {
            assertTrue(x.activityInfo.enabled);
            assertTrue(x.activityInfo.exported);

            /*
             * Verify that the plug-in doesn't request permissions not available to the host
             */
            assertNull(x.activityInfo.permission);
        }
    }

    /**
     * Gets a list of all Activities in {@code context}'s package that export
     * {@link com.twofortyfouram.locale.api.Intent#ACTION_EDIT_SETTING}.
     *
     * @param context Application context.
     */
    private static List<ResolveInfo> getPluginActivities(@NonNull final Context context) {

        final String packageName = context.getPackageName();

        final List<ResolveInfo> result = new LinkedList<ResolveInfo>();

        for (final ResolveInfo x : context.getPackageManager().queryIntentActivities(
                new Intent(com.twofortyfouram.locale.api.Intent.ACTION_EDIT_SETTING),
                PackageManager.GET_ACTIVITIES)) {
            if (packageName.equals(x.activityInfo.packageName)) {
                result.add(x);
            }
        }

        return result;
    }

    /**
     * Gets a list of all BroadcastReceivers in {@code context}'s package that export
     * {@link com.twofortyfouram.locale.api.Intent#ACTION_FIRE_SETTING ACTION_FIRE_SETTING}.
     *
     * @param context Application context.
     */
    private static List<ResolveInfo> getPluginReceivers(@NonNull final Context context) {
        final String packageName = context.getPackageName();

        final List<ResolveInfo> result = new LinkedList<ResolveInfo>();

        for (final ResolveInfo x : context.getPackageManager().queryBroadcastReceivers(
                new Intent(com.twofortyfouram.locale.api.Intent.ACTION_FIRE_SETTING),
                PackageManager.GET_INTENT_FILTERS)) {
            if (packageName.equals(x.activityInfo.packageName)) {
                result.add(x);
            }

        }

        return result;
    }
}
