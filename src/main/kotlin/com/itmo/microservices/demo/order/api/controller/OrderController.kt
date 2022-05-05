package com.itmo.microservices.demo.order.api.controller

import com.itmo.microservices.demo.delivery.api.model.BookingDto
import com.itmo.microservices.demo.order.api.model.OrderModel
import com.itmo.microservices.demo.order.api.service.OrderService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(
    val orderService: OrderService
) {
    @PostMapping
    fun createOrder(): OrderModel = orderService.createOrder()

    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable orderId: UUID): OrderModel = orderService.getOrder(orderId)

    @PutMapping("/{orderId}/items/{itemId}")
    fun moveItemToCart(@PathVariable orderId: UUID, @PathVariable itemId: UUID, @RequestParam amount: Int) = orderService.moveItemToCart(orderId, itemId, amount)

    @PostMapping("/{orderId}/bookings")
    fun finalizeOrder(@PathVariable orderId: UUID): BookingDto = orderService.finalizeOrder(orderId)

    @PostMapping("/{orderId}/delivery")
    fun setDeliverySlot(@PathVariable orderId: UUID, @RequestParam("slot_in_sec") slotInSec: Int): BookingDto = orderService.setDeliverySlot(orderId, slotInSec)
}
