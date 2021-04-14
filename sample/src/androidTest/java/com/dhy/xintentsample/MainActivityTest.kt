package com.dhy.xintentsample

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dhy.xintent.XIntent
import com.dhy.xintent.putSerializableExtra
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private val activityOptions = Bundle()
    private val iValue = 1
    private val bValue = true
    private val sValue = "1"

    @JvmField
    @Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    init {
        XIntent.with(activityOptions).apply {
            putSerializableExtra(iValue, bValue, sValue)
        }
    }

    @Test
    fun firstIn() {
        activityScenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
//        activityScenarioRule.scenario.onActivity {
//            XIntent.with(it).apply {
//                val i: Int? = readExtra()
//                val b: Boolean? = readExtra()
//                val s: String? = readExtra()
//                Assert.assertEquals(iValue, i)
//                Assert.assertEquals(bValue, b)
//                Assert.assertEquals(sValue, s)
//            }
//        }


        activityScenarioRule.scenario.recreate()
        Espresso.onView(ViewMatchers.withText("true/false"))
                .check(matches(isDisplayed()))


    }
}