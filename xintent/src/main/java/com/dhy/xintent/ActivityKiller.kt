package com.dhy.xintent

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.dhy.xintent.simple.SimpleActivityLifecycleCallbacks
import kotlin.reflect.KClass

object ActivityKiller {
    private var inited = false

    @JvmStatic
    fun init(application: Application) {
        if (inited) return
        inited = true
        application.registerActivityLifecycleCallbacks(object : SimpleActivityLifecycleCallbacks() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activityStack.add(activity)
            }

            override fun onActivityDestroyed(activity: Activity) {
                activityStack.remove(activity)
            }
        })
    }

    @JvmStatic
    fun killAll() {
        kill(activityStack)
    }

    @JvmStatic
    fun killAllExcept(vararg activityClasses: KClass<out Activity>) {
        val classes = activityClasses.map { it.java }
        kill(activityStack.filter { !classes.contains(it.javaClass) })
    }

    @JvmStatic
    fun killAllExcept(vararg activityClasses: Class<out Activity>) {
        kill(activityStack.filter { !activityClasses.contains(it.javaClass) })
    }

    @JvmStatic
    fun kill(vararg activityClasses: KClass<out Activity>) {
        val classes = activityClasses.map { it.java }
        kill(activityStack.filter { classes.contains(it.javaClass) })
    }

    @JvmStatic
    fun kill(vararg activityClasses: Class<out Activity>) {
        kill(activityStack.filter { activityClasses.contains(it.javaClass) })
    }

    private fun kill(activities: List<Activity>) {
        if (!inited) {
            throw  IllegalStateException("you should init ${ActivityKiller.javaClass.simpleName} first")
        }
        activities.forEach { it.finish() }
    }

    val activityStack: MutableList<Activity> = mutableListOf()
}
