package com.ni.core.baseClasses


import androidx.viewbinding.ViewBinding

abstract class BaseObservableFragment<V : ViewBinding, ListenerType>(inflater: Inflate<V>) : BaseFragment<V>(inflater) {

    private val listeners = hashSetOf<ListenerType>()

    fun notify(data: (ListenerType) -> Unit) {
        listeners.forEach(data)
    }

    protected fun registerObserver(it: ListenerType) {
        if (listeners.contains(it).not()) listeners.add(it)
    }

    protected fun unRegisterObserver(it: ListenerType) {
        if (listeners.contains(it)) listeners.remove(it)
    }
}