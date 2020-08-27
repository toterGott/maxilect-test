package com.maxilect.generator.controller

import com.maxilect.generator.service.GeneratorService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class GeneratorController(val generatorService: GeneratorService) {

    @GetMapping
    fun getUniqueId() = generatorService.getUniqueId()
}
