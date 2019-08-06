package com.github.hfantin.licensingservice.hystrix

import com.netflix.hystrix.strategy.HystrixPlugins
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class ThreadLocalConfiguration {

    @Autowired(required = false)
    private val existingConcurrencyStrategy: HystrixConcurrencyStrategy? = null

    @PostConstruct
    fun init() {
        // Keeps references of existing Hystrix plugins.
        with(HystrixPlugins.getInstance()){
            val eventNotifier = this.eventNotifier
            val metricsPublisher = this.metricsPublisher
            val propertiesStrategy = this.propertiesStrategy
            val commandExecutionHook = this.commandExecutionHook
            HystrixPlugins.reset()
            this.registerConcurrencyStrategy(ThreadLocalAwareStrategy(existingConcurrencyStrategy))
            this.registerEventNotifier(eventNotifier)
            this.registerMetricsPublisher(metricsPublisher)
            this.registerPropertiesStrategy(propertiesStrategy)
            this.registerCommandExecutionHook(commandExecutionHook)
        }

    }
}
