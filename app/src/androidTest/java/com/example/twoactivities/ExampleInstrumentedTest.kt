package com.example.twoactivities

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun editTextHintTest() {
        onView(withId(R.id.editText_main)).check(matches(withHint("Enter Your Message Here")))
    }

    @Test
    fun replyTextNotDisplayed() {
        onView(withId(R.id.text_message_reply)).check(matches(not(isDisplayed())))
    }


    fun performSendMessage(message : String){
        onView(withId(R.id.editText_main)).perform(typeText(message), closeSoftKeyboard())
        onView(withId(R.id.button_main)).perform(click())
    }

    @Test
    fun sendMessageTest() {
        val message = "Hy"

        performSendMessage(message)

        intended(allOf(
                hasExtra(MainActivity.EXTRA_MESSAGE, message)))

        onView(withId(R.id.text_message)).check(matches(withText(message)))
    }

    @Test
    fun replyMessageTest() {
        val message = "Hy"
        val reply  = "There"

        performSendMessage(message)

        onView(withId(R.id.editText_second)).perform(typeText(reply), closeSoftKeyboard())
        onView(withId(R.id.button_second)).perform(click())

        onView(withId(R.id.text_message_reply)).check(matches(withText(reply)))
    }

    @Test
    fun replyStubMessageTest() {
        val message = "Hy"
        val reply  = "There"

        val resultData = Intent()
        resultData.putExtra(SecondActivity.EXTRA_REPLY, reply)
        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)

        intending(toPackage(activityRule.activity.packageName)).respondWith(result)

        performSendMessage(message)

        onView(withId(R.id.text_message_reply)).check(matches(withText(reply)))
    }
}