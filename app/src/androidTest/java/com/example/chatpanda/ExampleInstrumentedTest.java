package com.example.chatpanda;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumented test that runs on an Android device or emulator.
 *
 * This test verifies:
 * 1. The application context is available
 * 2. The correct package name is used
 *
 * @see <a href="http://d.android.com/tools/testing">Android Testing Documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void appContext_isCorrect() {
        // Get the application context using modern API
        Context context = ApplicationProvider.getApplicationContext();

        // Verify context is not null
        assertNotNull(context);

        // Verify package name
        assertEquals("com.example.chatpanda", context.getPackageName());
    }
}
