package com.dhy.xintent

import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import kotlin.reflect.KClass

class Waterfall private constructor() : IFlow {
    companion object : IFlow {
        override fun flow(action: Flow.(Flow) -> Unit): Waterfall {
            return Waterfall().flow(action)
        }
    }

    private val flowActions: Queue<Flow.(Flow) -> Unit> = LinkedBlockingQueue()
    private var onEnd: (Flow.(Flow) -> Unit)? = null
    private val results: MutableList<Any?> = mutableListOf()
    private var running = false

    /**
     * on the end of {@link Flow} *SHOULD INVOKE* {@link Flow#next} when has more Flows
     */
    override fun flow(action: Flow.(Flow) -> Unit): Waterfall {
        if (!end) {
            flowActions.add(action)
            startAction()
        }
        return this
    }

    fun onEnd(onEnd: Flow.(Flow) -> Unit): Waterfall {
        this.onEnd = onEnd
        startAction()
        return this
    }

    private fun startAction() {
        if (running) return
        var action = flowActions.poll()
        if (end) {
            action = onEnd
            onEnd = null
        }
        if (action != null) {
            running = true
            action(flow, flow)
        }
    }

    private var end = false

    @Suppress("UNCHECKED_CAST")
    private val flow = object : Flow {
        override fun <T : Any> getResult(cls: KClass<T>): T? {
            return results.find { cls.isInstance(it) } as T?
        }

        override fun <T : Any?> getPreResult(): T {
            return results.lastOrNull() as T
        }

        override fun <T : Any?> getResult(step: Int): T {
            return results[step] as T
        }

        override fun next(result: Any?) {
            results.add(result)
            running = false
            startAction()
        }

        private var isError: Boolean = false
        override fun end(error: Any?) {
            end = true
            isError = error != null
            flowActions.clear()
            next(error)
        }

        override fun isError() = isError
    }
}

interface IFlow {
    fun flow(action: Flow.(Flow) -> Unit): Waterfall
}