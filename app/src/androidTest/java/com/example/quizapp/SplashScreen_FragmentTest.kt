package com.example.quizapp

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashScreen_FragmentTest{
    @get:Rule
    val activityScenarioRule= activityScenarioRule<MainActivity>()

    @Test
    fun isFragmentDisplayed(){
        launchFragmentInContainer<SplashScreen_Fragment>()
        onView(withId(R.id.fragment_splashScreen)).check(matches(isDisplayed()))
    }
}