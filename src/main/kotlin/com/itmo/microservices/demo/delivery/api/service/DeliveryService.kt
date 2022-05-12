package com.itmo.microservices.demo.delivery.api.service

import java.sql.Timestamp

interface DeliveryService {
    fun  getAvailableSlots(numberOfSlots: Int): List<Int>
    fun reserveAvailableSlot(slot: Int, reserveTime: Timestamp, orderId: Int)
    fun cancelSlot(orderId: Int)
    fun deliveryComplete(orderId: Int)
}