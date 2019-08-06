package com.github.hfantin.licensingservice.hystrix

import com.github.hfantin.licensingservice.util.UserContext
import com.github.hfantin.licensingservice.util.UserContextHolder

import java.util.concurrent.Callable


class DelegatingUserContextCallable<V>(private val delegate: Callable<V>,
                                       private var originalUserContext: UserContext?) : Callable<V> {

    @Throws(Exception::class)
    override fun call(): V {
        UserContextHolder.setContext(originalUserContext)
        try {
            return delegate.call()
        } finally {
            this.originalUserContext = null
        }
    }
    companion object {
        fun <V> create(delegate: Callable<V>, userContext: UserContext) = DelegatingUserContextCallable(delegate, userContext)
    }
}