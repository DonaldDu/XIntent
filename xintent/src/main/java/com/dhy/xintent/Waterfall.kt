package com.dhy.xintent

import android.app.Activity
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

/**
 *@param activity for run onUiThread
 * */
class Waterfall(private val activity: Activity? = null) {
    private val flowActions: Queue<(Flow) -> Unit> = LinkedBlockingQueue()
    private var onError: ((Flow) -> Unit)? = null
    private val results: MutableList<Any?> = mutableListOf()
    /**
     * on the end of {@link Flow} *SHOULD INVOKE* {@link Flow#next} when has more Flows
     */
    fun flow(action: (Flow) -> Unit): Waterfall {
        flowActions.add(action)
        return this
    }

    fun onError(onError: ((Flow) -> Unit)): Waterfall {
        this.onError = onError
        return this
    }

    fun start(onUiThread: Boolean = false) {
        next(onUiThread)
    }

    private fun exit() {
        flowActions.clear()
        results.clear()
    }

    private fun next(onUiThread: Boolean) {
        val action = flowActions.poll()
        if (action != null) {
            if (onUiThread && activity?.isFinishing != true) {
                activity!!.runOnUiThread {
                    action.invoke(flow)
                }
            } else action.invoke(flow)
        } else exit()
    }

    @Suppress("UNCHECKED_CAST")
    private val flow = object : Flow {

        override fun <T : Any> getPreResult(): T {
            return results.lastOrNull() as T
        }

        override fun <T : Any> getResult(step: Int): T {
            return results[step] as T
        }

        override fun next(result: Any?, onUiThread: Boolean) {
            results.add(result)
            this@Waterfall.next(onUiThread)
        }

        override fun error(result: Any?, onUiThread: Boolean) {
            flowActions.clear()
            if (onError != null) {
                flowActions.add(onError)
                onError = null
            }
            next(result, onUiThread)
        }
    }
}