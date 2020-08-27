package com.maxilect.coordinator.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class CoordinatorController {

    @Volatile
    var rangeId = AtomicLong(0)

    @GetMapping
    fun getRangeId() = RangeIdDto(rangeId.getAndIncrement())
}

data class RangeIdDto(val id: Long)
