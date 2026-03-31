package com.example.a529lablearnandroid

import android.os.Build
import androidx.test.core.app.ActivityScenario
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 */

// 1. The simple math test DOES NOT need Robolectric.
// Standard JUnit handles this perfectly and super fast.
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

// 2. The Activity test NEEDS Robolectric to fake the Android OS.
// The annotations MUST go immediately above THIS class!
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class MenuActivityLocalTest {

    @Test
    fun testMenuActivity() {
        val scenario = ActivityScenario.launch(MenuActivity::class.java)

        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }
    @Test
    fun testCamActivity() {
        val scenario = ActivityScenario.launch(CameraActivity::class.java)

        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }
    @Test
    fun testGalleryActivity() {
        val scenario = ActivityScenario.launch(GalleryActivity::class.java)

        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }
    @Test
    fun testWeaselActivity() {
        val scenario = ActivityScenario.launch(MainWeasel::class.java)

        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }
    @Test
    fun testPokeActivity() {
        val scenario = ActivityScenario.launch(PokedexActivity::class.java)

        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }
    @Test
    fun testRPGActivity() {
        val scenario = ActivityScenario.launch(RPGCardActivity::class.java)

        scenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }
}