package com.maxilect.generator

import com.maxilect.generator.controller.GeneratorController
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SpringBootTest
@Disabled
class GeneratorApplicationTests(@Autowired val generatorController: GeneratorController) {

    @Test
    fun contextLoads() {
    }

    @Test
    fun concurrencyTest() {
        val threadsCount = 10
        val runsCount = 1000000
        val initValue = 1
        val threadPool = Executors.newFixedThreadPool(threadsCount)

        for (i in 1..threadsCount) {
            threadPool.execute(Thread {
				for (j in 1..runsCount) {
					generatorController.getUniqueId()
				}
			})
        }

        threadPool.shutdown()
        if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
            threadPool.shutdownNow()
        }

        assertEquals((threadsCount * runsCount + initValue).toLong(), generatorController.getUniqueId().id)
    }
}
