package org.tasks.locale.receiver;

import android.content.Intent;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.tasks.locale.bundle.PluginBundleValues;

/**
 * Tests the {@link FireReceiver}.
 */
public final class FireReceiverTest extends AndroidTestCase {
    /*
     * These test cases perform sanity checks. These tests are not very extensive and additional
     * testing is required to verify the BroadcastReceiver works correctly. For example, a human
     * would need to manually verify that a Toast message appears when a correct Intent is sent to
     * the receiver. Depending on what your setting implements, you may be able to verify more
     * easily that the setting triggered the desired result via unit tests than this sample setting
     * can.
     */

    @SmallTest
    public void testNullTitle() {
        final FireReceiver fireReceiver = new FireReceiver();

        final Bundle bundle = PluginBundleValues
                .generateBundle("test_title", "test_query"); //$NON-NLS-1$
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, null);

        /*
         * The receiver shouldn't crash if the EXTRA_BUNDLE is incorrect
         */
        fireReceiver.onReceive(getContext(), new Intent(
                com.twofortyfouram.locale.api.Intent.ACTION_FIRE_SETTING).putExtra(
                com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE, bundle));
    }

    @SmallTest
    public void testNullQuery() {
        final FireReceiver fireReceiver = new FireReceiver();

        final Bundle bundle = PluginBundleValues
                .generateBundle("test_title", "test_query"); //$NON-NLS-1$
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, null);

        /*
         * The receiver shouldn't crash if the EXTRA_BUNDLE is incorrect
         */
        fireReceiver.onReceive(getContext(), new Intent(
                com.twofortyfouram.locale.api.Intent.ACTION_FIRE_SETTING).putExtra(
                com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE, bundle));
    }

    @SmallTest
    public void testNormal() {
        final FireReceiver fireReceiver = new FireReceiver();

        final Bundle bundle = PluginBundleValues
                .generateBundle("test_title", "test_query"); //$NON-NLS-1$

        fireReceiver.onReceive(getContext(), new Intent(
                com.twofortyfouram.locale.api.Intent.ACTION_FIRE_SETTING).putExtra(
                com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE, bundle));
    }
}
