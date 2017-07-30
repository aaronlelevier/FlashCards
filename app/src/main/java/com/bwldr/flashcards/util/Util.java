package com.bwldr.flashcards.util;

import android.app.Activity;
import android.widget.Toast;

import java.util.UUID;

public class Util {

    private Util() {
        throw new AssertionError("This is a static class");
    }

    /**
     * Generate UUID as a String helper
     * @return String UUID
     */
    public static String genId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Show Toast helper
     * @param activity on which to show Toast
     * @param text content of the Toas message
     */
    public static void showToast(final Activity activity, final String text) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
