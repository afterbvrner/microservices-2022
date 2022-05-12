package com.itmo.microservices.demo.delivery.impl.service

import com.itmo.microservices.demo.delivery.api.service.DeliveryService
import com.itmo.microservices.demo.delivery.impl.entity.DeliverySlot
import com.itmo.microservices.demo.delivery.impl.repository.DeliveryRepository
import com.itmo.microservices.demo.users.impl.repository.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@Service
class DeliveryServiceImpl(
    private val deliveryRepository: DeliveryRepository): DeliveryService {
    init {
        if (true) for (i in 0..1000) {
            val slot = DeliverySlot().also { x->
                x.seconds = i
                x.isReserved = false
            }
            deliveryRepository.save(slot)
        }
    }
    override fun getAvailableSlots(numberOfSlots: Int): List<Int> =
        deliveryRepository.findTop1000ByIsReservedFalse()
            .groupBy { x -> x.seconds }
            .keys
            .mapNotNull { x -> x }
            .slice(0..numberOfSlots)
            .toList()

    override fun reserveAvailableSlot(slot: Int, reserveTime: Timestamp, orderId: Int) {
        var slot = deliveryRepository.findAllBySeconds(slot)
            .first { x -> !x.isReserved!! }
        slot.isReserved = true
        slot.datetime = reserveTime
        slot.orderId = orderId
        deliveryRepository.save(slot)
    }

    override fun cancelSlot(orderId: Int) {
        var slot = deliveryRepository.findByOrderId(orderId)
        cancelSlot(slot)
    }

    private fun cancelSlot(slot: DeliverySlot) {
        slot.isReserved = false
        slot.datetime = null
        slot.orderId = null
        deliveryRepository.save(slot)
    }

    fun deliveryComplete(orderId: Int) {
        var slot = deliveryRepository.findByOrderId(orderId)
        deliveryRepository.delete(slot)
    }

    @Scheduled(fixedDelay = 5000)
    fun scheduledCheckSlots() =
        deliveryRepository
            .findAllByIsReservedTrueAndDatetimeGreaterThan(Timestamp(System.currentTimeMillis()))
            .forEach(this::cancelSlot)

    }
