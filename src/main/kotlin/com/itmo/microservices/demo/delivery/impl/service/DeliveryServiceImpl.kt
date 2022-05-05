package com.itmo.microservices.demo.delivery.impl.service

import com.itmo.microservices.demo.delivery.api.service.DeliveryService
import com.itmo.microservices.demo.delivery.impl.repository.DeliveryRepository
import com.itmo.microservices.demo.users.impl.repository.UserRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@Service
class DeliveryServiceImpl(
    private val deliveryRepository: DeliveryRepository): DeliveryService {

    override fun getAvailableSlots(numberOfSlots: Int): List<Int> =
        deliveryRepository.findAllByIsReservedFalseAndDatetimeGreaterThan(Timestamp.from(Date().toInstant()))
            .groupBy { x -> x.datetime }
            .keys
            .mapNotNull { x -> (Date().time.seconds - x!!.time.seconds).toInt(DurationUnit.SECONDS) }
            .slice(0..numberOfSlots)
            .toList()

    override fun reserveAvailableSlot(slot: Int) {
//        var slot = deliveryRepository.findAllByDatetimeIs(Date(slot.toLong()))
//            .first { x -> !x.isReserved!! }
//        slot.isReserved = true
//        deliveryRepository.save(slot)
    }

    override fun cancelSlot(slot: Int) {
//        var slot = deliveryRepository.getDeliverySlotByDatetimeIs(Date(slot.toLong()))
//            .first { x -> x.isReserved!! }
//        slot.isReserved = false
//        deliveryRepository.save(slot)
    }


}