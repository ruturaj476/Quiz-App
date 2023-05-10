package com.example.quizapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class QuestionsListScreen_FragmentTest{
    @get:Rule
    val activityScenarioRule= activityScenarioRule<MainActivity>()

    @Test
    fun recyclerview_scroll_test(){
        activityScenarioRule.scenario.onActivity { activity -> activity.supportFragmentManager.beginTransaction().replace(R.id.container,QuestionsListScreen_Fragment()).commit()}
        onView(withId(R.id.fragment_question_list)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerViewofQuestion)).perform(RecyclerViewActions.scrollToPosition<QuestionsListAdapter.ViewHolder>(9));
    }
}