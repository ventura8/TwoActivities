package com.example.twoactivities

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    private var activityScenario: ActivityScenario<MainActivity>? = null

    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun editTextHintTest() {
        onView(withId(R.id.editText_main)).check(matches(withHint("Enter Your Message Here")))
    }

    @Test
    fun replyTextNotDisplayed() {
        onView(withId(R.id.text_message_reply)).check(matches(not(isDisplayed())))
    }


    private fun performSendMessage(message: String) {
        onView(withId(R.id.editText_main)).perform(typeText(message), closeSoftKeyboard())
        onView(withId(R.id.button_main)).perform(click())
    }

    @Test
    fun sendMessageTest() {
        withIntents {
            val message = "Hy"

            performSendMessage(message)

            intended(
                allOf(
                    hasExtra(MainActivity.EXTRA_MESSAGE, message)
                )
            )

            onView(withId(R.id.text_message)).check(matches(withText(message)))
        }
    }

    @Test
    fun replyMessageTest() {
        val message = "Hy"
        val reply = "There"

        performSendMessage(message)

        onView(withId(R.id.editText_second)).perform(typeText(reply), closeSoftKeyboard())
        onView(withId(R.id.button_second)).perform(click())

        onView(withId(R.id.text_message_reply)).check(matches(withText(reply)))
    }

    @Test
    fun replyStubMessageTest() {
        withIntents {
            val message = "Hy"
            val reply = "There"

            val resultData = Intent()
            resultData.putExtra(SecondActivity.EXTRA_REPLY, reply)
            val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)

            intending(hasComponent(SecondActivity::class.java.name)).respondWith(result)

            performSendMessage(message)

            onView(withId(R.id.text_message_reply)).check(matches(withText(reply)))
        }
    }

    private fun withIntents(content: () -> Unit) {
        Intents.init()
        content()
        Intents.release()
    }
}