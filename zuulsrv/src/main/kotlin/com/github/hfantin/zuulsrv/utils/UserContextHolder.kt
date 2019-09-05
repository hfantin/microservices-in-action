package com.github.hfantin.zuulsrv.utils

import org.springframework.util.Assert

object UserContextHolder {

    private val userContext = ThreadLocal<UserContext>()

    fun getContext() = userContext.get() ?: UserContext().also { uc -> userContext.set(uc) }

    fun setContext(context: UserContext?) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted")
        userContext.set(context)
    }
}