package org.tasks.locale.bundle;

import android.os.Bundle;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.tasks.locale.BuildConfig;

/**
 * Tests {@link PluginBundleValues}.
 */
public final class PluginBundleValuesTest extends AndroidTestCase {

    @SmallTest
    public static void testExtraConstants() {
        /*
         * NOTE: This test is expected to fail initially when you are adapting this example to your
         * own plug-in. Once you've settled on constant names for your Intent extras, go ahead and
         * update this test case.
         *
         * The goal of this test case is to prevent accidental renaming of the Intent extras. If the
         * extra is intentionally changed, then this unit test needs to be intentionally updated.
         */
        assertEquals(
                "com.twofortyfouram.locale.example.setting.toast.extra.STRING_MESSAGE",
                PluginBundleValues.BUNDLE_EXTRA_STRING_MESSAGE); //$NON-NLS-1$
        assertEquals(
                "com.twofortyfouram.locale.example.setting.toast.extra.INT_VERSION_CODE",
                PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE); //$NON-NLS-1$
    }

    @SmallTest
    public void testGenerateBundle() {
        final Bundle bundle = PluginBundleValues.generateBundle(getContext(), "Foo"); //$NON-NLS-1$
        assertNotNull(bundle);

        assertEquals(2, bundle.keySet().size());

        assertEquals("Foo",
                bundle.getString(PluginBundleValues.BUNDLE_EXTRA_STRING_MESSAGE)); //$NON-NLS-1$
        assertEquals(BuildConfig.VERSION_CODE,
                bundle.getInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE));
    }

    @SmallTest
    public static void testVerifyBundle_correct() {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_MESSAGE,
                "I am a toast message!"); //$NON-NLS-1$
        bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        assertTrue(PluginBundleValues.isBundleValid(bundle));
    }

    @SmallTest
    public static void testVerifyBundle_null() {
        assertFalse(PluginBundleValues.isBundleValid(null));
    }

    @SmallTest
    public static void testVerifyBundle_missing_extra() {
        assertFalse(PluginBundleValues.isBundleValid(new Bundle()));
    }

    @SmallTest
    public static void testVerifyBundle_extra_items() {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_MESSAGE,
                "I am a toast message!"); //$NON-NLS-1$
        bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        bundle.putString("test", "test"); //$NON-NLS-1$//$NON-NLS-2$
        assertTrue(PluginBundleValues.isBundleValid(bundle));
    }

    @SmallTest
    public static void testVerifyBundle_null_message() {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_MESSAGE, null);
        bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        assertFalse(PluginBundleValues.isBundleValid(bundle));
    }

    @SmallTest
    public static void testVerifyBundle_empty_message() {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_MESSAGE, ""); //$NON-NLS-1$
        bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        assertFalse(PluginBundleValues.isBundleValid(bundle));
    }

    @SmallTest
    public static void testVerifyBundle_wrong_type() {
        {
            final Bundle bundle = new Bundle();
            bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_STRING_MESSAGE, 1);
            bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
            assertFalse(PluginBundleValues.isBundleValid(bundle));
        }

        {
            final Bundle bundle = new Bundle();
            bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_MESSAGE,
                    "I am a toast message!"); //$NON-NLS-1$
            bundle.putString(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE,
                    "test"); //$NON-NLS-1$
            assertFalse(PluginBundleValues.isBundleValid(bundle));
        }
    }
}
