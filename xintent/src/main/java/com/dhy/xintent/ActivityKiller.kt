package com.dhy.xintent

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.dhy.xintent.simple.SimpleActivityLifecycleCallbacks
import java.util.*
import kotlin.reflect.KClass

object ActivityKiller {
    private var inited = false
    @JvmStatic
    fun init(application: Application) {
        inited = true
        application.registerActivityLifecycleCallbacks(object : SimpleActivityLifecycleCallbacks() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activities.add(activity)
            }

            override fun onActivityDestroyed(activity: Activity) {
                activities.remove(activity)
            }
        })
    }

    @JvmStatic
    fun killAll() {
        kill(activities)
    }

    @JvmStatic
    fun killAllExcept(vararg activityClasses: KClass<out Activity>) {
        val classes = activityClasses.map { it.java }
        kill(activities.filter { !classes.contains(it.javaClass) })
    }

    @JvmStatic
    fun killAllExcept(vararg activityClasses: Class<out Activity>) {
        kill(activities.filter { !activityClasses.contains(it.javaClass) })
    }

    @JvmStatic
    fun kill(vararg activityClasses: KClass<out Activity>) {
        val classes = activityClasses.map { it.java }
        kill(activities.filter { classes.contains(it.javaClass) })
    }

    @JvmStatic
    fun kill(vararg activityClasses: Class<out Activity>) {
        kill(activities.filter { activityClasses.contains(it.javaClass) })
    }

    private fun kill(activities: List<Activity>) {
        if (!inited) {
            throw  IllegalStateException("you should init ${ActivityKiller.javaClass.simpleName} first")
        }
        activities.forEach { it.finish() }
    }

    private val activities = ArrayList<Activity>()
}
