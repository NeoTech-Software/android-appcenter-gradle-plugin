package org.neotech.app;

import android.content.Context;

/**
 * MainActivity is by default ignored, so this class makes sure the mapping.txt
 * file contains at least something.
 */
public class ObfuscatedClass {

    public CharSequence getAppName(Context context) {
        return context.getString(R.string.app_name);
    }
}
