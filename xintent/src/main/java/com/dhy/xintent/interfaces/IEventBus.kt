package com.dhy.xintent.interfaces

import org.greenrobot.eventbus.EventBus

interface IEventBus {
    fun register(obj: Any) {
        if (obj is IEventBus) {
            val bus = EventBus.getDefault()
            if (!bus.isRegistered(obj)) bus.register(obj)
        }
    }

    fun unregister(obj: Any) {
        if (obj is IEventBus) {
            val bus = EventBus.getDefault()
            if (bus.isRegistered(obj)) bus.unregister(obj)
        }
    }
}
