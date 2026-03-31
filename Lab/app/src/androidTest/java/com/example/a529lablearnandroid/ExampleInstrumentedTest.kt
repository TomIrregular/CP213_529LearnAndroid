package com.example.a529lablearnandroid

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.a529lablearnandroid", appContext.packageName)
    }
}

@RunWith(AndroidJUnit4::class)
class MenuActivityTest {

    // This rule launches MenuActivity before each test and closes it after
    @get:Rule
    val activityRule = ActivityScenarioRule(MenuActivity::class.java)

    @Test
    fun appLaunchesSuccessfully() {
        // Since the rule launches the activity, if the test doesn't crash,
        // the activity successfully started!

        // Later, you'll add Espresso code here to click buttons, like:
        // onView(withId(R.id.my_button)).perform(click())
    }
}