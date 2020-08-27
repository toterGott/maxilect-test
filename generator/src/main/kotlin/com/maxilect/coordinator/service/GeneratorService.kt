package com.maxilect.generator.service

import main.model.ResponseDto
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import javax.annotation.PostConstruct


@Service
class GeneratorService {

    var counter: Long = 0
    var rangeId: Long = 0
    val rangeSize: Long = 10

    @PostConstruct
    fun initRangeId() {
        updateRangeId()
    }

    @Synchronized
    fun getUniqueId(): ResponseDto {
        if (counter == rangeSize) {
            updateRangeId()
            counter = 0
        }
        val result = rangeSize * rangeId + counter + 1;
        ++counter
        return ResponseDto(result)
    }

    fun updateRangeId() {
        val restTemplate = RestTemplate()
        val fooResourceUrl = "http://coordinator:8080/"
        val response = restTemplate.getForEntity(fooResourceUrl, ResponseDto::class.java)
        rangeId = response.body!!.id
    }
}
