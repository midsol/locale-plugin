package org.tasks.locale.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tasks.locale.bundle.PluginBundleValues;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.todoroo.astrid.service;

public final class FireReceiver extends BroadcastReceiver {

    private static final Logger log = LoggerFactory.getLogger(FireReceiver.class);

    protected boolean isBundleValid(final Bundle bundle) {
        return PluginBundleValues.isBundleValid(bundle);
    }

    protected void firePluginSetting(final Context context, final Bundle bundle) {
        String sTitle = PluginBundleValues.getTitle(bundle));
        TaskCreator.basicQuickAddTask(sTitle);
    }

    @Override
    public final void onReceive(final Context context, final Intent intent) {
        log.debug("Received {}", intent); //$NON-NLS-1$

        /*
         * Note: It is OK if a host sends an ordered broadcast for plug-in
         * settings. Such a behavior would allow the host to optionally block until the
         * plug-in setting finishes.
         */

        if (!com.twofortyfouram.locale.api.Intent.ACTION_FIRE_SETTING.equals(intent.getAction())) {
            log.error("Intent action is not {}", com.twofortyfouram.locale.api.Intent.ACTION_FIRE_SETTING); //$NON-NLS-1$
            return;
        }

        /*
         * Ignore implicit intents, because they are not valid. It would be
         * meaningless if ALL plug-in setting BroadcastReceivers installed were
         * asked to handle queries not intended for them. Ideally this
         * implementation here would also explicitly assert the class name as
         * well, but then the unit tests would have trouble. In the end,
         * asserting the package is probably good enough.
         */
        if (!context.getPackageName().equals(intent.getPackage())
                && !new ComponentName(context, this.getClass().getName()).equals(intent
                .getComponent())) {
            log.error("Intent is not explicit"); //$NON-NLS-1$
            return;
        }

        final Bundle bundle = intent
                .getBundleExtra(com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE);

        if (null == bundle) {
            log.error("{} is missing",
                    com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE); //$NON-NLS-1$
            return;
        }

        if (!isBundleValid(bundle)) {
            log.error("{} is invalid",
                    com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE); //$NON-NLS-1$
            return;
        }

        firePluginSetting(context, bundle);
    }
}
