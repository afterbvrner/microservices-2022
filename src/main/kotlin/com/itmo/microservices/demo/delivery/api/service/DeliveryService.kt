package com.itmo.microservices.demo.delivery.api.service

interface DeliveryService {
    fun  getAvailableSlots(numberOfSlots: Int): List<Int>
    fun reserveAvailableSlot(slot:Int)
    fun cancelSlot(slot: Int)
}