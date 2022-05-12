package com.itmo.microservices.demo.delivery.api.controller

import com.itmo.microservices.commonlib.annotations.InjectEventLogger
import com.itmo.microservices.commonlib.logging.EventLogger
import com.itmo.microservices.demo.delivery.api.service.DeliveryService
import com.itmo.microservices.demo.users.api.model.AppUserModel
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset

@RestController
@RequestMapping("/delivery")
class DeliveryController(private val deliveryService: DeliveryService) {

    @InjectEventLogger
    private lateinit var eventLogger: EventLogger

    @GetMapping("/slots")
    @Operation(
        summary = "Getting available delivery slots",
        responses = [ApiResponse(description = "OK", responseCode = "200")],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getSlots(@Parameter(required = true, description = "Number of slots we want to get")
                 @RequestParam number: Int): List<Int> {

        return  deliveryService.getAvailableSlots(number)
    }

    @PutMapping("/slots")
    fun reserveSlot(@RequestParam slot: Int, //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam timestamp: LocalDateTime,
                    @RequestParam orderId: Int) {
        deliveryService.reserveAvailableSlot(slot, Timestamp(System.currentTimeMillis()), orderId)
    }

    @PutMapping("/slots/{orderId}/cancel")
    fun cancelSlot(@PathVariable orderId: Int) {
        deliveryService.cancelSlot(orderId)
    }
}