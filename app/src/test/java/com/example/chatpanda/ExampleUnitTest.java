package com.example.chatpanda;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Local unit tests that run on the JVM (no Android device required).
 *
 * These tests are fast and ideal for validating:
 * - Business logic
 * - Utility methods
 * - Calculations
 *
 * @see <a href="http://d.android.com/tools/testing">Android Testing Documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        // Arrange
        int a = 2;
        int b = 2;

        // Act
        int result = a + b;

        // Assert
        assertEquals(4, result);
    }

    @Test
    public void booleanLogic_isCorrect() {
        boolean isChatPandaAwesome = true;
        assertTrue(isChatPandaAwesome);
    }
}
