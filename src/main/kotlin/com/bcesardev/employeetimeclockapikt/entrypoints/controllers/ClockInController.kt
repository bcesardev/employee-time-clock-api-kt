package com.bcesardev.employeetimeclockapikt.entrypoints.controllers

import com.bcesardev.employeetimeclockapikt.business.services.ClockInService
import com.bcesardev.employeetimeclockapikt.business.services.EmployeeService
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.ClockInDto
import com.bcesardev.employeetimeclockapikt.entrypoints.dtos.ResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat
import javax.validation.Valid

@RestController
@RequestMapping("/api/clock-in")
class ClockInController(val clockInService: ClockInService, employeeService: EmployeeService) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Value("\${pagination.qty-by-page}")
    val qtyByPage: Int = 0

    @PostMapping
    fun add(@Valid @RequestBody clockInDto: ClockInDto, result: BindingResult): ResponseEntity<ResponseDto<ClockInDto>> {
        
    }

}