package com.example.quizapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class  NavigationTest{
    @get:Rule
    val activityScenarioRule= activityScenarioRule<MainActivity>()

    @Test
    fun navigationOfFragments_ThroughApp(){
        activityScenarioRule.scenario.onActivity { activity -> activity.supportFragmentManager.beginTransaction().replace(R.id.container,SetupScreen_Fragment()).commit()}
        //startQuiz
        onView(withId(R.id.startButton)).perform(click())
        onView(withId(R.id.fragment_question_list)).check(matches(isDisplayed()))
        //Submit quiz
        onView(withId(R.id.submit_Button)).perform(click())
        onView(withText("Are you sure you want to Submit the test ?")).check(matches(isDisplayed()))
        onView(withText("Yes")).perform(click())
        onView(withId(R.id.Summary_screen_fragment)).check(matches(isDisplayed()))
        //restartquiz
        onView(withId(R.id.restartButton)).perform(click())
        onView(withId(R.id.Setup_ScreenFragment)).check(matches(isDisplayed()))
    }
}