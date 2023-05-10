package com.example.quizapp

import android.R
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class QuestionDetailScreen_FragmentTest {
    @get:Rule
    val activityScenarioRule= activityScenarioRule<MainActivity>()

    @Test
    fun questionDetailScreen_nextBtnTest(){
        activityScenarioRule.scenario.onActivity { activity -> activity.supportFragmentManager.beginTransaction().replace(
            com.example.quizapp.R.id.container,QuestionDetailScreen_Fragment()).commit()}
        onView(withId(com.example.quizapp.R.id.nextQuestion)).perform(click())
    }
    @Test
    fun questionDetailSubmitBtn_test(){
        activityScenarioRule.scenario.onActivity { activity -> activity.supportFragmentManager.beginTransaction().replace(
            com.example.quizapp.R.id.container,QuestionDetailScreen_Fragment()).commit()}
        onView(withId(com.example.quizapp.R.id.Submit_Button)).perform(click())
        onView(ViewMatchers.withText("Are you sure you want to Submit the test ?")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText("Yes")).perform(click())
    }
}