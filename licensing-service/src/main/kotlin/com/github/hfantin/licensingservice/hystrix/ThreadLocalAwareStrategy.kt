package com.github.hfantin.licensingservice.hystrix

import com.github.hfantin.licensingservice.util.UserContextHolder
import com.netflix.hystrix.HystrixThreadPoolKey
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle
import com.netflix.hystrix.strategy.properties.HystrixProperty
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit


class ThreadLocalAwareStrategy(private val existingConcurrencyStrategy: HystrixConcurrencyStrategy?) : HystrixConcurrencyStrategy() {

    override fun getBlockingQueue(maxQueueSize: Int) = existingConcurrencyStrategy?.let { it.getBlockingQueue(maxQueueSize) }
            ?: super.getBlockingQueue(maxQueueSize)

    override fun <T> getRequestVariable(rv: HystrixRequestVariableLifecycle<T>) = existingConcurrencyStrategy?.let { it.getRequestVariable(rv) }
            ?: super.getRequestVariable(rv)

    override fun getThreadPool(threadPoolKey: HystrixThreadPoolKey,
                               corePoolSize: HystrixProperty<Int>,
                               maximumPoolSize: HystrixProperty<Int>,
                               keepAliveTime: HystrixProperty<Int>, unit: TimeUnit,
                               workQueue: BlockingQueue<Runnable>) = existingConcurrencyStrategy?.let { it.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue) }
            ?: super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)

    override fun <T> wrapCallable(callable: Callable<T>) = existingConcurrencyStrategy?.let { it.wrapCallable(DelegatingUserContextCallable(callable, UserContextHolder.getContext())) }
            ?: super.wrapCallable(DelegatingUserContextCallable(callable, UserContextHolder.getContext()))

}
