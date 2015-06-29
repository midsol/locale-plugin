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
        assertEquals("org.tasks.locale.STRING_TITLE", PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE);
        assertEquals("org.tasks.locale.STRING_QUERY", PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY);
        assertEquals("org.tasks.locale.INT_VERSION_CODE", PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE);
    }

    @SmallTest
    public void testGenerateBundle() {
        final Bundle bundle = PluginBundleValues.generateBundle("Foo", "Bar", "Baz");
        assertNotNull(bundle);

        assertEquals(4, bundle.keySet().size());

        assertEquals("Foo", bundle.getString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE));
        assertEquals("Bar", bundle.getString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY));
        assertEquals("Baz", bundle.getString(PluginBundleValues.BUNDLE_EXTRA_STRING_VALUES));
        assertEquals(BuildConfig.VERSION_CODE, bundle.getInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE));
    }

    @SmallTest
    public static void testVerifyBundle_correct() {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, "Foo");
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, "Bar");
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
    public static void testVerifyBundle_ignore_extra_items() {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, "Foo");
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, "Bar");
        bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        bundle.putString("test", "test"); //$NON-NLS-1$//$NON-NLS-2$
        assertTrue(PluginBundleValues.isBundleValid(bundle));
    }

    @SmallTest
    public static void testVerifyBundle_null_title() {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, null);
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, "Bar");
        bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        assertFalse(PluginBundleValues.isBundleValid(bundle));
    }

    @SmallTest
    public static void testVerifyBundle_empty_title() {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, "");
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, "Bar");
        bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        assertFalse(PluginBundleValues.isBundleValid(bundle));
    }

    @SmallTest
    public static void testVerifyBundle_null_query() {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, "Foo");
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, null);
        bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        assertFalse(PluginBundleValues.isBundleValid(bundle));
    }

    @SmallTest
    public static void testVerifyBundle_empty_query() {
        final Bundle bundle = new Bundle();
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, "Foo");
        bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, "");
        bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
        assertFalse(PluginBundleValues.isBundleValid(bundle));
    }

    @SmallTest
    public static void testVerifyBundle_wrong_type() {
        {
            final Bundle bundle = new Bundle();
            bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, "Foo");
            bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, 1);
            bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
            assertFalse(PluginBundleValues.isBundleValid(bundle));
        }

        {
            final Bundle bundle = new Bundle();
            bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, 1);
            bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, "Bar");
            bundle.putInt(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, 1);
            assertFalse(PluginBundleValues.isBundleValid(bundle));
        }

        {
            final Bundle bundle = new Bundle();
            bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_TITLE, "Foo");
            bundle.putString(PluginBundleValues.BUNDLE_EXTRA_STRING_QUERY, "Bar");
            bundle.putString(PluginBundleValues.BUNDLE_EXTRA_INT_VERSION_CODE, "test");
            assertFalse(PluginBundleValues.isBundleValid(bundle));
        }
    }
}
