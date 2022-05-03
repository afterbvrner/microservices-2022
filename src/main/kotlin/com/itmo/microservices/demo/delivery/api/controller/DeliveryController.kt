package com.itmo.microservices.demo.delivery.api.controller

import com.itmo.microservices.demo.delivery.api.service.DeliveryService
import com.itmo.microservices.demo.users.api.model.AppUserModel
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/delivery")
class DeliveryController(private val deliveryService: DeliveryService) {

    @GetMapping("/slots")
    @Operation(
        summary = "Получение возможных сейчас слотов доставки",
        responses = [ApiResponse(description = "OK", responseCode = "200")],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getSlots(@Parameter(required = true, description = "Number of slots we want to get") @RequestParam number: Int): List<Int> {
        return  deliveryService.getAvailableSlots(number)
    }

    @PutMapping("/slots")
    fun reserveSlot(slot: Int) {
        deliveryService.reserveAvailableSlot(slot)
    }

    @PutMapping("/slots/cancel")
    fun cancelSlot(slot: Int) {
        deliveryService.cancelSlot(slot)
    }
}